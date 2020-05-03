package ua.electro.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.electro.models.Category;
import ua.electro.models.FeaturesOfCategory;
import ua.electro.servises.CategoryService;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping(path = "/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/add_category")
    public String addCategory(
            @ModelAttribute("category") Category category) {

        categoryService.save(category);

        return "redirect:/control_panel/features";
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
    public String addFeatureToCategory(
            @RequestParam("category_id") Long category_id,
            @RequestParam("feature_id") Long feature_id) {

        Category category = categoryService.findOneById(category_id);

        FeaturesOfCategory foc = new FeaturesOfCategory();
        foc.setCategory(category);
        foc.setFeature(categoryService.findFeatureById(feature_id));

        category.getFeaturesOfCategory().add(foc);

        categoryService.save(category);

        return "redirect:/control_panel/categories";
    }

    @GetMapping("/delete_feature")
    public String deleteFeatureFromCategory(
            @RequestParam("category_id") Long category_id,
            @RequestParam("feature_id") Long feature_id) {

        categoryService.removeFeatureOfProductByCategoryAndFeature(category_id, feature_id);

        return "redirect:/control_panel/categories";
    }

    @GetMapping("/delete_category/{category_id}")
    public String deleteCategory(
            @PathVariable("category_id") Long category_id) {

        categoryService.deleteById(category_id);

        return "redirect:/control_panel/categories";
    }

}
