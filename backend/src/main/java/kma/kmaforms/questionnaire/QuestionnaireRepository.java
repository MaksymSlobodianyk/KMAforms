package kma.kmaforms.questionnaire;

import kma.kmaforms.questionnaire.model.Questionnaire;
import kma.kmaforms.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface QuestionnaireRepository extends JpaRepository<Questionnaire, UUID> {
    @Query("SELECT q FROM Questionnaire q " +
            "ORDER BY q.createdAt DESC")
    List<Questionnaire> getAllQuestionnaires();
    List<Questionnaire> getAllByAuthorOrderByCreatedAtDesc(User author);
    Optional<Questionnaire> getByIdAndAuthor(UUID id, User author);
}