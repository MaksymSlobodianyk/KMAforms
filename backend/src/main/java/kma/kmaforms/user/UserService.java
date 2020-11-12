package kma.kmaforms.user;


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
}
