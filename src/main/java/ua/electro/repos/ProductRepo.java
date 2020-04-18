package ua.electro.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.electro.models.Category;
import ua.electro.models.Product;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product, Long> {

    @Query("select new Product(p, q.quantity) " +
            "from Product p " +
            "left join QuantityOfProducts q on p.id = q.id")
    @Override
    List<Product> findAll();


    /*FIXME: Category Integer Id, to change if when fix in model*/
    @Query("select new Product(p, q.quantity) " +
            "from Product p " +
            "left join QuantityOfProducts q on p.id = q.id " +
            "where " +
            "(p.id between :idMin and :idMax) and " +
            "(p.title like concat('%', :title, '%')) and " +
            "(p.price between :priceMin and :priceMax) and " +
            "(q.quantity between :quantityMin and :quantityMax) and " +
            "(p.category.id = :category_id) and " +
            "(p.productStatus.id = :status_id)")
    List<Product> findWithFilterCategoryAndStatus(
            @Param("idMin") Long idMin,
            @Param("idMax") Long idMax,
            @Param("title") String title,
            @Param("priceMin") Integer priceMin,
            @Param("priceMax") Integer priceMax,
            @Param("quantityMin") Long quantityMin,
            @Param("quantityMax") Long quantityMax,
            @Param("category_id") Long category_id,
            @Param("status_id") Long status_id);

    @Query("select new Product(p, q.quantity) " +
            "from Product p " +
            "left join QuantityOfProducts q on p.id = q.id " +
            "where " +
            "(p.id between :idMin and :idMax) and " +
            "(p.title like concat('%', :title, '%')) and " +
            "(p.price between :priceMin and :priceMax) and " +
            "(q.quantity between :quantityMin and :quantityMax) and " +
            "(p.category.id = :category_id)")
    List<Product> findWithFilterCategory(
            @Param("idMin") Long idMin,
            @Param("idMax") Long idMax,
            @Param("title") String title,
            @Param("priceMin") Integer priceMin,
            @Param("priceMax") Integer priceMax,
            @Param("quantityMin") Long quantityMin,
            @Param("quantityMax") Long quantityMax,
            @Param("category_id") Long category_id);

    @Query("select new Product(p, q.quantity) " +
            "from Product p " +
            "left join QuantityOfProducts q on p.id = q.id " +
            "where " +
            "(p.id between :idMin and :idMax) and " +
            "(p.title like concat('%', :title, '%')) and " +
            "(p.price between :priceMin and :priceMax) and " +
            "(q.quantity between :quantityMin and :quantityMax) and " +
            "(p.productStatus.id = :status_id)")
    List<Product> findWithFilterStatus(
            @Param("idMin") Long idMin,
            @Param("idMax") Long idMax,
            @Param("title") String title,
            @Param("priceMin") Integer priceMin,
            @Param("priceMax") Integer priceMax,
            @Param("quantityMin") Long quantityMin,
            @Param("quantityMax") Long quantityMax,
            @Param("status_id") Long status_id);

    @Query("select new Product(p, q.quantity) " +
            "from Product p " +
            "left join QuantityOfProducts q on p.id = q.id " +
            "where " +
            "(p.id between :idMin and :idMax) and " +
            "(p.title like concat('%', :title, '%')) and " +
            "(p.price between :priceMin and :priceMax) and " +
            "(q.quantity between :quantityMin and :quantityMax)")
    List<Product> findWithFilter(
            @Param("idMin") Long idMin,
            @Param("idMax") Long idMax,
            @Param("title") String title,
            @Param("priceMin") Integer priceMin,
            @Param("priceMax") Integer priceMax,
            @Param("quantityMin") Long quantityMin,
            @Param("quantityMax") Long quantityMax);

    @Query("select max(p.id) from Product p")
    Long findOneByMaxId();

    @Query("select max(p.price) from Product p")
    Integer findMaxPrice();

    @Query("select max(q.quantity) from QuantityOfProducts q")
    Long findMaxQuantity();


    Product findOneById(Long product_id);

    List<Product> findByCategory(Category category);
}
