package ua.electro.controllers;

import lombok.val;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.electro.models.*;
import ua.electro.servises.CartService;
import ua.electro.servises.OrderService;
import ua.electro.servises.ProductService;
import ua.electro.servises.UserService;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Set;

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

    public UserController(UserService userService, OrderService orderService, CartService cartService, ProductService productService) {
        this.userService = userService;
        this.orderService = orderService;
        this.cartService = cartService;
        this.productService = productService;
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
            @Valid @ModelAttribute("user") User newUser,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            val errorMap = ControllerUtil.getErrors(bindingResult);
            model.mergeAttributes(errorMap);

            List<OrderOfProduct> orders = orderService.findByUser(user);
            model.addAttribute("user", user);
            model.addAttribute("orders", orders);
            return "profile";
        } else {
            userService.editUser(user, newUser);
            return "redirect:/";
        }
    }

    @GetMapping("deleteAccount")
    public String deactivateAccount(@AuthenticationPrincipal User user) {

        userService.deactivateUser(user.getId());
        return "redirect:/";
    }

    /*------------------------------Cart------------------------------*/

    @GetMapping("/cart")
    public String getCart(
            @AuthenticationPrincipal User user,
            @ModelAttribute("session_user") User session_user,
            Model model) {

        /*getActualUser() returns the user depending on whether
          he is authorized or not*/
        user = userService.getActualUser(user, session_user);

        Set<CartItem> cartItems = getCartItems(user);

        model.addAttribute("user", user);
        model.addAttribute("cartItems", cartItems);
        return "cartPage";
    }


    @GetMapping("editCart")
    public String editCart(
            @AuthenticationPrincipal User user,
            @ModelAttribute("session_user") User session_user,
            @RequestParam("cartItem_id") Long cartItem_id,
            @Valid @ModelAttribute("cart_item") CartItem cartItem,
            /*If CartItem has errors with data binding for example
              if quantity is negative or null then bindingResult
              will contain these errors*/
            BindingResult bindingResult,
            Model model) {

        user = userService.getActualUser(user, session_user);

        /*If entered data has errors then return error message back*/
        if (bindingResult.hasErrors()) {
            val errorMap = ControllerUtil.getErrors(bindingResult);
            model.mergeAttributes(errorMap);
            model.addAttribute("user", user);
            model.addAttribute("cartItems", getCartItems(user));
            return "cartPage";

        } else {
            /*Update only cart item that have current user*/
            for (CartItem u_cartItem : user.getCartItems()) {
                if (u_cartItem.getId().equals(cartItem_id)) {

                    /*Get current quantity of product*/
                    Long current_quantity = productService.findQuantityByProductId(u_cartItem.getProduct().getId());

                    /*Compare with quantity that user have entered*/
                    if (current_quantity < cartItem.getQuantity()) {

                        model.addAttribute("saveItemError", "Sorry, we do not have that amount of product");
                        model.addAttribute("user", user);
                        model.addAttribute("cartItems", getCartItems(user));
                        return "cartPage";
                    }
                    u_cartItem.setQuantity(cartItem.getQuantity());

                    /*If user authorised then save
                    cart items data in database*/
                    if (user.getId() != null) {
                        cartService.saveSet(Collections.singleton(u_cartItem));
                    }
                    return "redirect:/users/cart";
                }
            }
        }

        return "redirect:/users/cart";
    }

    @GetMapping("deleteCart/{cartItem_id}")
    public String deleteCartItem(
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

        user = userService.getActualUser(user, session_user);

        model.addAttribute("user", user);

        /*In case when cart empty return error to cart page*/
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


    @PostMapping("order_maker")
    public String makeOrder(
            @AuthenticationPrincipal User user,
            @ModelAttribute("session_user") User session_user,
            @Valid @ModelAttribute("order") OrderOfProduct order,
            BindingResult bindingResult,
            @RequestParam Long payment_id,
            @RequestParam Long delivery_id,
            Model model) {

        user = userService.getActualUser(user, session_user);

        if (bindingResult.hasErrors()) {
            val errorMap = ControllerUtil.getErrors(bindingResult);
            model.mergeAttributes(errorMap);
            model.addAttribute("types_of_payment", orderService.findAllTypesOfPayment());
            model.addAttribute("types_of_delivery", orderService.findAllTypesOfDelivery());
            model.addAttribute("user", user);
            return "editOrderPage";
        } else {

            /*Assign to order values of types of payment and delivery*/
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
                /*Total calculation: for each item -> total += quantity * (price - discount)*/
                order.setTotal(order.getTotal() + (cartItem.getQuantity() * (cartItem.getProduct().getPrice() - cartItem.getProduct().getDiscount())));
            });

            /*If user registered, add to order*/
            if (user.getId() != null) {
                order.setUser(user);
            }

            /*Clear cart in db*/
            cartService.deleteSet(user.getCartItems());
            user.getCartItems().clear();

            orderService.save(order);

            model.addAttribute("order", order);
            model.addAttribute("user", user);

            return "orderPage";
        }
    }

    @GetMapping("order/{order_id}")
    public String getOrderPage(
            @PathVariable("order_id") Long order_id,
            Model model) {

        model.addAttribute("order", orderService.findOneById(order_id));
        return "orderPage";
    }

    @GetMapping("{type}/{order_id}")
    public String cancelOrder(
            @PathVariable("order_id") Long order_id,
            @PathVariable("type") String status) {

        orderService.setOrderStatus(order_id, status);

        if (status.equals("Canceled")) {
            return "redirect:/users/profile";
        }

        return "redirect:/control_panel/orders";
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
    public String saveComment(
            @AuthenticationPrincipal User user,
            @PathVariable("product_id") Long product_id,
            @ModelAttribute("comment") Comment comment,
            Model model) {

        comment.setAuthor(user);
        comment.setProduct(productService.findOneById(product_id));

        userService.saveComment(comment);

        model.addAttribute("product", productService.findOneById(product_id));
        return "redirect:/products/" + product_id;
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
