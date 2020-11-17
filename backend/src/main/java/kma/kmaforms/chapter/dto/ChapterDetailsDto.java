package kma.kmaforms.chapter.dto;

import kma.kmaforms.question.dto.QuestionDetailsDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChapterDetailsDto {
    private UUID id;
    private String title;
    private String description;
    private List<QuestionDetailsDto> questions;
}
