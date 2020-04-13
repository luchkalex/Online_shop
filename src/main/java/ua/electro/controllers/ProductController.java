package ua.electro.controllers;

import lombok.val;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.electro.models.Product;
import ua.electro.servises.ProductService;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/add_product")
    public String addProduct() {
        return "addProduct";
    }


    /*TODO: Find another way to validate price and date*/
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/add_product")
    public String add(
            @Valid Product product,
            BindingResult bindingResult,
            @RequestParam String date,
            @RequestParam String price_value,
            Model model) {

        val formatter = new SimpleDateFormat("yyyy-mm-dd");


        boolean hasErrors = false;

        if (!StringUtils.isEmpty(price_value)) {
            try {
                product.setPrice(Integer.parseInt(price_value));
            } catch (NumberFormatException e) {
                model.addAttribute("priceError", "Price can't be text");
                hasErrors = true;
            }
        } else {
            model.addAttribute("priceError", "Price can't be empty");
            hasErrors = true;
        }

        try {
            val release_date = formatter.parse(date);
            product.setRelease_date(release_date);
        } catch (ParseException e) {
            model.addAttribute("release_dateError", "Wrong date");
            hasErrors = true;
        }

        if (bindingResult.hasErrors() || hasErrors) {
            val errorMap = ControllerUtil.getErrors(bindingResult);
            model.mergeAttributes(errorMap);
            model.addAttribute("product", product);
            return "addProduct";
        } else {

            model.addAttribute("product", null);
            productService.save(product);

        }

        return "redirect:/";
    }

    @GetMapping("/catalog")
    public String showCatalog(Model model) {

        val products = productService.findAll();

        model.addAttribute("products", products);

        return "catalog";
    }


}
