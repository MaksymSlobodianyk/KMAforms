package kma.kmaforms.question.model;

import kma.kmaforms.chapter.model.Chapter;
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
@Table(name = "question")
public class Question {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    UUID id;
    String title;
    String type;
    Integer position;
    String options;
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "chapter_id", nullable = false)
    Chapter chapter;
}