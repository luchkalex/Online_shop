package ua.electro.servises;

import org.springframework.stereotype.Service;
import ua.electro.models.Product;
import ua.electro.repos.ProductRepo;

import javax.persistence.EntityManager;
import java.util.List;

@Service
public class ProductService {

    private final EntityManager entityManager;

    private final ProductRepo productRepo;

    public ProductService(ProductRepo productRepo, EntityManager entityManager) {
        this.productRepo = productRepo;
        this.entityManager = entityManager;
    }


    public void save(Product product) {
        productRepo.save(product);
    }

    public List<Product> findAll() {

        return productRepo.findAll();
    }
}
