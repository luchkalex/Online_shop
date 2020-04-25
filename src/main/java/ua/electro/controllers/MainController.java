package ua.electro.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.electro.models.Message;
import ua.electro.models.User;
import ua.electro.repos.MessageRepo;
import ua.electro.servises.UserService;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

@Controller
@Slf4j
@SessionAttributes("session_user")
public class MainController {

    private final MessageRepo messageRepo;
    private final UserService userService;

    public MainController(MessageRepo messageRepo, UserService userService) {
        this.messageRepo = messageRepo;
        this.userService = userService;
    }


    /*If search this var in the application properties and set it here*/
    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/")
    public String greeting(
            @AuthenticationPrincipal User user,
            @ModelAttribute("user") User session_user,
            Model model) {

        model.addAttribute("user", userService.getActualUser(user, session_user));
        return "greeting";
    }


    @GetMapping("/main")
    public String main(@RequestParam(required = false) String filter, Model model) {
        Iterable<Message> messages;

        if (filter != null && !filter.isEmpty()) {
            messages = messageRepo.findByTag(filter);

        } else {
            messages = messageRepo.findAll();
        }

        model.addAttribute("messages", messages);
        model.addAttribute("filter", filter);

        messages.forEach(msg -> log.debug("Have message {}", msg.toString()));
        return "main";
    }

    /*BindingResult contains all the values of errors and in parameters set have to be before Model
     * @AuthenticationPrincipal get current user from session*/
    @PostMapping("/main")
    public String add(
            @AuthenticationPrincipal User user,
            @Valid Message message,
            BindingResult bindingResult,
            @RequestParam("file") MultipartFile file,
            Model model) throws IOException {

        message.setAuthor(user);

        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = ControllerUtil.getErrors(bindingResult);
            model.mergeAttributes(errorMap);
            model.addAttribute("message", message);
        } else {
            saveFile(message, file);
            model.addAttribute("message", null);
            messageRepo.save(message);

        }
        Iterable<Message> messages = messageRepo.findAll();

        model.addAttribute("messages", messages);

        return "main";
    }

    private void saveFile(@Valid Message message, @RequestParam("file") MultipartFile file) throws IOException {
//        if (file != null && !Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {
//            File uploadDir = new File(uploadPath);
//
//            if (!uploadDir.exists()) {
//                if (uploadDir.mkdir()) {
//                    System.out.println("MkDir OK");
//                }
//            }
//
//            String uuidFile = UUID.randomUUID().toString();
//
//            String resultFileName = uuidFile + "." + file.getOriginalFilename();
//
//            file.transferTo(new File(uploadPath + "/" + resultFileName));
//
//            message.setFilename(resultFileName);
//        }
    }

    @GetMapping("/users-messages/{user}")
    public String getUserMessages(
            @AuthenticationPrincipal User currentUser,
            @PathVariable User user,
            Model model,
            @RequestParam(required = false) Message message
    ) {

        Set<Message> messages = user.getMessages();

        model.addAttribute("message", message);
        model.addAttribute("messages", messages);
        model.addAttribute("userChannel", user);
        model.addAttribute("subscriptionsCount", user.getSubscriptions().size());
        model.addAttribute("subscribersCount", user.getSubscribers().size());
        model.addAttribute("isSubscriber", user.getSubscribers().contains(currentUser));
        model.addAttribute("isCurrentUser", currentUser.equals(user));


        return "userMessages";
    }

    @PostMapping("/users-messages/{userId}")
    public String updateMessage(
            @AuthenticationPrincipal User currentUser,
            @PathVariable Long userId,
            @RequestParam Message message,
            @RequestParam String text,
            @RequestParam String tag,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        if (message.getAuthor().equals(currentUser)) {

            if (!StringUtils.isEmpty(text)) {
                message.setText(text);
            }

            if (!StringUtils.isEmpty(tag)) {
                message.setTag(tag);
            }

            saveFile(message, file);

            messageRepo.save(message);
        }


        return "redirect:/users-messages/" + userId;
    }

    @ModelAttribute("session_user")
    public User createUser() {
        User user = new User();
        user.setActive(true);
        return user;
    }

}
