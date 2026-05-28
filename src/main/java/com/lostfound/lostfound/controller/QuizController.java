package com.lostfound.lostfound.controller;

import java.util.List;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.lostfound.lostfound.dto.quiz.QuizRequest;
import com.lostfound.lostfound.dto.quiz.QuizResponse;
import com.lostfound.lostfound.service.QuizService;

import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/quiz")
public class QuizController {

    private final QuizService quizService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<QuizResponse> addQuiz(@RequestBody QuizRequest dto) {
        QuizResponse created = quizService.addQuiz(dto);
        return ResponseEntity.ok(created);
    }

    
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<QuizResponse>> getAllQuiz() {
        List<QuizResponse> quizzes = quizService.getAllQuiz();
        return ResponseEntity.ok(quizzes);
    }

   
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<QuizResponse> getQuizById(@PathVariable Long id) {
        return ResponseEntity.ok(quizService.getQuizById(id));
    }

  
    @GetMapping("/item/{itemId}") 
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<QuizResponse>> getQuizByItemId(@PathVariable Long itemId) {
        List<QuizResponse> quizzes = quizService.getQuizByItemId(itemId);
        return ResponseEntity.ok(quizzes);
    }

   
    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<QuizResponse> updateQuiz(
            @PathVariable Long id,
            @RequestBody QuizRequest dto) {
        QuizResponse updated = quizService.updateQuiz(id, dto);
        return ResponseEntity.ok(updated);
    }


    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<String> deleteQuizById(@PathVariable Long id) {
        quizService.deleteQuizById(id);
        return ResponseEntity.ok("Quiz deleted successfully with id " + id);
    }

    @DeleteMapping("/deleteAll")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<String> deleteAllQuiz() {
        quizService.deleteAllQuiz();
        return ResponseEntity.ok("All quizzes deleted");
    }
}

