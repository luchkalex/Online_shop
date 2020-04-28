package ua.electro.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.electro.models.TypesOfPayment;

public interface PaymentRepo extends JpaRepository<TypesOfPayment, Long> {

    TypesOfPayment findOneById(Long typeOfPayment);
}
