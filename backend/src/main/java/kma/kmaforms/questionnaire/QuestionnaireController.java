package kma.kmaforms.questionnaire;

import kma.kmaforms.auth.AuthService;
import kma.kmaforms.exceptions.AlreadyFilledException;
import kma.kmaforms.exceptions.ErrorMessages;
import kma.kmaforms.exceptions.NotFoundException;
import kma.kmaforms.exceptions.NotFoundUserException;
import kma.kmaforms.questionnaire.dto.QuestionnaireCreationDto;
import kma.kmaforms.questionnaire.dto.QuestionnaireDetailsDto;
import kma.kmaforms.questionnaire.dto.QuestionnaireShortDetailsDto;
import kma.kmaforms.questionnaire.dto.QuestionnaireShortDetailsParticipantsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

//@CrossOrigin(origins = "http://localhost:4200")
@CrossOrigin
@RestController
@RequestMapping("/api/questionnaire")
public class QuestionnaireController {

    private QuestionnaireService questionnaireService;
    private AuthService authService;

    @Autowired
    public QuestionnaireController(QuestionnaireService questionnaireService, AuthService authService) {
        this.questionnaireService = questionnaireService;
        this.authService = authService;
    }

    @PostMapping
    public void saveQuestionnaire(@RequestBody QuestionnaireCreationDto questionnaireDto) {
        questionnaireService.saveQuestionnaire(questionnaireDto, authService.getAuthorizedUser().getEmail());
    }

    @PostMapping(value= "/enable")
    public void enableQuestionnaire(@RequestParam UUID questionnaireId) {
        try {
            questionnaireService
                    .enableQuestionnaire(questionnaireId,authService.getAuthorizedUser().getEmail(), true);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorMessages.QUESTIONNAIRE_NOT_FOUND, e);
        }
    }

    @PostMapping(value= "/disable")
    public void disableQuestionnaire(@RequestParam UUID questionnaireId){
        try {
            questionnaireService
                    .enableQuestionnaire(questionnaireId,authService.getAuthorizedUser().getEmail(), false);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorMessages.QUESTIONNAIRE_NOT_FOUND, e);
        }
    }

    @GetMapping(value = "/all/detail")
    public List<QuestionnaireShortDetailsParticipantsDto> getAllQuestionnairesWParticipants() {
        return questionnaireService.getAllWParticipants(authService.getAuthorizedUser().getEmail());
    }

    @GetMapping(value = "/all")
    public List<QuestionnaireShortDetailsDto> getAllQuestionnaires() {
       return questionnaireService.getAll(authService.getAuthorizedUser().getEmail());
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteQuestionnaireById(@RequestParam UUID questionnaireId){
        try {
            questionnaireService.deleteQuestionnaireById(questionnaireId,authService.getAuthorizedUser().getEmail());
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorMessages.QUESTIONNAIRE_NOT_FOUND, e);
        }
    }

    @GetMapping()
    public QuestionnaireDetailsDto getQuestionnaireById(@RequestParam UUID questionnaireId, @RequestParam Boolean vo) {
        try {
            return questionnaireService
                    .getQuestionnaireById(questionnaireId,authService.getAuthorizedUser().getEmail(), vo);
        } catch (AlreadyFilledException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, ErrorMessages.QUESTIONNAIRE_ALREADY_FILLED, e);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorMessages.QUESTIONNAIRE_NOT_FOUND, e);
        }

    }

}
