package kma.kmaforms.user;

import kma.kmaforms.auth.AuthService;
import kma.kmaforms.exceptions.ErrorMessages;
import kma.kmaforms.exceptions.NotEnoughPermissionsException;
import kma.kmaforms.exceptions.NotFoundException;
import kma.kmaforms.user.dto.UserWithRoleDetailsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

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

    @PostMapping(value = "/role")
    public void setUserRole(@RequestParam Boolean makeAdmin,@RequestParam String userEmail){
        var authenticatedUser = authService.getAuthorizedUser();
        try {
            userService.setRole(makeAdmin,authenticatedUser,userEmail);
        } catch (NotEnoughPermissionsException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ErrorMessages.QUESTIONNAIRE_NOT_FOUND, e);
        }
        catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorMessages.QUESTIONNAIRE_NOT_FOUND, e);
        }
    }

    @GetMapping(value = "/all")
    public List<UserWithRoleDetailsDto> setUserRole(){
        var authenticatedUser = authService.getAuthorizedUser();
        try {
           return userService.getUsersWithRoles(authenticatedUser);
        } catch (NotEnoughPermissionsException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ErrorMessages.QUESTIONNAIRE_NOT_FOUND, e);
        }
        catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorMessages.QUESTIONNAIRE_NOT_FOUND, e);
        }
    }
}
