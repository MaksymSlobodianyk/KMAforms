package kma.kmaforms.questionnaire;

import kma.kmaforms.answer.AnswerRepository;
import kma.kmaforms.chapter.ChapterRepository;
import kma.kmaforms.chapter.dto.ChapterDetailsDto;
import kma.kmaforms.chapter.model.Chapter;
import kma.kmaforms.exceptions.AlreadyFilledException;
import kma.kmaforms.exceptions.NotFoundException;
import kma.kmaforms.question.QuestionRepository;
import kma.kmaforms.question.dto.QuestionDetailsDto;
import kma.kmaforms.question.dto.QuestionWithAnswerDetailsDto;
import kma.kmaforms.question.model.Question;
import kma.kmaforms.questionnaire.dto.QuestionnaireCreationDto;
import kma.kmaforms.questionnaire.dto.QuestionnaireDetailsDto;
import kma.kmaforms.questionnaire.dto.QuestionnaireShortDetailsDto;
import kma.kmaforms.questionnaire.model.Questionnaire;
import kma.kmaforms.user.UserService;
import kma.kmaforms.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class QuestionnaireService {
    private QuestionnaireRepository questionnaireRepository;
    private ChapterRepository chapterRepository;
    private QuestionRepository questionRepository;
    private AnswerRepository answerRepository;
    private UserService userService;

    @Autowired
    public QuestionnaireService(QuestionnaireRepository questionnaireRepository, UserService userService,
                                ChapterRepository chapterRepository, QuestionRepository questionRepository,
                                AnswerRepository answerRepository) {
        this.questionnaireRepository = questionnaireRepository;
        this.userService = userService;
        this.chapterRepository = chapterRepository;
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    public void saveQuestionnaire(QuestionnaireCreationDto questionnaireDto, String currentUserEmail) throws NotFoundException {
        var currentUser = userService.getUserByEmail(currentUserEmail);
        var savedQuestionnaire = questionnaireRepository.save(
                Questionnaire.builder()
                        .title(questionnaireDto.getTitle())
                        .author(currentUser)
                        .createdAt(new Date())
                        .isActivated(false)
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

    public List<QuestionnaireShortDetailsDto> getAll(String currentUserEmail) throws NotFoundException {
        var currentUser = userService.getUserByEmail(currentUserEmail);
        return questionnaireRepository.getAllByAuthorOrderByCreatedAtDesc(currentUser)
                .stream()
                .map(questionnaire -> QuestionnaireShortDetailsDto
                        .builder()
                        .id(questionnaire.getId())
                        .title(questionnaire.getTitle())
                        .isActivated(questionnaire.isActivated())
                        .createdAt(questionnaire.getCreatedAt())
                        .authorEmail(currentUserEmail)
                        .authorDisplayName(currentUser.getDisplayName())
                        .build())
                .collect(Collectors.toList());
    }

    public void deleteQuestionnaireById(UUID questionnaireId, String currentUserEmail) throws NotFoundException {
        var currentUser = userService.getUserByEmail(currentUserEmail);
        var questionnaire = questionnaireRepository.getByIdAndAuthor(questionnaireId, currentUser).orElseThrow(NotFoundException::new);
        questionnaireRepository.delete(questionnaire);
    }


    public void checkIfAlreadyAnswered(User user, Questionnaire questionnaire)
            throws AlreadyFilledException {
        var question = chapterRepository
                .getAllByQuestionnaire(questionnaire)
                .stream().findFirst()
                .flatMap(ch ->
                        questionRepository.getAllByChapter(ch)
                                .stream().findFirst()
                );
        if (question.isPresent()) {
            var a = answerRepository
                    .getByQuestionAndAuthor(question.get(), user);
            if (a.isPresent()) {
                throw new AlreadyFilledException();
            }
        }
    }

    public QuestionnaireDetailsDto getQuestionnaireById(UUID questionnaireId, String currentUserEmail)
            throws NotFoundException, AlreadyFilledException {
        var currentUser = userService.getUserByEmail(currentUserEmail);
        var questionnaire = questionnaireRepository.getByIdAndAuthor(questionnaireId, currentUser).orElseThrow(NotFoundException::new);
        checkIfAlreadyAnswered(currentUser, questionnaire);
        return QuestionnaireDetailsDto.builder()
                .id(questionnaire.getId())
                .title(questionnaire.getTitle())
                .isActivated(questionnaire.isActivated())
                .createdAt(questionnaire.getCreatedAt())
                .authorDisplayName(currentUser.getDisplayName())
                .authorEmail(currentUserEmail)
                .chapters(chapterRepository.getAllByQuestionnaire(questionnaire)
                        .stream()
                        .map(chapter ->
                                ChapterDetailsDto.builder()
                                        .id(chapter.getId())
                                        .title(chapter.getTitle())
                                        .description(chapter.getDescription())
                                        .questions(questionRepository.getAllByChapter(chapter)
                                                .stream()
                                                .map(question -> QuestionDetailsDto.builder()
                                                        .id(question.getId())
                                                        .title(question.getTitle())
                                                        .type(question.getType())
                                                        .options(question.getOptions())
                                                        .build())
                                                .collect(Collectors.toList()))
                                        .build()
                        ).collect(Collectors.toList())
                )
                .build();
    }

    public QuestionnaireDetailsDto getQuestionnaireWithAnswers(UUID questionnaireId, String currentUserEmail, Map<UUID, String> answers)
            throws NotFoundException, AlreadyFilledException {
        var currentUser = userService.getUserByEmail(currentUserEmail);
        var questionnaire = questionnaireRepository.getByIdAndAuthor(questionnaireId, currentUser).orElseThrow(NotFoundException::new);
        checkIfAlreadyAnswered(currentUser, questionnaire);
        return QuestionnaireDetailsDto.builder()
                .id(questionnaire.getId())
                .title(questionnaire.getTitle())
                .isActivated(questionnaire.isActivated())
                .createdAt(questionnaire.getCreatedAt())
                .authorDisplayName(currentUser.getDisplayName())
                .authorEmail(currentUserEmail)
                .chapters(chapterRepository.getAllByQuestionnaire(questionnaire)
                        .stream()
                        .map(chapter ->
                                ChapterDetailsDto.builder()
                                        .id(chapter.getId())
                                        .title(chapter.getTitle())
                                        .description(chapter.getDescription())
                                        .questions(questionRepository.getAllByChapter(chapter)
                                                .stream()
                                                .map(question -> QuestionWithAnswerDetailsDto.builder()
                                                        .id(question.getId())
                                                        .title(question.getTitle())
                                                        .type(question.getType())
                                                        .options(question.getOptions())
                                                        .answer(answers.get(question.getId()))
                                                        .build())
                                                .collect(Collectors.toList()))
                                        .build()
                        ).collect(Collectors.toList())
                )
                .build();
    }
}
