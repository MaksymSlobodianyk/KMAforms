package kma.kmaforms.answer;

import kma.kmaforms.answer.dto.AnswerCreationDto;
import kma.kmaforms.answer.model.Answer;
import kma.kmaforms.exceptions.AlreadyFilledException;
import kma.kmaforms.exceptions.NotFoundException;
import kma.kmaforms.exceptions.NotFoundUserException;
import kma.kmaforms.question.QuestionRepository;
import kma.kmaforms.questionnaire.QuestionnaireRepository;
import kma.kmaforms.questionnaire.QuestionnaireService;
import kma.kmaforms.questionnaire.dto.QuestionnaireDetailsDto;
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
    private QuestionnaireService questionnaireService;

    @Autowired
    public AnswerService(AnswerRepository answerRepository,
                         UserService userService,
                         QuestionnaireService questionnaireService,
                         QuestionRepository questionRepository,
                         QuestionnaireRepository questionnaireRepository) {
        this.answerRepository = answerRepository;
        this.userService = userService;
        this.questionnaireRepository = questionnaireRepository;
        this.questionRepository = questionRepository;
        this.questionnaireService = questionnaireService;
    }

    public void saveAnswers(List<AnswerCreationDto> answers, String currentUserEmail){
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

    public QuestionnaireDetailsDto getUserQuestionnaireAnswer(UUID questionnaireId, String userEmail)
            throws NotFoundException{
        Map<UUID, String> answers = new HashMap<>();
        answerRepository.getByQuestionnaireAndAuthor(questionnaireId, userEmail)
                .forEach(answer -> answers.put(answer.getQuestion().getId(), answer.getAnswer()));
        return questionnaireService.getQuestionnaireWithAnswers(questionnaireId, userEmail, answers);
    }

    public List<UserDetailsDto> getUsersWhoRepliedOnQuestionnaire(UUID questionnaireId) {
        return questionnaireService.getUsersWhoRepliedOnQuestionnaire(questionnaireId);
    }
}
