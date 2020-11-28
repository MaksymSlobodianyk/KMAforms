package kma.kmaforms.answer;

import kma.kmaforms.answer.dto.AnswerCreationDto;
import kma.kmaforms.auth.AuthService;
import kma.kmaforms.exceptions.AlreadyFilledException;
import kma.kmaforms.exceptions.ErrorMessages;
import kma.kmaforms.exceptions.NotEnoughPermissionsException;
import kma.kmaforms.exceptions.NotFoundException;
import kma.kmaforms.questionnaire.QuestionnaireRepository;
import kma.kmaforms.questionnaire.dto.QuestionnaireDetailsDto;
import kma.kmaforms.user.UserService;
import kma.kmaforms.user.dto.UserDetailsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
    public AnswerController(AnswerService answerService, AuthService authService) {
        this.answerService = answerService;
        this.authService = authService;
    }

    @PostMapping
    public void saveAnswers(@RequestBody List<AnswerCreationDto> answers){
        answerService.saveAnswers(answers, authService.getAuthorizedUser().getEmail());
    }

    @GetMapping(value = "/my")
    public QuestionnaireDetailsDto getCurrentUserAnswer(@RequestParam UUID questionnaireId) {
        try {
            return answerService.getUserQuestionnaireAnswer(questionnaireId, authService.getAuthorizedUser().getEmail());
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorMessages.QUESTIONNAIRE_NOT_FOUND, e);
        }catch (NotEnoughPermissionsException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ErrorMessages.QUESTIONNAIRE_NOT_FOUND, e);
        }

    }

    @GetMapping(value = "/user")
    public QuestionnaireDetailsDto getUserAnswer(@RequestParam UUID questionnaireId, @RequestParam String userEmail) {
        try {
            return answerService.getUserQuestionnaireAnswer(questionnaireId, userEmail);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorMessages.QUESTIONNAIRE_NOT_FOUND, e);
        }catch (NotEnoughPermissionsException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ErrorMessages.QUESTIONNAIRE_NOT_FOUND, e);
        }
    }

    @GetMapping(value = "/statistic")
    public QuestionnaireDetailsDto getQuestionnaireStatistic(@RequestParam UUID questionnaireId) {
        try {
            return answerService.getStatisticForQuestionnaire(questionnaireId,  authService.getAuthorizedUser().getEmail());
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorMessages.QUESTIONNAIRE_NOT_FOUND, e);
        }catch (NotEnoughPermissionsException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ErrorMessages.QUESTIONNAIRE_NOT_FOUND, e);
        }
    }

    @GetMapping(value = "/users")
    public List<UserDetailsDto> asa(@RequestParam UUID questionnaireId) {
        return answerService.getUsersWhoRepliedOnQuestionnaire(questionnaireId);
    }
}
