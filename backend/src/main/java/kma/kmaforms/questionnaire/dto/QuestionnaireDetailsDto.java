package kma.kmaforms.questionnaire.dto;

import kma.kmaforms.chapter.dto.ChapterDetailsDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionnaireDetailsDto {
    UUID id;
    String title;
    String authorDisplayName;
    String authorEmail;
    Date createdAt;
    boolean isActivated;
    List<ChapterDetailsDto> chapters;
}
