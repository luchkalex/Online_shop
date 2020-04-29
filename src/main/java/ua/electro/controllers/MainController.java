package ua.electro.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("session_user")
public class MainController {

    @GetMapping("/")
    public String rootPage() {

        return "redirect:/products/catalog";
    }
}
