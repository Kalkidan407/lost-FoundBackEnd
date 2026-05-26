package com.lostfound.lostfound.dto.quiz;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizResponse {

    private Long id;
    private String question;
    private String answer;
    
    private Long itemId;
    private String itemName;
}
