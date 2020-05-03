package ua.electro.controllers;

import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.electro.models.*;
import ua.electro.servises.CategoryService;
import ua.electro.servises.ProductService;
import ua.electro.servises.UserService;
import ua.electro.servises.accessoryServices.CustomFileValidator;
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
    private final CustomFileValidator customFileValidator;

    public ProductController(ProductService productService, CategoryService categoryService, UserService userService, CustomFileValidator customFileValidator) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.userService = userService;
        this.customFileValidator = customFileValidator;
    }

    @Value("${upload.path}")
    private String uploadPath;



    /*-----------------------Add Product---------------------------*/

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/add_product_choice_category")
    public String getPreAddProductPage(Model model) {

        model.addAttribute("categories", categoryService.findAll());
        return "preAddProduct";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/add_product")
    public String getAddProductPage(
            @RequestParam("category_id") Integer category_id,
            Model model) {

        val features = categoryService.findFeatureOfCategoryByCategory(categoryService.findOneById(Long.valueOf(category_id)));

        model.addAttribute("features_of_cat", features);
        return "addProduct";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/add_product")
    public String addProduct(
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

        if (!StringUtils.isEmpty(file.getOriginalFilename())) {
            customFileValidator.validate(file, bindingResult);
        }

        if (bindingResult.hasErrors()) {

            val errorMap = ControllerUtil.getErrors(bindingResult);
            val features = categoryService.findFeatureOfCategoryByCategory(categoryService.findOneById(Long.valueOf(category_id)));
            model.addAttribute("features_of_cat", features);
            model.mergeAttributes(errorMap);
            model.addAttribute("product", product);
            return "addProduct";

        } else {

            product.setPhoto(saveFile(product, file));
            product.setDiscount(0f);
            product.setRating(0f);

            model.addAttribute("product", null);
            productService.save(product);
        }
        return "redirect:/control_panel/products";
    }

    /*-----------------------Edit Product---------------------------*/

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/edit/{product}")
    public String getEditProductPage(
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
            @RequestParam("category_id") Integer category_id,
            @PathVariable("product_id") Product oldProduct,
            @Valid @ModelAttribute("newProduct") Product newProduct,
            BindingResult bindingResult,
            @RequestParam(required = false) List<Long> features_id,
            @RequestParam("file") MultipartFile file,
            Model model) throws IOException {

        val category = categoryService.findOneById(Long.valueOf(category_id));

        newProduct.getValuesOfFeatures().clear();
        newProduct.setValuesOfFeatures(categoryService.getListValuesOfFeatures(features_id));

        if (category != null) {
            newProduct.setCategory(category);
        }

        newProduct.setId(oldProduct.getId());

        newProduct.setDiscount(oldProduct.getDiscount());

        if (!StringUtils.isEmpty(file.getOriginalFilename())) {
            customFileValidator.validate(file, bindingResult);
        }

        if (bindingResult.hasErrors()) {
            val errorMap = ControllerUtil.getErrors(bindingResult);
            val features = categoryService.findFeatureOfCategoryByCategory(categoryService.findOneById(Long.valueOf(category_id)));
            model.addAttribute("features_of_cat", features);
            model.mergeAttributes(errorMap);
            model.addAttribute("product", newProduct);
            model.addAttribute("type", "edit");
            return "addProduct";
        } else {

            newProduct.setPhoto(saveFile(newProduct, file));
            model.addAttribute("newProduct", null);
            productService.save(newProduct);
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

    /*-----------------------Remove product---------------------------*/

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/delete/{product_id}")
    public String removeProduct(
            @PathVariable("product_id") Long product_id) {

        productService.remove(product_id);

        return "redirect:/control_panel/products";
    }

    /*-----------------------Income---------------------------*/

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
            @Valid @ModelAttribute("productFilter") ProductFilter productFilter,
            BindingResult bindingResult,
            Model model) {

        List<Product> products;

        if (bindingResult.hasErrors()) {
            val errorMap = ControllerUtil.getErrors(bindingResult);
            model.mergeAttributes(errorMap);
            products = productService.findAll();
        } else {
            productFilter = productService.validate(productFilter);
            products = productService.findWithFilter(productFilter);
        }

        user = userService.getActualUser(user, session_user);
        model.addAttribute("cartItems", user.getCartItems());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("products", products);

        model.addAttribute("pf", productFilter);
        return "catalog";
    }

    @GetMapping("/category/{category_id}")
    public String getCatalogByCategory(
            @AuthenticationPrincipal User user,
            @ModelAttribute("session_user") User session_user,
            @PathVariable("category_id") Integer category_id,
            @RequestParam(required = false) List<Long> features_id,
            @Valid @ModelAttribute("productFilter") ProductFilter productFilter,
            BindingResult bindingResult,
            Model model) {

        List<Product> products;

        user = userService.getActualUser(user, session_user);
        val category = new Category(Long.valueOf(category_id));

        val features = categoryService.findFeatureOfCategoryByCategory(category);

        if (bindingResult.hasErrors()) {
            val errorMap = ControllerUtil.getErrors(bindingResult);
            model.mergeAttributes(errorMap);
            products = productService.findByCategoryId(category.getId());

        } else {
            productFilter = productService.validate(productFilter);

            productFilter.setCategory(category.getId());

            products = productService.findWithFilter(productFilter);
        }

        if (features_id != null) {
            products = productService.filterWithFeatures(features_id, products);
        }

        model.addAttribute("cartItems", user.getCartItems());
        model.addAttribute("products", products);
        model.addAttribute("features_of_cat", features);
        model.addAttribute("type", "category");
        model.addAttribute("pf", productFilter);
        return "catalog";
    }

    /*-----------Product page-----------------------*/

    @GetMapping("/{product_id}")
    public String getProductPage(
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

        productService.addViewOfPage(product.getId());

        model.addAttribute("cartItems", user.getCartItems());
        model.addAttribute("product", product);
        model.addAttribute("commented", commented);
        model.addAttribute("user", user);

        return "productPage";
    }

    /*-----------------------Cart-----------------------*/

    @GetMapping("/add_to_cart/{product_id}")
    public String addProductToCart(
            @AuthenticationPrincipal User user,
            @ModelAttribute("session_user") User session_user,
            @PathVariable("product_id") Product product,
            Model model
    ) {
        user = userService.getActualUser(user, session_user);
        user = productService.addCartItems(user, product);

        model.addAttribute("user", user);
        return "redirect:/users/cart";
    }

    /**
     * Method to save
     *
     * @param file          to local repository
     *                      add path of this file to
     * @param product#photo as well
     * @return resultFileName or null
     */
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
