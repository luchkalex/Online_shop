package com.example.sweater.servises;

import com.example.sweater.Models.Role;
import com.example.sweater.Models.User;
import com.example.sweater.repos.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    private final UserRepo userRepo;

    public UserService(UserRepo userRepo, MailSender mainSender) {
        this.userRepo = userRepo;
        this.mailSender = mainSender;
    }

    private final MailSender mailSender;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username);
    }

    public boolean addUser(User user) {
        User userFromDb = userRepo.findByUsername(user.getUsername());

        if (userFromDb != null) {
            return false;
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString());

        userRepo.save(user);

        if (!StringUtils.isEmpty(user.getEmail())) {

            String message = String.format(
                    "Hello %s\n" +
                            "Welcome please visit : http:/localhost:8080/activate/%s",
                    user.getUsername(),
                    user.getActivationCode());

            mailSender.send(user.getEmail(), "Activation Code", message);

        }

        return true;
    }

    public boolean activateUser(String code) {
        User user = userRepo.findByActivationCode(code);

        if (user == null) {
            return false;
        }

        user.setActivationCode(null);

        userRepo.save(user);

        return true;
    }
}
