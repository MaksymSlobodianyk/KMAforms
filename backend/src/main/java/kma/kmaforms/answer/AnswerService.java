package kma.kmaforms.answer;

import kma.kmaforms.answer.dto.AnswerCreationDto;
import kma.kmaforms.answer.model.Answer;
import kma.kmaforms.exceptions.NotFoundException;
import kma.kmaforms.question.QuestionRepository;
import kma.kmaforms.questionnaire.QuestionnaireRepository;
import kma.kmaforms.user.UserService;
import kma.kmaforms.user.dto.UserDetailsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class AnswerService {
    private AnswerRepository answerRepository;
    private UserService userService;
    private QuestionRepository questionRepository;
    private QuestionnaireRepository questionnaireRepository;

    @Autowired
    public AnswerService(AnswerRepository answerRepository, UserService userService, QuestionRepository questionRepository, QuestionnaireRepository questionnaireRepository) {
        this.answerRepository = answerRepository;
        this.userService = userService;
        this.questionnaireRepository = questionnaireRepository;
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

    public Map<UUID, String> getUserQuestionnaireAnswer(UUID questionnaireId, String userEmail) {
        Map<UUID, String> answers = new HashMap<>();
        answerRepository.getByQuestionnaireAndAuthor(questionnaireId, userEmail)
                .forEach(answer -> answers.put(answer.getQuestion().getId(), answer.getAnswer()));
        return answers;
    }

    public List<UserDetailsDto> getUsersWhoRepliedOnQuestionnaire(UUID questionnaireId) {
        return answerRepository.getUsersWhoRepliedOnQuestionnaire(questionnaireId).stream()
                .map(user -> UserDetailsDto.builder()
                        .email(user.getEmail())
                        .displayName(user.getDisplayName())
                        .build()).collect(Collectors.toList());
    }
}
