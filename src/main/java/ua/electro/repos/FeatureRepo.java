package ua.electro.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.electro.models.Feature;

public interface FeatureRepo extends JpaRepository<Feature, Long> {

    Feature findOneById(Long feature_id);
}
