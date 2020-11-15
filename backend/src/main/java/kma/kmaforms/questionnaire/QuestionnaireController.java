package kma.kmaforms.questionnaire;

import kma.kmaforms.auth.AuthService;
import kma.kmaforms.exceptions.NotFoundException;
import kma.kmaforms.questionnaire.dto.QuestionnaireCreationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

}
