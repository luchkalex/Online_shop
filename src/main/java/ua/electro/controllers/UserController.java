package ua.electro.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.electro.models.*;
import ua.electro.servises.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;


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
    private final ProductService productService;
    private final CommentService commentService;

    public UserController(UserService userService, OrderService orderService, CartService cartService, ProductService productService, CommentService commentService) {
        this.userService = userService;
        this.orderService = orderService;
        this.cartService = cartService;
        this.productService = productService;
        this.commentService = commentService;
    }


    /*------------------------Delete?------------------------------*/

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

    /*------------------------------Profile---------------------------------*/

    @GetMapping("profile")
    public String getProfile(Model model, @AuthenticationPrincipal User user) {

        List<OrderOfProduct> orders = orderService.findByUser(user);

        model.addAttribute("user", user);
        model.addAttribute("orders", orders);
        return "profile";
    }

    @PostMapping("profile")
    public String editProfile(
            @AuthenticationPrincipal User user,
            @ModelAttribute("user") User newUser
    ) {

        userService.editUser(user, newUser);
        return "redirect:/";
    }

    @GetMapping("deleteAccount")
    public String deleteAccount(@AuthenticationPrincipal User user) {

        userService.deactivateUser(user.getId());
        return "redirect:/";
    }

    /*------------------------------Cart------------------------------*/

    @GetMapping("/cart")
    public String getCart(
            @AuthenticationPrincipal User user,
            @ModelAttribute("session_user") User session_user,
            Model model) {

        user = userService.getActualUser(user, session_user);

        Set<CartItem> cartItems = getCartItems(user);

        model.addAttribute("user", user);
        model.addAttribute("cartItems", cartItems);
        return "cartPage";
    }


    @GetMapping("editCart")
    public String getCart(
            @AuthenticationPrincipal User user,
            @ModelAttribute("session_user") User session_user,
            @RequestParam("cartItem_id") Long cartItem_id,
            @RequestParam("quantity") Integer quantity) {

        user = userService.getActualUser(user, session_user);

        /*Block for save deleting. Update only cart item that have current user*/
        for (CartItem u_cartItem : user.getCartItems()) {
            if (u_cartItem.getId().equals(cartItem_id)) {
                u_cartItem.setQuantity(quantity);

                if (user.getId() != null) {
                    cartService.saveSet(Collections.singleton(u_cartItem));
                }
                return "redirect:/users/cart";
            }
        }
        return "redirect:/users/cart";
    }

    @GetMapping("deleteCart/{cartItem_id}")
    public String deleteCart(
            @AuthenticationPrincipal User user,
            @ModelAttribute("session_user") User session_user,
            @PathVariable("cartItem_id") Long cartItem_id) {

        user = userService.getActualUser(user, session_user);

        /*Block for save deleting. Update only cart item that have current user*/
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

    /*------------------------------Order-----------------------------------*/

    @GetMapping("order_maker")
    public String getEditOrderPage(
            @AuthenticationPrincipal User user,
            @ModelAttribute("session_user") User session_user,
            Model model) {

        // TODO: 4/22/20 Check on empty cart
        user = userService.getActualUser(user, session_user);


        model.addAttribute("user", user);

        /*In case when cart empty return error to cart*/
        if (user.getCartItems().isEmpty()) {
            Set<CartItem> cartItems = getCartItems(user);
            model.addAttribute("cartError", "Cart is empty. You can't make order");
            model.addAttribute("cartItems", cartItems);
            return "cartPage";
        }

        model.addAttribute("types_of_payment", orderService.findAllTypesOfPayment());
        model.addAttribute("types_of_delivery", orderService.findAllTypesOfDelivery());
        return "editOrderPage";
    }

    // TODO: 4/25/20 When order will be approved and completed add to outcome
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

        /*Copy from cart items to order items*/
        user.getCartItems().forEach(cartItem -> {
            order.getOrderItems().add(new OrderItem(
                    cartItem.getProduct(),
                    cartItem.getQuantity(),
                    order
            ));
            order.setTotal(order.getTotal() + (cartItem.getQuantity() * cartItem.getProduct().getPrice()));
        });

        /*If user registered, add to order*/
        if (user.getId() != null) {
            order.setUser(user);
        }

        /*Clear cart in db*/
        cartService.deleteSet(user.getCartItems());

        orderService.save(order);

        model.addAttribute("order", order);
        model.addAttribute("user", user);

        return "orderPage";
    }

    @GetMapping("order/{order_id}")
    public String getOrderPage(
            @PathVariable("order_id") Long order_id,
            Model model) {

        model.addAttribute("order", orderService.findOneById(order_id));
        return "orderPage";
    }

    @GetMapping("cancelOrder/{order_id}")
    public String cancelOrder(@PathVariable("order_id") Long order_id) {

        orderService.cancelOrder(order_id);
        return "redirect:/users/profile";
    }

    /*------------------------------Comments------------------------------*/

    @GetMapping("comment/{product_id}")
    public String getCommentPage(
            @PathVariable("product_id") Long product_id,
            Model model) {

        model.addAttribute("product", productService.findOneById(product_id));
        return "feedback";
    }

    @PostMapping("comment/{product_id}")
    public String getCommentPage(
            @AuthenticationPrincipal User user,
            @PathVariable("product_id") Long product_id,
            @ModelAttribute("comment") Comment comment,
            Model model) {

        comment.setAuthor(user);
        comment.setProduct(productService.findOneById(product_id));

        commentService.save(comment);

        model.addAttribute("product", productService.findOneById(product_id));
        return "redirect:/products/" + product_id;
    }



    /*------------------------------Subscriptions Delete?------------------------------*/


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

    /*------------------------------Additional------------------------------*/

    @ModelAttribute("session_user")
    public User createUser() {
        User user = new User();
        user.setActive(true);
        return user;
    }

    private Set<CartItem> getCartItems(User user) {
        Set<CartItem> cartItems;

        if (user.getId() != null) {
            cartItems = cartService.findByUser(user);
        } else {
            cartItems = user.getCartItems();
        }
        return cartItems;
    }
}
