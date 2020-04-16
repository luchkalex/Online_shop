package ua.electro.controllers;

import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ua.electro.models.Category;
import ua.electro.models.Product;
import ua.electro.models.ProductStatuses;
import ua.electro.servises.CategoryService;
import ua.electro.servises.ProductService;
import ua.electro.servises.StatusesService;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Objects;
import java.util.UUID;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;


    public ProductController(ProductService productService, StatusesService statusesService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @Value("${upload.path}")
    private String uploadPath;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/add_product")
    public String addProduct(Model model) {
        model.addAttribute("categories", categoryService.findAll());
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
            @RequestParam("file") MultipartFile file,
            @RequestParam ProductStatuses productStatus,
            @RequestParam Category category,
            Model model) throws IOException {

        val formatter = new SimpleDateFormat("yyyy-mm-dd");


        boolean hasErrors = false;

        /*Price date validation on empty*/
        if (!StringUtils.isEmpty(price_value)) {
            /*Price date validation on int*/
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

        /*Release date validation*/
        try {
            val release_date = formatter.parse(date);
            product.setRelease_date(release_date);
        } catch (ParseException e) {
            model.addAttribute("release_dateError", "Wrong date");
            hasErrors = true;
        }

        if (productStatus != null) {
            product.setProductStatus(productStatus);
        }

        if (category != null) {
            product.setCategory(category);
        }

        if (bindingResult.hasErrors() || hasErrors) {
            val errorMap = ControllerUtil.getErrors(bindingResult);
            model.mergeAttributes(errorMap);
            model.addAttribute("product", product);
            return "addProduct";
        } else {

            saveFile(product, file);
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

    private void saveFile(Product product, @RequestParam("file") MultipartFile file) throws IOException {
        if (file != null && !Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                if (uploadDir.mkdir()) {
                    System.out.println("MkDir OK");
                }
            }

            String uuidFile = UUID.randomUUID().toString();

            String resultFileName = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFileName));

            product.setPhoto(resultFileName);
        }
    }
}
