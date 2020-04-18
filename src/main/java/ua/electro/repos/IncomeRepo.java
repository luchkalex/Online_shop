package ua.electro.repos;

import org.springframework.data.repository.CrudRepository;
import ua.electro.models.Income;

public interface IncomeRepo extends CrudRepository<Income, Long> {

}
