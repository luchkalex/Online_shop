package com.example.sweater.servises;


import com.example.sweater.Models.Category;
import com.example.sweater.repos.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;


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
