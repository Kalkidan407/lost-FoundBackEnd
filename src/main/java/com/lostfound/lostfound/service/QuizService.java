package com.lostfound.lostfound.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.lostfound.lostfound.dto.quiz.QuizRequest;
import com.lostfound.lostfound.dto.quiz.QuizResponse;
import com.lostfound.lostfound.model.Item;
import com.lostfound.lostfound.model.Quiz;
import com.lostfound.lostfound.repository.ItemRepository;
import com.lostfound.lostfound.repository.QuizRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuizService {

    private static final String QUIZ_NOT_FOUND = "Quiz not found with id: ";

    private final QuizRepository quizRepository;
    private final ItemRepository itemRepository;

    private QuizResponse toDTO(Quiz quiz) {
        QuizResponse dto = new QuizResponse();
        dto.setId(quiz.getId());
        dto.setQuestion(quiz.getQuestion());
        dto.setAnswer(quiz.getAnswer());

        if (quiz.getItem() != null) {
            dto.setItemId(quiz.getItem().getId());
            dto.setItemName(quiz.getItem().getName());
        }

        return dto;
    }

    private Quiz fromDTO(QuizRequest request) {
        if (request == null) {
            return null;
        }

        Quiz quiz = new Quiz();
        quiz.setQuestion(request.getQuestion());
        quiz.setAnswer(request.getAnswer());

        if (request.getItemId() != null) {
            Item item = itemRepository.findById(request.getItemId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "Item not found with id: " + request.getItemId()));
            quiz.setItem(item);
        }

        return quiz;
    }

    public QuizResponse addQuiz(QuizRequest dto) {
        Quiz quiz = fromDTO(dto);
        if (quiz == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quiz request must not be null");
        }
        Quiz saved = quizRepository.save(quiz);
        return toDTO(saved);
    }

    public QuizResponse getQuizById(Long id) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, QUIZ_NOT_FOUND + id));
        return toDTO(quiz);
    }

    public List<QuizResponse> getAllQuiz() {
        List<Quiz> quizzes = quizRepository.findAll();
        return quizzes.stream().map(this::toDTO).toList();
    }

    public List<QuizResponse> getQuizByItemId(Long itemId) {
        List<Quiz> quizzes = quizRepository.findByItemId(itemId);
        return quizzes.stream().map(this::toDTO).toList();
    }

    public QuizResponse updateQuiz(Long id, QuizRequest updatedQuiz) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, QUIZ_NOT_FOUND + id));

        quiz.setQuestion(updatedQuiz.getQuestion());
        quiz.setAnswer(updatedQuiz.getAnswer());

        if (updatedQuiz.getItemId() != null && !updatedQuiz.getItemId().equals(quiz.getItem().getId())) {
            Item item = itemRepository.findById(updatedQuiz.getItemId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "Item not found with id: " + updatedQuiz.getItemId()));
            quiz.setItem(item);
        }

        Quiz saved = quizRepository.save(quiz);
        return toDTO(saved);
    }

    public void deleteQuizById(Long id) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, QUIZ_NOT_FOUND + id));
        quiz.setDeleted(true);
        quiz.setDeletedAt(LocalDateTime.now());
        quizRepository.save(quiz);
    }

    public void deleteAllQuiz() {
        quizRepository.findAll().forEach(quiz -> {
            quiz.setDeleted(true);
            quiz.setDeletedAt(LocalDateTime.now());
            quizRepository.save(quiz);
        });
    }
}
