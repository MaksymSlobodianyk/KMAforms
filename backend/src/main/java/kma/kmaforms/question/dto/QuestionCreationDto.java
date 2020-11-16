package kma.kmaforms.question.dto;

import kma.kmaforms.chapter.dto.ChapterCreationDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionCreationDto {
    private String title;
    private String type;
    private String options;
}
