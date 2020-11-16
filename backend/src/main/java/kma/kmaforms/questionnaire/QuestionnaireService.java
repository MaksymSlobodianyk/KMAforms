package kma.kmaforms.questionnaire;

import kma.kmaforms.chapter.ChapterRepository;
import kma.kmaforms.chapter.model.Chapter;
import kma.kmaforms.exceptions.NotFoundException;
import kma.kmaforms.question.QuestionRepository;
import kma.kmaforms.question.model.Question;
import kma.kmaforms.questionnaire.dto.QuestionnaireCreationDto;
import kma.kmaforms.questionnaire.model.Questionnaire;
import kma.kmaforms.user.UserRepository;
import kma.kmaforms.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class QuestionnaireService {
    private QuestionnaireRepository questionnaireRepository;
    private ChapterRepository chapterRepository;
    private QuestionRepository questionRepository;
    private UserService userService;

    @Autowired
    public QuestionnaireService(QuestionnaireRepository questionnaireRepository, UserService userService, ChapterRepository chapterRepository, QuestionRepository questionRepository) {
        this.questionnaireRepository = questionnaireRepository;
        this.userService = userService;
        this.chapterRepository = chapterRepository;
        this.questionRepository = questionRepository;
    }

    public void saveQuestionnaire(QuestionnaireCreationDto questionnaireDto, String currentUserEmail) throws NotFoundException {
        var currentUser = userService.getUserByEmail(currentUserEmail);
        var savedQuestionnaire = questionnaireRepository.save(
                Questionnaire.builder()
                        .title(questionnaireDto.getTitle())
                        .author(currentUser)
                        .build()
        );
        questionnaireDto.getChapters().forEach(chapter -> {
            var savedChapter = chapterRepository.save(
                    Chapter.builder()
                            .title(chapter.getTitle())
                            .description(chapter.getDescription())
                            .questionnaire(savedQuestionnaire)
                            .build()
            );
            Integer questionNumber = 0;
            var chapterQuestions = chapter.getQuestions();
            for (int i = 0; i < chapterQuestions.size(); i++)
                questionRepository.save(
                        Question.builder()
                                .title(chapterQuestions.get(i).getTitle())
                                .type(chapterQuestions.get(i).getType())
                                .position(i + 1)
                                .options(chapterQuestions.get(i).getOptions())
                                .chapter(savedChapter)
                                .build()
                );
        });
    }
}
