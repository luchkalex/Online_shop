package ua.electro.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.electro.models.Category;
import ua.electro.servises.CategoryService;


@Controller // This means that this class is a Controller
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping(path = "/categories") // This means URL's start with /demo (after Application path)
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/add_category")
    public String addCategory(
            @ModelAttribute("category") Category category) {

        categoryService.save(category);

        return "redirect:/control_panel/categories";
    }

    @GetMapping("/edit_category")
    public String editCategory(
            @RequestParam("title") String title,
            @RequestParam("category_id") Long category_id) {

        Category category = categoryService.findOneById(category_id);

        category.setTitle(title);

        categoryService.save(category);

        return "redirect:/control_panel/categories";
    }

    @GetMapping("/add_feature")
    public String addFeature(
            @RequestParam("category_id") Long category_id,
            @RequestParam("feature_id") Long feature_id) {

        Category category = categoryService.findOneById(category_id);

//        category.setTitle(title);

        categoryService.save(category);

        return "redirect:/control_panel/categories";
    }
}
