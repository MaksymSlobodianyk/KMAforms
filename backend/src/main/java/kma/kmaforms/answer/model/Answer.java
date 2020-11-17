package kma.kmaforms.answer.model;

import kma.kmaforms.question.model.Question;
import kma.kmaforms.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "answer")

public class Answer {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    UUID id;
    String answer;
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "question_id", nullable = false)
    Question question;
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "author_email", nullable = false)
    User author;
}
