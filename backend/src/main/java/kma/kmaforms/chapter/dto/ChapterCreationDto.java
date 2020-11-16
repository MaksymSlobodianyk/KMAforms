package kma.kmaforms.chapter.dto;

import kma.kmaforms.question.dto.QuestionCreationDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChapterCreationDto {
    private String title;
    private String description;
    private List<QuestionCreationDto> questions;
}
