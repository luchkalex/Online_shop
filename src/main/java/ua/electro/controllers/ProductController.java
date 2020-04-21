package ua.electro.controllers;

import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.core.CollectionUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.electro.models.Category;
import ua.electro.models.Income;
import ua.electro.models.Product;
import ua.electro.models.ValueOfFeature;
import ua.electro.servises.CategoryService;
import ua.electro.servises.FeatureService;
import ua.electro.servises.ProductFilter;
import ua.electro.servises.ProductService;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final FeatureService featuresService;

    public ProductController(ProductService productService, CategoryService categoryService, FeatureService featuresService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.featuresService = featuresService;
    }

    @Value("${upload.path}")
    private String uploadPath;



    /*-----------------------Mapping---------------------------*/


    /*-----------------------Add Product---------------------------*/

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/add_product")
    public String addProduct(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "addProduct";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/add_product")
    public String addProduct(
            /*Price separated*/
            @RequestParam("category_id") Integer category_id,
            @Valid @ModelAttribute("product") Product product,
            BindingResult bindingResult,
            @RequestParam("file") MultipartFile file,
            Model model) throws IOException {

        val category = categoryService.findOneById(Long.valueOf(category_id));


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
            @ModelAttribute("productFilter") ProductFilter productFilter,
            Model model) {

        productFilter = productService.validate(productFilter);

        val products = productService.findWithFilter(productFilter);

        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("products", products);
        model.addAttribute("pf", productFilter);

        return "catalog";
    }

    @GetMapping("/category/{category_id}")
    public String getProductOfCategory(
            @PathVariable("category_id") Integer category_id,
            @ModelAttribute("productFilter") ProductFilter productFilter,
            Model model) {

        val category = new Category(Long.valueOf(category_id));

        val features = featuresService.findByCategory(category);

        productFilter = productService.validate(productFilter);

        val products = productService.findWithFilter(productFilter);

        model.addAttribute("products", products);
        model.addAttribute("features_of_cat", features);
        model.addAttribute("type", "category");
        model.addAttribute("cur_category", category);
        model.addAttribute("pf", productFilter);

        return "catalog";
    }

    @PostMapping("/category/{category_id}")
    public String getProductOfCategory(
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

        model.addAttribute("products", products);
        model.addAttribute("features_of_cat", features);
        model.addAttribute("type", "category");
        model.addAttribute("pf", productFilter);

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
