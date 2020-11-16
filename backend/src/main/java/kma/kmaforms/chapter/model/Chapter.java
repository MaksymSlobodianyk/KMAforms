package kma.kmaforms.chapter.model;

import kma.kmaforms.questionnaire.model.Questionnaire;
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
@Table(name = "chapter")
public class Chapter {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    UUID id;
    String title;
    String description;
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "questionnaire_id", nullable = false)
    Questionnaire questionnaire;
}