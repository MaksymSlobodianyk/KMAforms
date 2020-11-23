package kma.kmaforms.questionnaire.dto;

import kma.kmaforms.user.dto.UserDetailsDto;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class QuestionnaireShortDetailsParticipantsDto {
    UUID id;
    String title;
    String authorDisplayName;
    String authorEmail;
    Date createdAt;
    boolean isActivated;
    List<UserDetailsDto> participants;
}
