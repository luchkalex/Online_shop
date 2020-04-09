package ua.electro.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.electro.models.Product;

public interface ProductRepo extends JpaRepository<Product, Long> {
}
