package ua.electro.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.electro.models.views.SalesStat;

import java.util.Date;

public interface SalesStatRepo extends JpaRepository<SalesStat, Date> {

}
