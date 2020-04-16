package ua.electro.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.electro.models.ProductStatuses;

public interface StatusesRepo extends JpaRepository<ProductStatuses, Long> {

}
