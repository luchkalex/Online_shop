package ua.electro.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.electro.models.Feature;
import ua.electro.models.ValueOfFeature;
import ua.electro.servises.CategoryService;


@Controller
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping(path = "/features")
public class FeatureController {

    private final CategoryService categoryService;

    public FeatureController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @GetMapping("/add_feature")
    public String addFeature(
            @ModelAttribute("feature") Feature feature) {

        categoryService.saveFeature(feature);

        return "redirect:/control_panel/features";
    }

    @GetMapping("/edit_feature")
    public String editFeature(
            @RequestParam("title") String title,
            @RequestParam("feature_id") Long feature_id) {

        Feature feature = categoryService.findOneFeatureById(feature_id);

        feature.setTitle(title);

        categoryService.saveFeature(feature);

        return "redirect:/control_panel/features";
    }

    @GetMapping("/add_value")
    public String addValueOfFeature(
            @RequestParam("feature_id") Long feature_id,
            @RequestParam("value_title") String value_title) {

        Feature feature = categoryService.findOneFeatureById(feature_id);

        ValueOfFeature vof = new ValueOfFeature();

        vof.setFeature(feature);
        vof.setTitle(value_title);

        categoryService.saveValueOfFeature(vof);

        return "redirect:/control_panel/features";
    }

    @GetMapping("/delete_value")
    public String deleteValueOfFeature(
            @RequestParam("value_id") Long value_id) {

        categoryService.removeValueById(value_id);

        return "redirect:/control_panel/features";
    }

    @GetMapping("/delete_feature/{feature_id}")
    public String deleteFeature(
            @PathVariable("feature_id") Long feature_id) {
        categoryService.deleteFeatureById(feature_id);

        return "redirect:/control_panel/features";
    }
}
