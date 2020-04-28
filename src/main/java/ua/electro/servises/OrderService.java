package ua.electro.servises;


import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ua.electro.models.*;
import ua.electro.repos.DeliveryRepo;
import ua.electro.repos.OrderRepo;
import ua.electro.repos.PaymentRepo;
import ua.electro.servises.accessoryServices.OrderFilter;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepo orderRepo;
    private final DeliveryRepo deliveryRepo;
    private final PaymentRepo paymentRepo;
    private final EntityManager entityManager;

    public OrderService(OrderRepo orderRepo, DeliveryRepo deliveryRepo, PaymentRepo paymentRepo, EntityManager entityManager) {
        this.orderRepo = orderRepo;
        this.deliveryRepo = deliveryRepo;
        this.paymentRepo = paymentRepo;
        this.entityManager = entityManager;
    }


    public List<TypesOfPayment> findAllTypesOfPayment() {
        return orderRepo.findAllTypesOfPayment();
    }

    public List<TypesOfDelivery> findAllTypesOfDelivery() {
        return orderRepo.findAllTypesOfDelivery();
    }

    public TypesOfPayment findOneTypeOfPaymentById(Long typeOfPayment) {
        return paymentRepo.findOneById(typeOfPayment);
    }

    public TypesOfDelivery findOneDeliveryById(Long delivery_id) {
        return deliveryRepo.findOneById(delivery_id);
    }

    public void save(OrderOfProduct order) {
        orderRepo.save(order);
    }

    public OrderOfProduct findOneById(Long order_id) {
        return orderRepo.findOneById(order_id);
    }

    public void setOrderStatus(Long order_id, String status) {
        orderRepo.setOrderStatus(order_id, status);
    }

    public List<OrderOfProduct> findByUser(User user) {
        return orderRepo.findByUser(user);
    }

    public List<OrderOfProduct> findAll() {
        return orderRepo.findAll(Sort.by(Sort.Direction.ASC, "orderStatuses", "orderDate"));
    }


    public OrderFilter validate(OrderFilter orderFilter) {
        if (orderFilter.getTotalMax() == null) {
            orderFilter.setTotalMax(findMaxTotal());
        }

        if (orderFilter.getIdMax() == null) {
            orderFilter.setIdMax(findMaxId());
        }

        if (orderFilter.getDateMax() == null) {
            orderFilter.setDateMax(findMaxDate());
        }

        if (orderFilter.getIdMin() == null) {
            orderFilter.setIdMin(0L);
        }

        if (orderFilter.getTotalMin() == null) {
            orderFilter.setTotalMin(0);
        }

        if (orderFilter.getDateMin() == null) {
            orderFilter.setDateMin(findMinDate());
        }

        if (orderFilter.getUsername() == null) {
            orderFilter.setUsername("");
        }

        if (orderFilter.getAddress() == null) {
            orderFilter.setAddress("");
        }

        if (orderFilter.getDelivery() != null && orderFilter.getDelivery() == -1) {
            orderFilter.setDelivery(null);
        }

        if (orderFilter.getPayment() != null && orderFilter.getPayment() == -1) {
            orderFilter.setPayment(null);
        }

        if (orderFilter.getStatus() != null && orderFilter.getStatus() == -1) {
            orderFilter.setStatus(null);
        }

        return orderFilter;
    }

    private Date findMinDate() {
        return orderRepo.findMinDate();
    }

    private Date findMaxDate() {
        return orderRepo.findMaxDate();
    }

    private Long findMaxId() {
        return orderRepo.findMaxId();
    }

    private Integer findMaxTotal() {
        return orderRepo.findMaxTotal();
    }

    public List<OrderOfProduct> findWithFilter(OrderFilter orderFilter) {
        String additionalFilter = "";

        if (orderFilter.getStatus() != null) {
            additionalFilter = additionalFilter.concat("(o.orderStatuses.id = " + orderFilter.getStatus() + ") and ");
        }

        if (orderFilter.getPayment() != null) {
            additionalFilter = additionalFilter.concat("(o.typeOfPayment.id = " + orderFilter.getPayment() + ") and ");
        }

        if (orderFilter.getDelivery() != null) {
            additionalFilter = additionalFilter.concat("(o.typesOfDelivery.id = " + orderFilter.getDelivery() + ") and ");
        }

        Query query = entityManager.createQuery(
                "select o " +
                        "from OrderOfProduct o " +
                        "where " +
                        additionalFilter +
                        "(o.id between :idMin and :idMax) and " +
                        "(o.user.username like concat('%', :username, '%')) and " +
                        "(o.address like concat('%', :address, '%')) and " +
                        "(o.total between :totalMin and :totalMax) and " +
                        "((o.orderDate between :dateMin and :dateMax) or (o.orderDate is null)) " +
                        "order by o.orderStatuses.id, o.orderDate");

        query.setParameter("idMin", orderFilter.getIdMin());
        query.setParameter("idMax", orderFilter.getIdMax());
        query.setParameter("username", orderFilter.getUsername());
        query.setParameter("totalMin", orderFilter.getTotalMin());
        query.setParameter("totalMax", orderFilter.getTotalMax());
        query.setParameter("dateMin", orderFilter.getDateMin());
        query.setParameter("dateMax", orderFilter.getDateMax());
        query.setParameter("address", orderFilter.getAddress());

        List orders = query.getResultList();

        return orders;
    }

    public List<OrderStatuses> findAllStatuses() {
        return orderRepo.findAllStatuses();
    }
}
