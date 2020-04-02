package com.example.sweater.controllers;

import com.example.sweater.Models.Role;
import com.example.sweater.Models.User;
import com.example.sweater.repos.UserRepo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
/*This controller made for user management*/

/*PreAuthorize means that you have to have privileges to get access to this methods */
/*In this case only ADMIN has access to user list and can change it*/
@Controller
@RequestMapping("/users")
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {

    private final UserRepo userRepo;

    public UserController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    /*Returns list of users*/
    @GetMapping
    public String userList(Model model) {
        model.addAttribute(userRepo.findAll());
        return "userList";
    }

    /*Returns page of user with edit form*/
    @GetMapping("{user}")
    public String userEditForm(@PathVariable User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "userEdit";
    }


    @PostMapping
    public String saveUser(
            @RequestParam String username,
            @RequestParam Map<String, String> form,
            @RequestParam("user_id") User user) {

        user.setUsername(username);

        user.getRoles().clear();

        /*Who knows how it works but it converts all available roles to Set<String>*/
        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());


        /*Assign to user this role if get value from form belong to available roles*/
        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }

        userRepo.save(user);

        return "redirect:/users";
    }

}
