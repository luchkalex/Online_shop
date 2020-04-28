package ua.electro.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.electro.models.*;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<OrderOfProduct, Integer> {

    @Query("from TypesOfPayment")
    List<TypesOfPayment> findAllTypesOfPayment();

    @Query("from TypesOfDelivery")
    List<TypesOfDelivery> findAllTypesOfDelivery();

    OrderOfProduct findOneById(Long order_id);

    /*Order status with id = 2 - Canceled*/
    @Transactional
    @Modifying
    @Query("update OrderOfProduct set orderStatuses = (select id from OrderStatuses where title = :status) where id= :order_id")
    void setOrderStatus(
            @Param("order_id") Long order_id,
            @Param("status") String status);

    List<OrderOfProduct> findByUser(User user);

    @Query("select max(o.orderDate) from OrderOfProduct o")
    Date findMaxDate();

    @Query("select max(o.total) from OrderOfProduct o")
    Integer findMaxTotal();

    @Query("select max(o.id) from OrderOfProduct o")
    Long findMaxId();

    @Query("select min(o.orderDate) from OrderOfProduct o")
    Date findMinDate();

    @Query("from OrderStatuses")
    List<OrderStatuses> findAllStatuses();
}
