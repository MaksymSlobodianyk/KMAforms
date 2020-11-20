package kma.kmaforms.answer;

import kma.kmaforms.answer.dto.AnswerCreationDto;
import kma.kmaforms.auth.AuthService;
import kma.kmaforms.exceptions.NotFoundException;
import kma.kmaforms.questionnaire.QuestionnaireRepository;
import kma.kmaforms.user.UserService;
import kma.kmaforms.user.dto.UserDetailsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/api/answer")
public class AnswerController {

    private AnswerService answerService;
    private AuthService authService;


    @Autowired
    public AnswerController(AnswerService answerService, AuthService authService, QuestionnaireRepository questionnaireRepository, UserService userService) {
        this.answerService = answerService;
        this.authService = authService;
    }

    @PostMapping
    public void saveAnswers(@RequestBody List<AnswerCreationDto> answers) throws NotFoundException {
        answerService.saveAnswers(answers, authService.getAuthorizedUser().getEmail());

    }

    @GetMapping(value = "/my")
    public Map<UUID, String> getCurrentUserAnswer(@RequestParam UUID questionnaireId) {
        return answerService.getUserQuestionnaireAnswer(questionnaireId, authService.getAuthorizedUser().getEmail());
    }

    @GetMapping(value = "/user")
    public Map<UUID, String> getUserAnswer(@RequestParam UUID questionnaireId, @RequestParam String userEmail) {
        return answerService.getUserQuestionnaireAnswer(questionnaireId, userEmail);
    }

    @GetMapping(value = "/users")
    public List<UserDetailsDto> asa(@RequestParam UUID questionnaireId) {
        return answerService.getUsersWhoRepliedOnQuestionnaire(questionnaireId);
    }
}
