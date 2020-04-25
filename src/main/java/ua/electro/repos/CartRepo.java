package ua.electro.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.electro.models.CartItem;
import ua.electro.models.User;

import java.util.Set;

@Repository
public interface CartRepo extends JpaRepository<CartItem, Integer> {

    // TODO: 4/23/20 What if we can just implement method and validate this shit

    @Transactional
    @Modifying
    @Query("delete from CartItem c where c=:cart")
    void delete(@Param("cart") CartItem cartItem);

    @Transactional
    @Modifying
    @Override
    <S extends CartItem> S save(S s);

    Set<CartItem> findByUser(User user);
}
