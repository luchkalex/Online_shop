package ua.electro.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.electro.models.ValueOfFeature;

public interface ValueOfFeatureRepo extends JpaRepository<ValueOfFeature, Long> {

    ValueOfFeature findOneById(Long feature_id);
}
