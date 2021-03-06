package ua.electro.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ua.electro.models.User;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);


    User findByActivationCode(String code);

    User findOneById(Long id);

    @Transactional
    @Modifying
    @Override
    <S extends User> S save(S s);

    @Transactional
    @Modifying
    @Query("update User set active = false, password = null where id = :user_id")
    void deactivateUser(@Param("user_id") Long user_id);
}
