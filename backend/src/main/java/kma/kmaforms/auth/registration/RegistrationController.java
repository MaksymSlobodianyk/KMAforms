package kma.kmaforms.auth.registration;

import kma.kmaforms.auth.AuthService;
import kma.kmaforms.user.UserService;
import kma.kmaforms.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin
@RestController
@RequestMapping("/api/register")
public class RegistrationController {

    private AuthService authService;
    private UserService userService;

    @Autowired
    public RegistrationController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }


    @PostMapping
    public User saveUserIfNotExist() {
        var authenticatedUser = authService.getAuthorizedUser();
        return userService.registerUserIfNotExist(authenticatedUser);
    }
}
