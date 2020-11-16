package kma.kmaforms.answer;


import kma.kmaforms.exceptions.NotFoundException;
import kma.kmaforms.user.dto.AuthenticatedUser;
import kma.kmaforms.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AnswerService {
    private AnswerRepository answerRepository;

    @Autowired
    public AnswerService(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    public User registerUserIfNotExist(AuthenticatedUser user) {
        var registeredUser = answerRepository.findById(user.getEmail());
        return registeredUser.orElseGet(() -> answerRepository.save(new User(user.getEmail(), user.getDisplayName(), "User")));
    }

    public String getRole(AuthenticatedUser user) throws NotFoundException {
        var registeredUser = answerRepository.findById(user.getEmail()).orElseThrow(NotFoundException::new);
        return registeredUser.getRole();
    }
}
