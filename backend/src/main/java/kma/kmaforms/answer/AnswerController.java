package kma.kmaforms.answer;

import kma.kmaforms.answer.dto.AnswerCreationDto;
import kma.kmaforms.auth.AuthService;
import kma.kmaforms.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public void saveAnswers(@RequestBody List<AnswerCreationDto> answers) throws NotFoundException {
        answerService.saveAnswers(answers,authService.getAuthorizedUser().getEmail());

    }

    ;

}
