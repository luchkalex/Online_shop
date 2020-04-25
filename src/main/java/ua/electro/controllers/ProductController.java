package ua.electro.controllers;

import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.core.CollectionUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.electro.models.*;
import ua.electro.servises.*;

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
    private final FeatureService featuresService;
    private final UserService userService;

    public ProductController(ProductService productService, CategoryService categoryService, FeatureService featuresService, UserService userService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.featuresService = featuresService;
        this.userService = userService;
    }

    @Value("${upload.path}")
    private String uploadPath;



    /*-----------------------Mapping---------------------------*/


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

        val features = featuresService.findByCategory(categoryService.findOneById(Long.valueOf(category_id)));
        model.addAttribute("features_of_cat", features);
        return "addProduct";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/add_product")
    public String addProduct(
            /*Price separated*/
            @RequestParam("category_id") Integer category_id,
            @Valid @ModelAttribute("product") Product product,
            BindingResult bindingResult,
            @RequestParam List<Long> features_id,
            @RequestParam("file") MultipartFile file,
            Model model) throws IOException {

        val category = categoryService.findOneById(Long.valueOf(category_id));

        if (features_id != null) {
            features_id.forEach(feature_id -> {
                product.getValuesOfFeatures().add(featuresService.findOneById(feature_id));
            });
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
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("type", "edit");
        model.addAttribute("product", product);
        return "addProduct";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/edit/{product_id}")
    public String editProduct(
            /*Price separated*/
            @PathVariable("product_id") Long product_id,
            @RequestParam("category_id") Integer category_id,
            @Valid @ModelAttribute("product") Product product,
            BindingResult bindingResult,
            /*TODO: Add file validation on (Type, size, ect)*/
            @RequestParam("file") MultipartFile file,
            Model model) throws IOException {

        val category = categoryService.findOneById(Long.valueOf(category_id));

        if (Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {
            Product prev_product = productService.findOneById(product_id);
            if (prev_product.getPhoto() != null) {
                product.setPhoto(prev_product.getPhoto());
            }
        }

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

            saveFile(product, file);
            model.addAttribute("product", null);
            productService.save(product);

        }

        return "redirect:/control_panel/products";
    }

    /*-----------------------Delete product---------------------------*/

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/delete/{product_id}")
    public String deleteProduct(
            @PathVariable("product_id") Product product,
            Model model) {

        productService.delete(product);
        model.addAttribute("result", "Product deleted");
//
//            model.addAttribute("result", "Product is not deleted");
//

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

        model.addAttribute("cur_user", userService.getActualUser(user, session_user));

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
            @ModelAttribute("productFilter") ProductFilter productFilter,
            Model model) {

        val category = new Category(Long.valueOf(category_id));

        val features = featuresService.findByCategory(category);

        productFilter.setCategory(category);

        productFilter = productService.validate(productFilter);

        val products = productService.findWithFilter(productFilter);

        model.addAttribute("cur_user", userService.getActualUser(user, session_user));

        model.addAttribute("products", products);
        model.addAttribute("features_of_cat", features);
        model.addAttribute("type", "category");
        model.addAttribute("cur_category", category);
        model.addAttribute("pf", productFilter);

        return "catalog";
    }

    @PostMapping("/category/{category_id}")
    public String getProductOfCategory(
            @AuthenticationPrincipal User user,
            @ModelAttribute("session_user") User session_user,
            @PathVariable("category_id") Integer category_id,
            @RequestParam(required = false) List<Long> features_id,
            @ModelAttribute("productFilter") ProductFilter productFilter,
            Model model) {

        val category = new Category(Long.valueOf(category_id));

        productFilter = productService.validate(productFilter);

        productFilter.setCategory(category);

        List<Product> products = productService.findWithFilter(productFilter);

        /*Filtering by features*/
        if (features_id != null) {
            features_id.forEach(filter -> {
                CollectionUtils.filter(products, product -> ((Product) product).getValuesOfFeatures().contains(new ValueOfFeature(filter)));
            });
        }

        val features = featuresService.findByCategory(category);

        model.addAttribute("cur_user", userService.getActualUser(user, session_user));
        model.addAttribute("products", products);
        model.addAttribute("features_of_cat", features);
        model.addAttribute("type", "category");
        model.addAttribute("pf", productFilter);

        return "catalog";
    }

    /*-----------Product page-----------------------*/

    @GetMapping("/{product_id}")
    public String getProduct(
            @PathVariable("product_id") Product product,
            Model model) {

        model.addAttribute("product", product);

        return "productPage";
    }

    /*Add to cart*/
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

        if (user.getId() == null) {
            user.getCartItems().add(new CartItem((long) (Math.random() * Long.MAX_VALUE), user, product, 1));
        } else {
            product.getCartItems().add(new CartItem(null, user, product, 1));

            productService.save(product);
        }

        model.addAttribute("user", user);

        return "redirect:/users/cart";
    }

    @ModelAttribute("session_user")
    public User createUser() {
        return new User();
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
