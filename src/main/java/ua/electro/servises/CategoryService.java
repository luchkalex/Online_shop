package ua.electro.servises;


import org.springframework.stereotype.Service;
import ua.electro.models.Category;
import ua.electro.models.Feature;
import ua.electro.models.FeaturesOfCategory;
import ua.electro.models.ValueOfFeature;
import ua.electro.repos.CategoryRepo;
import ua.electro.repos.FeatureOfCategoryRepo;
import ua.electro.repos.FeatureRepo;
import ua.electro.repos.ValueOfFeatureRepo;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CategoryService {

    private final CategoryRepo categoryRepo;
    private final FeatureRepo featureRepo;
    private final FeatureOfCategoryRepo featureOfCategoryRepo;
    private final ValueOfFeatureRepo valueOfFeatureRepo;

    public CategoryService(CategoryRepo categoryRepo, FeatureRepo featureRepo, FeatureOfCategoryRepo featureOfCategoryRepo, ValueOfFeatureRepo valueOfFeatureRepo) {
        this.categoryRepo = categoryRepo;
        this.featureRepo = featureRepo;
        this.featureOfCategoryRepo = featureOfCategoryRepo;
        this.valueOfFeatureRepo = valueOfFeatureRepo;
    }

    /*------------------------Category------------------------*/
    public List<Category> findAll() {
        return categoryRepo.findAll();
    }

    public void save(Category category) {
        categoryRepo.save(category);
    }

    public Category findOneById(Long category_id) {
        return categoryRepo.findOneById(category_id);
    }


    /*------------------------Features------------------------------------*/
    public List<Feature> findAllFeatures() {
        return featureRepo.findAll();
    }


    /*------------------------Feature of category------------------------*/
    public List<FeaturesOfCategory> findFeatureOfCategoryByCategory(Category category) {
        return featureOfCategoryRepo.findByCategory(category);
    }

    /*------------------------Values of feature------------------------*/
    public ValueOfFeature findOneValueOfFeatureById(Long feature_id) {
        return valueOfFeatureRepo.findOneById(feature_id);
    }

    public Set<ValueOfFeature> getListValuesOfFeatures(List<Long> features_id) {

        Set<ValueOfFeature> valuesOfFeatures = new HashSet<>();

        if (features_id != null) {
            features_id.forEach(feature_id -> valuesOfFeatures.add(findOneValueOfFeatureById(feature_id)));
        }
        return valuesOfFeatures;
    }

}
