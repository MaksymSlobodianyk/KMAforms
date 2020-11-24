package kma.kmaforms.user;


import kma.kmaforms.exceptions.NotFoundException;
import kma.kmaforms.user.dto.AuthenticatedUser;
import kma.kmaforms.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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

    public User getUserByEmail(String email) throws NotFoundException {
        return userRepository.findById(email).orElseThrow(NotFoundException::new);
    }
}
