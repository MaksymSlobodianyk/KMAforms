package kma.kmaforms.question.dto;

import kma.kmaforms.answer.dto.AnswerStatisticDto;
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
public class QuestionStatisticDto extends QuestionDto {
    private UUID id;
    private String title;
    private String type;
    private List<AnswerStatisticDto> answer;
}
