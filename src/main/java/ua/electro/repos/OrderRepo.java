package ua.electro.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.electro.models.OrderOfProduct;
import ua.electro.models.TypesOfDelivery;
import ua.electro.models.TypesOfPayment;

import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<OrderOfProduct, Integer> {

    @Query("from TypesOfPayment")
    List<TypesOfPayment> findAllTypesOfPayment();

    @Query("from TypesOfDelivery")
    List<TypesOfDelivery> findAllTypesOfDelivery();

    @Query("from TypesOfPayment where id=:payment_id")
    TypesOfPayment findOneTypeOfPaymentById(@Param("payment_id") Long payment_id);

    @Query("from TypesOfDelivery where id=:delivery_id")
    TypesOfDelivery findOneDeliveryById(@Param("delivery_id") Long delivery_id);
}
