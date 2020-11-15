package kma.kmaforms.question;


import kma.kmaforms.exceptions.NotFoundException;
import kma.kmaforms.user.dto.AuthenticatedUser;
import kma.kmaforms.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class QuestionService {
    private QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

}
