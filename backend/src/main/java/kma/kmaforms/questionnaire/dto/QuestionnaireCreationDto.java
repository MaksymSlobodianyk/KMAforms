package kma.kmaforms.questionnaire.dto;

import kma.kmaforms.chapter.dto.ChapterCreationDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionnaireCreationDto {
    private String title;
    private List<ChapterCreationDto> chapters;
}
