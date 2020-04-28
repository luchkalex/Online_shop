package ua.electro.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.electro.models.Category;
import ua.electro.models.FeaturesOfCategory;

import java.util.List;

public interface FeatureOfCategoryRepo extends JpaRepository<FeaturesOfCategory, Long> {

    List<FeaturesOfCategory> findByCategory(Category category);


}
