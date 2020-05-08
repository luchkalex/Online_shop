package ua.electro.servises;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import ua.electro.models.Comment;
import ua.electro.models.Role;
import ua.electro.models.User;
import ua.electro.repos.CommentRepo;
import ua.electro.repos.UserRepo;
import ua.electro.servises.accessoryServices.MailSender;

import java.util.Collections;
import java.util.UUID;

@Service
@Transactional
public class UserService implements UserDetailsService {

    private final UserRepo userRepo;
    private final MailSender mailSender;
    public final PasswordEncoder passwordEncoder;
    private final CommentRepo commentRepo;

    public UserService(UserRepo userRepo, MailSender mainSender, PasswordEncoder passwordEncoder, CommentRepo commentRepo) {
        this.userRepo = userRepo;
        this.mailSender = mainSender;
        this.passwordEncoder = passwordEncoder;
        this.commentRepo = commentRepo;
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

        user.setActive(false);
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
                            "Welcome please visit : \n" +
                            "http://localhost:8080/activate/%s",
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
        user.setActive(true);

        userRepo.save(user);

        return true;
    }


    public void editUser(User user, User newUser) {

        boolean isEmailChanged = (newUser.getEmail() != null && !newUser.getEmail().equals(user.getEmail()))
                || (user.getEmail() != null && !user.getEmail().equals(newUser.getEmail()));

        if (isEmailChanged) {
            user.setEmail(newUser.getEmail());
            if (!StringUtils.isEmpty(newUser.getEmail())) {
                user.setActivationCode(UUID.randomUUID().toString());
            }
        }

        if (!StringUtils.isEmpty(newUser.getPassword())) {
            user.setPassword(passwordEncoder.encode(newUser.getPassword()));
        }

        if (!StringUtils.isEmpty(newUser.getUsername())) {
            user.setUsername(newUser.getUsername());
        }

        if (!StringUtils.isEmpty(newUser.getAddress())) {
            user.setAddress(newUser.getAddress());
        }

        if (!StringUtils.isEmpty(newUser.getPhone())) {
            user.setPhone(newUser.getPhone());
        }

        userRepo.save(user);

        if (isEmailChanged) {
            sendCodeActivationMail(user);
        }
    }

    public void save(User user) {
        userRepo.save(user);
    }

    public User findOneById(Long id) {
        return userRepo.findOneById(id);
    }

    public User getActualUser(@AuthenticationPrincipal User user, @ModelAttribute("user") User session_user) {
        if (user != null) {
            return findOneById(user.getId());
        } else {
            return session_user;
        }
    }

    public void deactivateUser(Long user_id) {
        userRepo.deactivateUser(user_id);
    }

    public void saveComment(Comment comment) {
        commentRepo.save(comment);
    }
}
