package ua.electro.servises;

import org.springframework.cglib.core.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.electro.models.*;
import ua.electro.repos.IncomeRepo;
import ua.electro.repos.ProductRepo;

import java.util.List;

@Service
@Transactional
public class ProductService {

    private final ProductRepo productRepo;

    private final IncomeRepo incomeRepo;

    public ProductService(ProductRepo productRepo, IncomeRepo incomeRepo) {
        this.productRepo = productRepo;
        this.incomeRepo = incomeRepo;
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

    public void delete(Product product) {
        productRepo.delete(product);
    }

    public void save(Income income) {
        incomeRepo.save(income);
    }

    public Product findOneById(Long product_id) {
        return productRepo.findOneById(product_id);
    }

    public List<Product> findByCategory(Category category) {
        return productRepo.findByCategory(category);
    }

    public ProductFilter validate(ProductFilter productFilter) {
        if (productFilter.getPriceMax() == null) {
            productFilter.setPriceMax(findMaxPrice());
        }

        if (productFilter.getIdMax() == null) {
            productFilter.setIdMax(findMaxId());
        }

        if (productFilter.getQuantityMax() == null) {
            productFilter.setQuantityMax(findMaxQuantity());
        }

        if (productFilter.getIdMin() == null) {
            productFilter.setIdMin(0L);
        }

        if (productFilter.getQuantityMin() == null) {
            productFilter.setQuantityMin(0L);
        }

        if (productFilter.getPriceMin() == null) {
            productFilter.setPriceMin(0);
        }

        if (productFilter.getTitle() == null) {
            productFilter.setTitle("");
        }

        return productFilter;
    }

    // FIXME: 4/26/20 Wrong logic of filtering. If we have two screen size 1000x2000 and 2000x4000
    //  we find only products that have first AND second one
    public List<Product> filterWithFeatures(List<Long> features_id, List<Product> products) {
        /*Filtering by features*/
        if (features_id != null) {
            features_id.forEach(filter -> {
                CollectionUtils.filter(products, product -> ((Product) product).getValuesOfFeatures().contains(new ValueOfFeature(filter)));
            });
        }
        return products;
    }

    public User addCartItems(User user, Product product) {
        if (user.getId() == null) {
            user.getCartItems().add(new CartItem((long) (Math.random() * Long.MAX_VALUE), user, product, 1));

        } else {
            product.getCartItems().add(new CartItem(null, user, product, 1));
            save(product);
        }
        return user;
    }
}
