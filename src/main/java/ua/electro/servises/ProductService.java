package ua.electro.servises;

import org.springframework.cglib.core.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.electro.models.*;
import ua.electro.repos.IncomeRepo;
import ua.electro.repos.ProductRepo;
import ua.electro.repos.StatusesRepo;
import ua.electro.servises.accessoryServices.ProductFilter;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Service
@Transactional
public class ProductService {

    private final ProductRepo productRepo;

    private final IncomeRepo incomeRepo;
    private final StatusesRepo statusesRepo;

    private final EntityManager entityManager;

    public ProductService(ProductRepo productRepo, IncomeRepo incomeRepo, StatusesRepo statusesRepo, EntityManager entityManager) {
        this.productRepo = productRepo;
        this.incomeRepo = incomeRepo;
        this.statusesRepo = statusesRepo;
        this.entityManager = entityManager;
    }


    public void save(Product product) {
        productRepo.save(product);
    }

    public List<Product> findAll() {
        return productRepo.findAll();
    }

    public List<Product> findWithFilter(ProductFilter productFilter) {

        String additionalFilter = "";

        if (productFilter.getCategory() != null) {
            additionalFilter = additionalFilter.concat("(p.category.id = " + productFilter.getCategory() + ") and ");
        }

        if (productFilter.getProductStatus() != null) {
            additionalFilter = additionalFilter.concat("(p.productStatus.id = " + productFilter.getProductStatus() + ") and ");
        }

        Query query = entityManager.createQuery(
                "select new Product(p, q.quantity) " +
                        "from Product p " +
                        "left join QuantityOfProducts q on p.id = q.id " +
                        "where " +
                        additionalFilter +
                        "(p.id between :idMin and :idMax) and " +
                        "(p.title like concat('%', :title, '%')) and " +
                        "(p.price between :priceMin and :priceMax) and " +
                        "(q.quantity between :quantityMin and :quantityMax)");

        query.setParameter("idMin", productFilter.getIdMin());
        query.setParameter("idMax", productFilter.getIdMax());
        query.setParameter("title", productFilter.getTitle());
        query.setParameter("priceMin", productFilter.getPriceMin());
        query.setParameter("priceMax", productFilter.getPriceMax());
        query.setParameter("quantityMin", productFilter.getQuantityMin());
        query.setParameter("quantityMax", productFilter.getQuantityMax());
        query.setParameter("quantityMax", productFilter.getQuantityMax());

        return query.getResultList();
    }

    public Long findMaxId() {
        return productRepo.findOneByMaxId();
    }

    public Float findMaxPrice() {
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
            Float maxPrice = findMaxPrice();
            if (maxPrice != null) {
                productFilter.setPriceMax(maxPrice);
            } else {
                productFilter.setPriceMax(0f);
            }
        }

        if (productFilter.getIdMax() == null) {
            Long maxId = findMaxId();
            if (maxId != null) {
                productFilter.setIdMax(maxId);
            } else {
                productFilter.setIdMax(0L);
            }
        }

        if (productFilter.getQuantityMax() == null) {
            Long maxQuantity = findMaxQuantity();
            if (maxQuantity != null) {
                productFilter.setQuantityMax(maxQuantity);
            } else {
                productFilter.setQuantityMax(0L);
            }
        }

        if (productFilter.getIdMin() == null) {
            productFilter.setIdMin(0L);
        }

        if (productFilter.getQuantityMin() == null) {
            productFilter.setQuantityMin(0L);
        }

        if (productFilter.getPriceMin() == null) {
            productFilter.setPriceMin(0f);
        }

        if (productFilter.getTitle() == null) {
            productFilter.setTitle("");
        }

        if (productFilter.getCategory() != null && productFilter.getCategory() == -1) {
            productFilter.setCategory(null);
        }

        if (productFilter.getProductStatus() != null && productFilter.getProductStatus() == -1) {
            productFilter.setProductStatus(null);
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

    public List<ProductStatuses> findAllProductStatuses() {
        return statusesRepo.findAll();
    }
}
