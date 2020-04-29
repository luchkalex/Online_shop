package ua.electro.controllers;

import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.electro.models.*;
import ua.electro.servises.CategoryService;
import ua.electro.servises.ProductService;
import ua.electro.servises.UserService;
import ua.electro.servises.accessoryServices.ProductFilter;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Controller
@SessionAttributes("session_user")
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final UserService userService;

    public ProductController(ProductService productService, CategoryService categoryService, UserService userService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.userService = userService;
    }

    @Value("${upload.path}")
    private String uploadPath;



    /*-----------------------Add Product---------------------------*/

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/add_product_choice_category")
    public String preAddProduct(Model model) {

        model.addAttribute("categories", categoryService.findAll());
        return "preAddProduct";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/add_product")
    public String addProduct(
            @RequestParam("category_id") Integer category_id,
            Model model) {

        val features = categoryService.findFeatureOfCategoryByCategory(categoryService.findOneById(Long.valueOf(category_id)));

        model.addAttribute("features_of_cat", features);
        return "addProduct";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/add_product")
    public String addProduct(
            // TODO: 4/25/20 Price separated?
            @RequestParam("category_id") Integer category_id,
            @Valid @ModelAttribute("product") Product product,
            BindingResult bindingResult,
            @RequestParam(required = false) List<Long> features_id,
            @RequestParam("file") MultipartFile file,
            Model model) throws IOException {

        val category = categoryService.findOneById(Long.valueOf(category_id));

        if (features_id != null) {
            product.setValuesOfFeatures(categoryService.getListValuesOfFeatures(features_id));
        }

        if (category != null) {
            product.setCategory(category);
        }

        if (bindingResult.hasErrors()) {

            val errorMap = ControllerUtil.getErrors(bindingResult);
            val categories = categoryService.findAll();
            model.mergeAttributes(errorMap);
            model.addAttribute("product", product);
            model.addAttribute("categories", categories);
            return "addProduct";

        } else {

            saveFile(product, file);

            model.addAttribute("product", null);
            productService.save(product);
        }
        return "redirect:/control_panel/products";
    }

    /*-----------------------Edit Product---------------------------*/

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/edit/{product}")
    public String editProduct(
            @PathVariable("product") @ModelAttribute Product product,
            Model model) {

        val features = categoryService.findFeatureOfCategoryByCategory(product.getCategory());

        model.addAttribute("features_of_cat", features);
        model.addAttribute("type", "edit");
        model.addAttribute("product", product);
        return "addProduct";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/edit/{product_id}")
    public String editProduct(
            // TODO: 4/25/20 Price separated?
            @RequestParam("category_id") Integer category_id,
            @PathVariable("product_id") Long product_id,
            @Valid @ModelAttribute("product") Product product,
            BindingResult bindingResult,
            @RequestParam(required = false) List<Long> features_id,
            /*TODO: Add file validation on (Type, size, ect)*/
            @RequestParam("file") MultipartFile file,
            Model model) throws IOException {

        val category = categoryService.findOneById(Long.valueOf(category_id));

        product.getValuesOfFeatures().clear();

        product.setValuesOfFeatures(categoryService.getListValuesOfFeatures(features_id));

        if (category != null) {
            product.setCategory(category);
        }

        product.setId(product_id);

        if (bindingResult.hasErrors()) {
            val errorMap = ControllerUtil.getErrors(bindingResult);
            val categories = categoryService.findAll();
            model.mergeAttributes(errorMap);
            model.addAttribute("categories", categories);
            model.addAttribute("type", "edit");
            return "addProduct";
        } else {

            product.setPhoto(saveFile(product, file));
            model.addAttribute("product", null);
            productService.save(product);
        }
        return "redirect:/control_panel/products";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/add_discount/{product_id}")
    public String addDiscount(
            @PathVariable("product_id") Long product_id,
            @RequestParam("discount") Float discount) {

        Product product = productService.findOneById(product_id);

        product.setDiscount(discount);

        productService.save(product);

        return "redirect:/control_panel/products";
    }

    /*-----------------------Delete product---------------------------*/

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/delete/{product_id}")
    public String deleteProduct(
            @PathVariable("product_id") Product product) {

        productService.delete(product);

        return "redirect:/control_panel/products";
    }

    /*-----------------------Income---------------------------*/

    /*TODO: Add validation on Number format (quantity)*/
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/income/{product}")
    public String addIncome(
            @PathVariable("product") Long product_id,
            @Valid @ModelAttribute("income") Income income
    ) {
        income.setProduct(new Product(product_id));
        productService.save(income);

        return "redirect:/control_panel/products";
    }


    /*-----------------------Catalog---------------------------*/

    @GetMapping("/catalog")
    public String showCatalog(
            @AuthenticationPrincipal User user,
            @ModelAttribute("session_user") User session_user,
            @ModelAttribute("productFilter") ProductFilter productFilter,
            Model model) {

        productFilter = productService.validate(productFilter);

        val products = productService.findWithFilter(productFilter);

        user = userService.getActualUser(user, session_user);
        model.addAttribute("cartItems", user.getCartItems());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("products", products);


        model.addAttribute("pf", productFilter);
        return "catalog";
    }

    @GetMapping("/category/{category_id}")
    public String getProductOfCategory(
            @AuthenticationPrincipal User user,
            @ModelAttribute("session_user") User session_user,
            @PathVariable("category_id") Integer category_id,
            @RequestParam(required = false) List<Long> features_id,
            @ModelAttribute("productFilter") ProductFilter productFilter,
            Model model) {

        val category = new Category(Long.valueOf(category_id));

        val features = categoryService.findFeatureOfCategoryByCategory(category);

        productFilter.setCategory(category.getId());

        productFilter = productService.validate(productFilter);

        List<Product> products = productService.findWithFilter(productFilter);

        if (features_id != null) {
            products = productService.filterWithFeatures(features_id, products);
        }

        model.addAttribute("cur_user", userService.getActualUser(user, session_user));
        model.addAttribute("products", products);
        model.addAttribute("features_of_cat", features);
        model.addAttribute("type", "category");
//        model.addAttribute("cur_category", category);
        model.addAttribute("pf", productFilter);
        return "catalog";
    }

    /*-----------Product page-----------------------*/

    @GetMapping("/{product_id}")
    public String getProduct(
            @AuthenticationPrincipal User user,
            @ModelAttribute("session_user") User session_user,
            @PathVariable("product_id") Product product,
            Model model) {

        user = userService.getActualUser(user, session_user);

        boolean commented = false;

        for (Comment comment : product.getComments()) {
            if (comment.getAuthor() == user) {
                commented = true;
                break;
            }
        }

        model.addAttribute("cartItems", user.getCartItems());
        model.addAttribute("product", product);
        model.addAttribute("commented", commented);
        model.addAttribute("user", user);

        return "productPage";
    }

    /*-----------------------Cart-----------------------*/

    @GetMapping("/add_to_cart/{product_id}")
    public String addToCart(
            @AuthenticationPrincipal User user,
            @ModelAttribute("session_user") User session_user,
            @PathVariable("product_id") Product product,
            Model model
    ) {

        user = userService.getActualUser(user, session_user);

        // FIXME: 4/22/20 Cart Item use Id and primary key. It is mean that we can add similar item with identical
        //  product and user. But if I create complex key, program goes down with exception
        //  java.sql.SQLNonTransientConnectionException: Communications link failure during rollback(). Transaction resolution unknown.

        user = productService.addCartItems(user, product);

        model.addAttribute("user", user);

        return "redirect:/users/cart";
    }


    private String saveFile(Product product, @RequestParam("file") MultipartFile file) throws IOException {
        if (!Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                if (uploadDir.mkdir()) {
                    System.out.println("MkDir OK");
                }
            }

            String uuidFile = UUID.randomUUID().toString();

            String resultFileName = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFileName));

            return resultFileName;

        } else if (product.getId() != null) {

            Product prev_product = productService.findOneById(product.getId());

            if (prev_product.getPhoto() != null) {
                return prev_product.getPhoto();
            } else {
                return null;
            }

        } else {
            return null;
        }
    }

    @ModelAttribute("session_user")
    public User createUser() {
        User user = new User();
        user.setActive(true);
        return user;
    }
}
