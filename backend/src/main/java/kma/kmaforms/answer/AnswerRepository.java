package kma.kmaforms.answer;

import kma.kmaforms.answer.model.Answer;
import kma.kmaforms.question.model.Question;
import kma.kmaforms.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, String> {
    Optional<Answer> getByQuestionAndAuthor(Question question, User author);

    @Query("SELECT a from Answer a INNER JOIN Question q ON q =a.question " +
            "INNER JOIN Chapter c ON c = q.chapter " +
            "INNER JOIN Questionnaire questionnaire ON questionnaire = c.questionnaire " +
            "WHERE questionnaire.id = :questionnaireId AND a.author.email = :email")
    List<Answer> getByQuestionnaireAndAuthor(@Param("questionnaireId") UUID questionnaireId, @Param("email") String email);

    @Query("SELECT distinct a.author from Answer a INNER JOIN Question q ON q =a.question " +
            "INNER JOIN Chapter c ON c = q.chapter " +
                    "INNER JOIN Questionnaire questionnaire ON questionnaire = c.questionnaire " +
                    "WHERE questionnaire.id = :questionnaireId")
    List<User> getUsersWhoRepliedOnQuestionnaire(@Param("questionnaireId") UUID questionnaireId);
}
