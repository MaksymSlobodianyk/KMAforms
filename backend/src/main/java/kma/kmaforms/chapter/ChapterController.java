package kma.kmaforms.chapter;

import kma.kmaforms.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api/user")
public class ChapterController {

    private ChapterService chapterService;
    private AuthService authService;

    @Autowired
    public ChapterController(ChapterService chapterService, AuthService authService) {
        this.chapterService = chapterService;
        this.authService = authService;
    }
    
}
