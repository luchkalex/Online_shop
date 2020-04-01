package com.example.sweater.controllers;

import com.example.sweater.Models.Category;
import com.example.sweater.servises.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller // This means that this class is a Controller
@RequestMapping(path = "/cat") // This means URL's start with /demo (after Application path)
public class CategoryController {

    @Autowired
    private CategoryService categoryService;



    @GetMapping(path = "/add") // Map ONLY POST Requests
    public @ResponseBody
//    String addNewUser(@RequestParam String title) {
    String addNewUser() {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        Category category = new Category();

        category.setTitle("new cat");
        categoryService.save(category);
        return "Saved";
    }

    @GetMapping(path = "/byid")
    public @ResponseBody
    Category getCatById() {
        // This returns a JSON or XML with the users
        return categoryService.getCategoryByID(1);
    }

    @GetMapping(path = "/all")
    public @ResponseBody
    List<Category> getAllCategories(){
        return categoryService.getListCategories();
    }
//    public String getAll(){
//        return "test";
//    }


}
