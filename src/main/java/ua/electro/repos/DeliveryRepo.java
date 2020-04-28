package ua.electro.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.electro.models.TypesOfDelivery;

public interface DeliveryRepo extends JpaRepository<TypesOfDelivery, Long> {

    TypesOfDelivery findOneById(Long delivery_id);
}
