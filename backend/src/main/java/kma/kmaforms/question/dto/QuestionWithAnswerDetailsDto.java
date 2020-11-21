package kma.kmaforms.question.dto;

import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionWithAnswerDetailsDto extends QuestionDto{
    private UUID id;
    private String title;
    private String type;
    private String options;
    private String answer;
}
