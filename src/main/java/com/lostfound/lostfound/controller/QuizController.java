package com.lostfound.lostfound.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.lostfound.lostfound.dto.quiz.QuizRequest;
import com.lostfound.lostfound.dto.quiz.QuizResponse;
import com.lostfound.lostfound.service.QuizService;
import com.lostfound.lostfound.service.QuizGenerationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/quiz")
public class QuizController {

    private final QuizService quizService;
    private final QuizGenerationService quizGenerationService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<QuizResponse> addQuiz(@RequestBody QuizRequest dto) {
        QuizResponse created = quizService.addQuiz(dto);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<QuizResponse>> getAllQuiz() {
        List<QuizResponse> quizzes = quizService.getAllQuiz();
        return ResponseEntity.ok(quizzes);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<QuizResponse> getQuizById(@PathVariable Long id) {
        return ResponseEntity.ok(quizService.getQuizById(id));
    }

    @GetMapping("/item/{itemId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<QuizResponse>> getQuizByItemId(@PathVariable Long itemId) {
        List<QuizResponse> quizzes = quizService.getQuizByItemId(itemId);
        return ResponseEntity.ok(quizzes);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<QuizResponse> updateQuiz(
            @PathVariable Long id,
            @RequestBody QuizRequest dto) {
        QuizResponse updated = quizService.updateQuiz(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteQuizById(@PathVariable Long id) {
        quizService.deleteQuizById(id);
        return ResponseEntity.ok("Quiz deleted successfully with id " + id);
    }

    @DeleteMapping("/deleteAll")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteAllQuiz() {
        quizService.deleteAllQuiz();
        return ResponseEntity.ok("All quizzes deleted");
    }

    @PostMapping("/generate/{itemId}")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<QuizResponse> generateOwnershipQuiz(@PathVariable Long itemId) {
        QuizResponse generated = quizGenerationService.generateOwnershipVerificationQuiz(itemId);
        return ResponseEntity.ok(generated);
    }

    @PostMapping("/generate/{itemId}/batch")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<QuizResponse>> generateMultipleQuizzes(
            @PathVariable Long itemId,
            @RequestParam(defaultValue = "3") int count) {
        List<QuizResponse> generated = quizGenerationService.generateMultipleOwnershipQuizzes(itemId, count);
        return ResponseEntity.ok(generated);
    }
}


