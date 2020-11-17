package kma.kmaforms.answer;

import kma.kmaforms.answer.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, String> {
}
