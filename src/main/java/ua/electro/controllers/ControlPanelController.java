package ua.electro.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.electro.servises.CategoryService;
import ua.electro.servises.ProductFilter;
import ua.electro.servises.ProductService;
import ua.electro.servises.StatusesService;

@Controller
@RequestMapping("/control_panel")
@PreAuthorize("hasAuthority('ADMIN')")
public class ControlPanelController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final StatusesService statusesService;

    public ControlPanelController(ProductService productService, CategoryService categoryService, StatusesService statusesService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.statusesService = statusesService;
    }

    /*TODO: Add validation on Number format (max and min values)*/
    /*FIXME: App failed on Filtering by category*/
    @GetMapping("/products")
    public String getProducts(
            @ModelAttribute("productFilter") ProductFilter productFilter,
            Model model) {

        model.addAttribute("products", productService.findWithFilter(productFilter));
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("statuses", statusesService.findAll());

        if (productFilter.getIdMax() == Long.MAX_VALUE) {
            productFilter.setIdMax(productService.findMaxId());
        }

        if (productFilter.getPriceMax() == Integer.MAX_VALUE) {
            productFilter.setPriceMax(productService.findMaxPrice());
        }

        if (productFilter.getQuantityMax() == Long.MAX_VALUE) {
            productFilter.setQuantityMax(productService.findMaxQuantity());
        }

        model.addAttribute("pf", productFilter);

        return "products_panel";
    }
}
