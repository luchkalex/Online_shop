package ua.electro.repos;

import org.springframework.data.repository.CrudRepository;
import ua.electro.models.Category;
import ua.electro.models.FeaturesOfCategory;

import java.util.List;

public interface FeaturesRepo extends CrudRepository<FeaturesOfCategory, Long> {

    List<FeaturesOfCategory> findByCategory(Category category);

}
