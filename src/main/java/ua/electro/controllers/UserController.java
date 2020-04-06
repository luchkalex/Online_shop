package ua.electro.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.electro.models.Role;
import ua.electro.models.User;
import ua.electro.servises.UserService;

import java.util.Map;
/*This controller made for user management*/

/*PreAuthorize means that you have to have privileges to get access to this methods */
/*In this case only ADMIN has access to user list and can change it*/
@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
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

}
