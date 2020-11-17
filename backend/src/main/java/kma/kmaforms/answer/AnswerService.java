package kma.kmaforms.answer;

import kma.kmaforms.answer.dto.AnswerCreationDto;
import kma.kmaforms.answer.model.Answer;
import kma.kmaforms.exceptions.NotFoundException;
import kma.kmaforms.question.QuestionRepository;
import kma.kmaforms.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AnswerService {
    private AnswerRepository answerRepository;
    private UserService userService;
    private QuestionRepository questionRepository;

    @Autowired
    public AnswerService(AnswerRepository answerRepository, UserService userService, QuestionRepository questionRepository) {
        this.answerRepository = answerRepository;
        this.userService = userService;
        this.questionRepository = questionRepository;
    }

    public void saveAnswers(List<AnswerCreationDto> answers, String currentUserEmail) throws NotFoundException {
        var currentUser = userService.getUserByEmail(currentUserEmail);
        answers.forEach(answer -> {
               var question = questionRepository.getById(answer.getQuestionId());
                question.ifPresent(value -> answerRepository.save(Answer.builder()
                        .question(value)
                        .author(currentUser)
                        .answer(answer.getAnswer())
                        .build()));
                }
        );
    }
}
