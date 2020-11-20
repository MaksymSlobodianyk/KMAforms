package kma.kmaforms.answer;

import kma.kmaforms.answer.model.Answer;
import kma.kmaforms.question.model.Question;
import kma.kmaforms.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, String> {
    Optional<Answer> getByQuestionAndAuthor(Question question, User author);
}
