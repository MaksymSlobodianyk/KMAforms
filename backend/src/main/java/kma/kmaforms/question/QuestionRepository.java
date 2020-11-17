package kma.kmaforms.question;

import kma.kmaforms.chapter.model.Chapter;
import kma.kmaforms.question.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface QuestionRepository extends JpaRepository<Question, UUID> {
    List<Question> getAllByChapter(Chapter chapter);
    Optional<Question> getById(UUID id);
}
