package kma.kmaforms.user;


import kma.kmaforms.exceptions.ErrorMessages;
import kma.kmaforms.exceptions.NotFoundException;
import kma.kmaforms.exceptions.NotFoundUserException;
import kma.kmaforms.user.dto.AuthenticatedUser;
import kma.kmaforms.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUserIfNotExist(AuthenticatedUser user) {
        var registeredUser = userRepository.findById(user.getEmail());
        return registeredUser.orElseGet(() -> userRepository.save(new User(user.getEmail(), user.getDisplayName(), "User")));
    }

    public String getRole(AuthenticatedUser user) throws NotFoundException {
        var registeredUser = userRepository.findById(user.getEmail()).orElseThrow(NotFoundException::new);
        return registeredUser.getRole();
    }

    public boolean isAdmin(AuthenticatedUser user) throws NotFoundException {
        return getRole(user).toUpperCase().equals("ADMIN");
    }

    public User getUserByEmail(String email){
        try {
            return userRepository.findById(email).orElseThrow(NotFoundUserException::new);
        } catch (NotFoundUserException e ) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorMessages.USER_NOT_FOUND, e);
        }

    }
}
