package com.example.sweater.servises;

import com.example.sweater.Models.Role;
import com.example.sweater.Models.User;
import com.example.sweater.repos.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private final UserRepo userRepo;

    private final MailSender mailSender;

    public final PasswordEncoder passwordEncoder;

    public UserService(UserRepo userRepo, MailSender mainSender, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.mailSender = mainSender;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    public boolean addUser(User user) {
        User userFromDb = userRepo.findByUsername(user.getUsername());

        if (userFromDb != null) {
            return false;
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);

        sendCodeActivationMail(user);

        return true;
    }

    private void sendCodeActivationMail(User user) {
        if (!StringUtils.isEmpty(user.getEmail())) {

            String message = String.format(
                    "Hello %s\n" +
                            "Welcome please visit : http:/localhost:8080/activate/%s",
                    user.getUsername(),
                    user.getActivationCode());

            mailSender.send(user.getEmail(), "Activation Code", message);

        }
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


    public List<User> findAll() {
        return userRepo.findAll();
    }

    public void saveUser(String username, Map<String, String> form, User user) {

        user.setUsername(username);

        user.getRoles().clear();

        /*Who knows how it works but it converts all available roles to Set<String>*/
        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());


        /*Assign to user this role if get value from form belong to available roles*/
        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }

        userRepo.save(user);
    }

    public void editUser(User user, String username, String password, String email) {

        boolean isEmailChanged = (email != null && !email.equals(user.getEmail()))
                || (user.getEmail() != null && !user.getEmail().equals(email));

        if (isEmailChanged) {
            user.setEmail(email);
            if (!StringUtils.isEmpty(email)) {
                user.setActivationCode(UUID.randomUUID().toString());
            }
        }

        if (!StringUtils.isEmpty(password)) {
            user.setPassword(passwordEncoder.encode(password));
        }

        if (!StringUtils.isEmpty(username)) {
            user.setUsername(username);
        }
        userRepo.save(user);

        if (isEmailChanged) {
            sendCodeActivationMail(user);
        }
    }
}
