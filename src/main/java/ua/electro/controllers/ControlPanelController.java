package ua.electro.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.electro.servises.CategoryService;
import ua.electro.servises.OrderService;
import ua.electro.servises.ProductService;
import ua.electro.servises.StatService;
import ua.electro.servises.accessoryServices.OrderFilter;
import ua.electro.servises.accessoryServices.ProductFilter;

@Controller
@RequestMapping("/control_panel")
@PreAuthorize("hasAuthority('ADMIN')")
public class ControlPanelController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final OrderService orderService;
    private final StatService statService;

    public ControlPanelController(ProductService productService, CategoryService categoryService, OrderService orderService, StatService statService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.orderService = orderService;
        this.statService = statService;
    }

    /*TODO: Add validation on Number format (max and min values)*/
    /*FIXME: App failed on Filtering by category*/
    @GetMapping("/products")
    public String getProducts(
            @ModelAttribute("productFilter") ProductFilter productFilter,
            Model model) {

        productFilter = productService.validate(productFilter);

        model.addAttribute("products", productService.findWithFilter(productFilter));
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("statuses", productService.findAllProductStatuses());

        model.addAttribute("pf", productFilter);

        return "products_panel";
    }

    @GetMapping("/orders")
    public String getOrders(
            @ModelAttribute("orderFilter") OrderFilter orderFilter,
            Model model) {

        orderFilter = orderService.validate(orderFilter);

        model.addAttribute("statuses", orderService.findAllStatuses());
        model.addAttribute("payments", orderService.findAllTypesOfPayment());
        model.addAttribute("deliveries", orderService.findAllTypesOfDelivery());
        model.addAttribute("orders", orderService.findWithFilter(orderFilter));
        model.addAttribute("of", orderFilter);

        return "orders_panel";
    }

    @GetMapping("/sales_stat")
    public String getSalesStat(
            Model model) {

        model.addAttribute("sales_stat", statService.findAllSalesStat());

        return "sales_stat_panel";
    }

    @GetMapping("/categories")
    public String getCategories(
            Model model) {

        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("features", categoryService.findAllFeatures());

        return "category_panel";
    }

}
