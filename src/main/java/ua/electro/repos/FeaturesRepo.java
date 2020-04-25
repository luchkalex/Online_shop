package ua.electro.repos;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ua.electro.models.Category;
import ua.electro.models.FeaturesOfCategory;
import ua.electro.models.ValueOfFeature;

import java.util.List;

public interface FeaturesRepo extends CrudRepository<FeaturesOfCategory, Long> {

    List<FeaturesOfCategory> findByCategory(Category category);

    @Query("from ValueOfFeature where id = :feature_id")
    ValueOfFeature findOneById(@Param("feature_id") Long feature_id);
}
