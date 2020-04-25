package ua.electro.servises;


import org.springframework.stereotype.Service;
import ua.electro.models.OrderOfProduct;
import ua.electro.models.TypesOfDelivery;
import ua.electro.models.TypesOfPayment;
import ua.electro.models.User;
import ua.electro.repos.OrderRepo;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepo orderRepo;


    public OrderService(OrderRepo orderRepo) {
        this.orderRepo = orderRepo;
    }


    public List<TypesOfPayment> findAllTypesOfPayment() {
        return orderRepo.findAllTypesOfPayment();
    }

    public List<TypesOfDelivery> findAllTypesOfDelivery() {
        return orderRepo.findAllTypesOfDelivery();
    }

    public TypesOfPayment findOneTypeOfPaymentById(Long typeOfPayment) {
        return orderRepo.findOneTypeOfPaymentById(typeOfPayment);
    }

    public TypesOfDelivery findOneDeliveryById(Long delivery_id) {
        return orderRepo.findOneDeliveryById(delivery_id);
    }

    public void save(OrderOfProduct order) {
        orderRepo.save(order);
    }

    public OrderOfProduct findOneById(Long order_id) {
        return orderRepo.findOneById(order_id);
    }

    public void cancelOrder(Long order_id) {
        orderRepo.cancelOrder(order_id);
    }

    public List<OrderOfProduct> findByUser(User user) {
        return orderRepo.findByUser(user);
    }
}
