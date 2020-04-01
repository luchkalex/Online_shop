package com.example.sweater.repos;

import com.example.sweater.Models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Integer> {
//    @Query(value = "SELECT c FROM Category c WHERE c.name LIKE '%' || :keyword || '%'"
//            + " OR c.email LIKE '%' || :keyword || '%'"
//            + " OR c.address LIKE '%' || :keyword || '%'")
//    List<Category> search(@Param("keyword") String keyword);
}
