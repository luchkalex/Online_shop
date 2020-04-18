package ua.electro.servises;


import org.springframework.stereotype.Service;
import ua.electro.models.Category;
import ua.electro.repos.CategoryRepo;

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

    public List<Category> findAll() {
        return categoryRepo.findAll();
    }

    public void save(Category category) {
        categoryRepo.save(category);
    }


    public Category findOneById(Long category_id) {
        return categoryRepo.findOneById(category_id);
    }

}
