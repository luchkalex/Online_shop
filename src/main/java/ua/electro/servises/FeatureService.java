package ua.electro.servises;

import org.springframework.stereotype.Service;
import ua.electro.models.Category;
import ua.electro.models.FeaturesOfCategory;
import ua.electro.models.ValueOfFeature;
import ua.electro.repos.FeaturesRepo;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class FeatureService {

    private final FeaturesRepo featuresRepo;

    public FeatureService(FeaturesRepo featuresRepo) {
        this.featuresRepo = featuresRepo;
    }

    public List<FeaturesOfCategory> findByCategory(Category category) {
        return featuresRepo.findByCategory(category);
    }

    public ValueOfFeature findOneById(Long feature_id) {
        return featuresRepo.findOneById(feature_id);
    }

    public Set<ValueOfFeature> getSetValuesOfFeatures(List<Long> features_id) {

        Set<ValueOfFeature> valuesOfFeatures = new HashSet<>();

        if (features_id != null) {
            features_id.forEach(feature_id -> {
                valuesOfFeatures.add(findOneById(feature_id));
            });
        }
        return valuesOfFeatures;
    }
}
