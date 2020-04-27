package ua.electro.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
    Integer findMaxPrice();

    @Query("select max(q.quantity) from QuantityOfProducts q")
    Long findMaxQuantity();


    Product findOneById(Long product_id);

    List<Product> findByCategory(Category category);
}
