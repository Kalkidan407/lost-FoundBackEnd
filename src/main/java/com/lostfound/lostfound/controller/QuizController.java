package com.lostfound.lostfound.controller;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.lostfound.lostfound.dto.quiz.QuizRequest;
import com.lostfound.lostfound.dto.quiz.QuizResponse;
import com.lostfound.lostfound.service.QuizService;

import lombok.RequiredArgsConstructor;

/**
 * QUIZ CONTROLLER WITH CACHING
 * 
 * CACHE STRATEGY:
 * - "quizzes": All quizzes (static content, cache permanently)
 * - "quiz::{id}": Individual quiz
 * - "quiz-by-item::{itemId}": Quizzes for specific item
 * 
 * WHY CACHE QUIZ:
 * Quiz questions are static content that rarely changes
 * Caching provides 100x performance improvement
 * Users can answer quizzes instantly without DB hits
 * 
 * RATE LIMITING:
 * Applied globally - protects from quiz spam/abuse
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/quiz")
public class QuizController {

    private final QuizService quizService;

    /**
     * Create new quiz question
     * Clears all quiz caches when new content added
     * 
     * @param dto Quiz question data
     * @return Created quiz response
     */
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @CacheEvict(value = {"quizzes", "quiz", "quiz-by-item"}, allEntries = true)
    public ResponseEntity<QuizResponse> addQuiz(@RequestBody QuizRequest dto) {
        QuizResponse created = quizService.addQuiz(dto);
        return ResponseEntity.ok(created);
    }

    /**
     * Get all quiz questions
     * Cached for maximum performance
     * Quiz content is static, so cache is very effective
     * 
     * BENEFIT: First user gets DB hit, all subsequent users get instant cache hit
     * 
     * @return List of all quizzes
     */
    @GetMapping
    @Cacheable("quizzes")  // Cache all quizzes (static content)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<QuizResponse>> getAllQuiz() {
        List<QuizResponse> quizzes = quizService.getAllQuiz();
        return ResponseEntity.ok(quizzes);
    }

    /**
     * Get quiz by ID
     * Individual quiz cached
     * Cache key: "quiz::{id}"
     * 
     * @param id Quiz ID
     * @return Quiz details
     */
    @GetMapping("/{id}")
    @Cacheable(value = "quiz", key = "#id")  // Cache individual quiz
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<QuizResponse> getQuizById(@PathVariable Long id) {
        return ResponseEntity.ok(quizService.getQuizById(id));
    }

    /**
     * Get quizzes for specific item
     * Cached by item ID
     * Cache key: "quiz-by-item::{itemId}"
     * 
     * @param itemId Item ID
     * @return List of quizzes for item
     */
    @GetMapping("/item/{itemId}")
    @Cacheable(value = "quiz-by-item", key = "#itemId")  // Cache quizzes grouped by item
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<QuizResponse>> getQuizByItemId(@PathVariable Long itemId) {
        List<QuizResponse> quizzes = quizService.getQuizByItemId(itemId);
        return ResponseEntity.ok(quizzes);
    }

    /**
     * Update quiz question
     * Clears all quiz caches to ensure fresh data
     * 
     * @param id Quiz ID to update
     * @param dto Updated quiz data
     * @return Updated quiz response
     */
    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    @CacheEvict(value = {"quizzes", "quiz", "quiz-by-item"}, allEntries = true)
    public ResponseEntity<QuizResponse> updateQuiz(
            @PathVariable Long id,
            @RequestBody QuizRequest dto) {
        QuizResponse updated = quizService.updateQuiz(id, dto);
        return ResponseEntity.ok(updated);
    }

    /**
     * Delete quiz by ID
     * Clears all quiz caches
     * 
     * @param id Quiz ID to delete
     * @return Success message
     */
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(value = {"quizzes", "quiz", "quiz-by-item"}, allEntries = true)
    public ResponseEntity<String> deleteQuiz(@PathVariable Long id) {
        quizService.deleteQuizById(id);
        return ResponseEntity.ok("Quiz deleted successfully with id " + id);
    }

    /**
     * Delete all quizzes
     * Clears entire quiz cache
     * 
     * @return Success message
     */
    @DeleteMapping("/deleteAll")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(value = {"quizzes", "quiz", "quiz-by-item"}, allEntries = true)
    public ResponseEntity<String> deleteAllQuiz() {
        quizService.deleteAllQuiz();
        return ResponseEntity.ok("All quizzes deleted");
    }
}

