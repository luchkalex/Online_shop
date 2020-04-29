package ua.electro.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import ua.electro.models.Category;
import ua.electro.models.FeaturesOfCategory;

import java.util.List;

public interface FeatureOfCategoryRepo extends JpaRepository<FeaturesOfCategory, Long> {

    List<FeaturesOfCategory> findByCategory(Category category);


    @Transactional
    @Modifying
    void removeByCategoryIdAndFeatureId(Long category_id, Long feature_id);
}
