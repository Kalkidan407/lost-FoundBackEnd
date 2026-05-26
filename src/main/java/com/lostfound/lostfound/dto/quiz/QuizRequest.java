package com.lostfound.lostfound.dto.quiz;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizRequest {

    @NotBlank(message = "question is required")
    @Size(max = 1000, message = "question too long")
    private String question;

    @NotBlank(message = "answer is required")
    @Size(max = 1000, message = "answer too long")
    private String answer;

    private Long itemId;
}
