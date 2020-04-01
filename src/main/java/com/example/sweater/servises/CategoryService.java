package com.example.sweater.servises;


import com.example.sweater.Models.Category;
import com.example.sweater.repos.CategoryRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepo categoryRepo;

    public CategoryService(CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
    }


    public List<Category> getListCategories() {
        return categoryRepo.findAll();
    }

    public Category getCategoryByID(int i) {
        Category cat = categoryRepo.getOne(i);
        System.out.println("hello");
        return cat;
    }

    public void save(Category category){
        categoryRepo.save(category);
    }


}
