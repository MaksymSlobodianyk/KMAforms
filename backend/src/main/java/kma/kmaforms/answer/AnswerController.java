package kma.kmaforms.answer;

import kma.kmaforms.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    
}
