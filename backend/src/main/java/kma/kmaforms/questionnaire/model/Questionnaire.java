package kma.kmaforms.questionnaire.model;

import kma.kmaforms.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "questionnaire")
public class Questionnaire {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    UUID id;
    String title;
    @Column(name = "is_activated")
    boolean isActivated;
    @Column(name = "created_at")
    Date createdAt;
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "author_email", nullable = false)
    User author;
}
