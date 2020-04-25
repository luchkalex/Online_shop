package ua.electro.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.electro.models.*;
import ua.electro.servises.CartService;
import ua.electro.servises.OrderService;
import ua.electro.servises.UserService;

import java.util.Collections;
import java.util.Map;
/*This controller made for user management*/

/*PreAuthorize means that you have to have privileges to get access to this methods */
/*In this case only ADMIN has access to user list and can change it*/
@Controller
@SessionAttributes("session_user")
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final OrderService orderService;
    private final CartService cartService;

    public UserController(UserService userService, OrderService orderService, CartService cartService) {
        this.userService = userService;
        this.orderService = orderService;
        this.cartService = cartService;
    }


    /*Returns list of users*/
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public String userList(Model model) {
        model.addAttribute(userService.findAll());
        return "userList";
    }

    /*Returns page of user with edit form*/
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("{user}")
    public String userEditForm(@PathVariable User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "userEdit";
    }


    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public String saveUser(
            @RequestParam String username,
            @RequestParam Map<String, String> form,
            @RequestParam("user_id") User user) {

        userService.saveUser(username, form, user);

        return "redirect:/users";
    }

    @GetMapping("profile")
    public String getProfile(Model model, @AuthenticationPrincipal User user) {

        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());


        return "profile";
    }

    @GetMapping("/cart")
    public String getCart(
            @AuthenticationPrincipal User user,
            @ModelAttribute("session_user") User session_user,
            Model model) {

        user = userService.getActualUser(user, session_user);

        model.addAttribute("user", user);

        return "cartPage";
    }

    // FIXME: 4/23/20 In auth user: Edit works but doesn't display changes
    @GetMapping("editCart")
    public String getCart(
            @AuthenticationPrincipal User user,
            @ModelAttribute("session_user") User session_user,
            @RequestParam("cartItem_id") Long cartItem_id,
            @RequestParam("quantity") Integer quantity,
            Model model) {

        user = userService.getActualUser(user, session_user);

        for (CartItem u_cartItem : user.getCartItems()) {
            if (u_cartItem.getId().equals(cartItem_id)) {
                u_cartItem.setQuantity(quantity);

                if (user.getId() != null) {
                    cartService.saveSet(Collections.singleton(u_cartItem));
                }

//                model.addAttribute("user", user);
                return "redirect:/users/cart";
            }
        }

//        model.addAttribute("user", user);
        return "redirect:/users/cart";
    }

    // FIXME: 4/23/20 In auth user: Delete work but doesn't displays
    @GetMapping("deleteCart/{cartItem_id}")
    public String deleteCart(
            @AuthenticationPrincipal User user,
            @ModelAttribute("session_user") User session_user,
            @PathVariable("cartItem_id") Long cartItem_id,
            Model model) {

        user = userService.getActualUser(user, session_user);

        for (CartItem u_cartItem : user.getCartItems()) {
            if (u_cartItem.getId().equals(cartItem_id)) {
                user.getCartItems().remove(u_cartItem);
                if (user.getId() != null) {
                    cartService.deleteOne(u_cartItem);
                }
                return "redirect:/users/cart";
            }
        }

        return "redirect:/users/cart";
    }

    @GetMapping("order_maker")
    public String getOrderPage(
            @AuthenticationPrincipal User user,
            @ModelAttribute("session_user") User session_user,
            Model model) {

        // TODO: 4/22/20 Check on empty cart
        user = userService.getActualUser(user, session_user);

        model.addAttribute("user", user);
        model.addAttribute("types_of_payment", orderService.findAllTypesOfPayment());
        model.addAttribute("types_of_delivery", orderService.findAllTypesOfDelivery());

        return "orderPage";
    }

    // TODO: 4/25/20 When order will be approved and done add to outcome
    @PostMapping("order_maker")
    public String makeOrder(
            @AuthenticationPrincipal User user,
            @ModelAttribute("session_user") User session_user,
            @ModelAttribute("order") OrderOfProduct order,
            @RequestParam Long payment_id,
            @RequestParam Long delivery_id,
            Model model) {

        user = userService.getActualUser(user, session_user);

        order.setTypeOfPayment(orderService.findOneTypeOfPaymentById(payment_id));

        order.setTypesOfDelivery(orderService.findOneDeliveryById(delivery_id));

        order.setTotal(0);

        user.getCartItems().forEach(cartItem -> {
            order.getOrderItems().add(new OrderItem(
                    cartItem.getProduct(),
                    cartItem.getQuantity(),
                    order
            ));
            order.setTotal(order.getTotal() + (cartItem.getQuantity() * cartItem.getProduct().getPrice()));
        });

        if (user.getId() != null) {
            order.setUser(user);
        }

        /*Delete in db*/
        cartService.deleteSet(user.getCartItems());

        orderService.save(order);

        user.getCartItems().clear();

        user.getOrders().add(order);

        model.addAttribute("user", user);
        model.addAttribute("order", order);

        return "orderConfirmPage";
    }


    @PostMapping("profile")
    public String editProfile(
            @AuthenticationPrincipal User user,
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String email
    ) {

        userService.editUser(user, username, password, email);

        return "redirect:/";
    }

    @GetMapping("subscribe/{user}")
    public String subscribe(
            @AuthenticationPrincipal User currentUser,
            @PathVariable User user) {

        userService.subscribe(currentUser, user);

        return "redirect:/users-messages" + user.getId();
    }

    @GetMapping("unsubscribe/{user}")
    public String unsubscribe(
            @AuthenticationPrincipal User currentUser,
            @PathVariable User user) {

        userService.unsubscribe(currentUser, user);

        return "redirect:/users-messages" + user.getId();
    }

    @GetMapping("{type}/{user}/list")
    public String userList(
            @PathVariable User user,
            @PathVariable String type,
            Model model
    ) {
        model.addAttribute("type", type);
        model.addAttribute("userChannel", user);
        if ("subscriptions".equals(type)) {
            model.addAttribute("users", user.getSubscriptions());
        } else {
            model.addAttribute("users", user.getSubscribers());
        }


        return "subscriptions";
    }

    // FIXME: 4/22/20 This method repeated in ProductController
    @ModelAttribute("session_user")
    public User createUser() {
        return new User();
    }
}
