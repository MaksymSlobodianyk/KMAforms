package kma.kmaforms.user;

import kma.kmaforms.auth.AuthService;
import kma.kmaforms.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api/user")
public class UserController {

    private UserService userService;
    private AuthService authService;

    @Autowired
    public UserController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @GetMapping(value = "/admin")
    public boolean userIsAdmin() throws NotFoundException {
        var authenticatedUser = authService.getAuthorizedUser();
        return userService.isAdmin(authenticatedUser);
    }
}
