package ua.electro.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ua.electro.models.Category;
import ua.electro.models.Product;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product, Long> {

    @Query("select new Product(p, q.quantity) " +
            "from Product p " +
            "left join QuantityOfProducts q on p.id = q.id")
    @Override
    List<Product> findAll();

    @Query("select max(p.id) from Product p")
    Long findOneByMaxId();

    @Query("select max(p.price) from Product p")
    Float findMaxPrice();

    @Query("select max(q.quantity) from QuantityOfProducts q")
    Long findMaxQuantity();


    Product findOneById(Long product_id);

    List<Product> findByCategory(Category category);

    @Transactional
    @Modifying
    @Query(value = "update OnlineShop.product_statistic set viewed = viewed + 1 where id = :product_id", nativeQuery = true)
    void addView(@Param("product_id") Long product_id);

    @Transactional
    @Modifying
    @Query("update Product p set p.productStatus = (select ps.id from ProductStatuses ps where ps.title = 'Deleted') " +
            "where p.id = :product_id")
    void setStatusDeleted(Long product_id);

    @Query("select q.quantity from QuantityOfProducts q where q.id = :product_id")
    Long findQuantityByProductId(Long product_id);
}
