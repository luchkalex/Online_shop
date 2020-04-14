package ua.electro.controllers;

import lombok.val;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.electro.servises.ProductService;

@Controller
@RequestMapping("/control_panel")
@PreAuthorize("hasAuthority('ADMIN')")
public class ControlPanelController {

    private final ProductService productService;

    public ControlPanelController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public String addProduct(Model model) {

        val products = productService.findAll();

        model.addAttribute("products", products);

        return "products_panel";
    }
}
