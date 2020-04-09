package ua.electro.servises;

import org.springframework.stereotype.Service;
import ua.electro.models.Product;
import ua.electro.repos.ProductRepo;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepo productRepo;

    public ProductService(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    public void save(Product product) {
        productRepo.save(product);
    }

    public List<Product> findAll() {
        return productRepo.findAll();
    }
}
