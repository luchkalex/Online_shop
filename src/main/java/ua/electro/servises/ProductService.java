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

    /*FIXME: It is very bad practice*/
    public List<Product> findWithFilter(ProductFilter productFilter) {
        if (productFilter.getCategory() == null && productFilter.getProductStatus() == null) {
            return productRepo.findWithFilter(
                    productFilter.getIdMin(),
                    productFilter.getIdMax(),
                    productFilter.getTitle(),
                    productFilter.getPriceMin(),
                    productFilter.getPriceMax(),
                    productFilter.getQuantityMin(),
                    productFilter.getQuantityMax()
            );
        } else if (productFilter.getCategory() == null && productFilter.getProductStatus() != null) {
            return productRepo.findWithFilterStatus(
                    productFilter.getIdMin(),
                    productFilter.getIdMax(),
                    productFilter.getTitle(),
                    productFilter.getPriceMin(),
                    productFilter.getPriceMax(),
                    productFilter.getQuantityMin(),
                    productFilter.getQuantityMax(),
                    productFilter.getProductStatus().getId()
            );
        } else if (productFilter.getProductStatus() == null && productFilter.getCategory() != null) {
            return productRepo.findWithFilterCategory(
                    productFilter.getIdMin(),
                    productFilter.getIdMax(),
                    productFilter.getTitle(),
                    productFilter.getPriceMin(),
                    productFilter.getPriceMax(),
                    productFilter.getQuantityMin(),
                    productFilter.getQuantityMax(),
                    productFilter.getCategory().getId()
            );
        } else {
            return productRepo.findWithFilterCategoryAndStatus(
                    productFilter.getIdMin(),
                    productFilter.getIdMax(),
                    productFilter.getTitle(),
                    productFilter.getPriceMin(),
                    productFilter.getPriceMax(),
                    productFilter.getQuantityMin(),
                    productFilter.getQuantityMax(),
                    productFilter.getCategory().getId(),
                    productFilter.getProductStatus().getId());
        }
    }

    public Long findMaxId() {
        return productRepo.findOneByMaxId();
    }

    public Integer findMaxPrice() {
        return productRepo.findMaxPrice();
    }

    public Long findMaxQuantity() {
        return productRepo.findMaxQuantity();
    }
}
