package kma.kmaforms.questionnaire;

import kma.kmaforms.auth.AuthService;
import kma.kmaforms.exceptions.NotFoundException;
import kma.kmaforms.questionnaire.dto.QuestionnaireCreationDto;
import kma.kmaforms.questionnaire.dto.QuestionnaireDetailsDto;
import kma.kmaforms.questionnaire.dto.QuestionnaireShortDetailsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:4200")
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
    public void saveQuestionnaire(@RequestBody QuestionnaireCreationDto questionnaireDto) throws NotFoundException {
        questionnaireService.saveQuestionnaire(questionnaireDto, authService.getAuthorizedUser().getEmail());
    }

    @GetMapping(value = "/all")
    public List<QuestionnaireShortDetailsDto> getAllQuestionnaires() throws NotFoundException {
       return questionnaireService.getAll(authService.getAuthorizedUser().getEmail());
    }

    @GetMapping()
    public QuestionnaireDetailsDto getQuestionnaireById(@RequestParam UUID questionnaireId) throws NotFoundException {
        return questionnaireService.getQuestionnaireById(questionnaireId,authService.getAuthorizedUser().getEmail());
    }

}
