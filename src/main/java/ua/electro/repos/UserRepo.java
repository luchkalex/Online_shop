package ua.electro.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.electro.models.User;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);


    User findByActivationCode(String code);
}
