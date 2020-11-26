package kma.kmaforms.answer;

import kma.kmaforms.answer.dto.AnswerCreationDto;
import kma.kmaforms.answer.dto.AnswerStatisticDto;
import kma.kmaforms.answer.model.Answer;
import kma.kmaforms.chapter.ChapterRepository;
import kma.kmaforms.chapter.dto.ChapterDetailsDto;
import kma.kmaforms.exceptions.NotFoundException;
import kma.kmaforms.question.QuestionRepository;
import kma.kmaforms.question.dto.QuestionStatisticDto;
import kma.kmaforms.question.dto.QuestionWithAnswerDetailsDto;
import kma.kmaforms.question.model.Question;
import kma.kmaforms.questionnaire.QuestionnaireRepository;
import kma.kmaforms.questionnaire.QuestionnaireService;
import kma.kmaforms.questionnaire.dto.QuestionnaireDetailsDto;
import kma.kmaforms.user.UserService;
import kma.kmaforms.user.dto.UserDetailsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class AnswerService {
    private AnswerRepository answerRepository;
    private UserService userService;
    private QuestionRepository questionRepository;
    private QuestionnaireRepository questionnaireRepository;
    private QuestionnaireService questionnaireService;
    private ChapterRepository chapterRepository;

    @Autowired
    public AnswerService(AnswerRepository answerRepository,
                         UserService userService,
                         QuestionnaireService questionnaireService,
                         QuestionRepository questionRepository,
                         QuestionnaireRepository questionnaireRepository,
                         ChapterRepository chapterRepository) {
        this.answerRepository = answerRepository;
        this.userService = userService;
        this.questionnaireRepository = questionnaireRepository;
        this.questionRepository = questionRepository;
        this.questionnaireService = questionnaireService;
        this.chapterRepository = chapterRepository;
    }

    public void saveAnswers(List<AnswerCreationDto> answers, String currentUserEmail) {
        var currentUser = userService.getUserByEmail(currentUserEmail);
        answers.forEach(answer -> {
                    var question = questionRepository.getById(answer.getQuestionId());
                    question.ifPresent(value -> answerRepository.save(Answer.builder()
                            .question(value)
                            .author(currentUser)
                            .answer(answer.getAnswer())
                            .build()));
                }
        );
    }

    public QuestionnaireDetailsDto getUserQuestionnaireAnswer(UUID questionnaireId, String userEmail)
            throws NotFoundException {
        Map<UUID, String> answers = new HashMap<>();
        answerRepository.getByQuestionnaireAndAuthor(questionnaireId, userEmail)
                .forEach(answer -> answers.put(answer.getQuestion().getId(), answer.getAnswer()));
        return questionnaireService.getQuestionnaireWithAnswers(questionnaireId, userEmail, answers);
    }

    public QuestionnaireDetailsDto getStatisticForQuestionnaire(UUID questionnaireId, String currentUserEmail)
            throws NotFoundException {
        var currentUser = userService.getUserByEmail(currentUserEmail);
        var questionnaire = questionnaireRepository.getByIdAndAuthor(questionnaireId, currentUser).orElseThrow(NotFoundException::new);
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
                                                .map(question -> QuestionStatisticDto.builder()
                                                        .id(question.getId())
                                                        .title(question.getTitle())
                                                        .type(question.getType())
                                                        .answers(processQuestionAnswersToStatistic(question))
                                                        .build())
                                                .collect(Collectors.toList()))
                                        .build()
                        ).collect(Collectors.toList())
                )
                .build();
    }

    private List<AnswerStatisticDto> processQuestionAnswersToStatistic(Question question) {
        var questionAnswers = answerRepository.getAllByQuestion(question);
        switch (question.getType()) {
            case "open":
                return questionAnswers.stream()
                        .map(answer -> new AnswerStatisticDto(1.0, answer.getAnswer()))
                        .collect(Collectors.toList());
            default:
                Map<String, Double> statistics = new HashMap<>();
                questionAnswers.forEach(answer -> {
                    if (statistics.get(answer.getAnswer()) != null) {
                        statistics.put(answer.getAnswer(), statistics.get(answer.getAnswer()) + 1);
                    } else {
                        statistics.put(answer.getAnswer(), 1.0);
                    }
                });
                return statistics.keySet().stream()
                        .map(key -> new AnswerStatisticDto(statistics.get(key) / questionAnswers.size(), key))
                        .collect(Collectors.toList());
        }
    }

    public List<UserDetailsDto> getUsersWhoRepliedOnQuestionnaire(UUID questionnaireId) {
        return questionnaireService.getUsersWhoRepliedOnQuestionnaire(questionnaireId);
    }
}
