package kma.kmaforms.answer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnswerCreationDto {
    private UUID  questionId;
    private String answer;
}
