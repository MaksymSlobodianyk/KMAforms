package kma.kmaforms.questionnaire.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.UUID;
@Data
@Builder
public class QuestionnaireShortDetailsDto {
    UUID id;
    String title;
    String authorDisplayName;
    String authorEmail;
    Date createdAt;
    boolean isActivated;
}
