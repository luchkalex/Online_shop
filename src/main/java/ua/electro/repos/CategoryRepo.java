package ua.electro.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.electro.models.Category;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {

    Category findOneById(Long category_id);

}
