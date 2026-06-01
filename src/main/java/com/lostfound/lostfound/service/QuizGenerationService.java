package com.lostfound.lostfound.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lostfound.lostfound.dto.quiz.QuizRequest;
import com.lostfound.lostfound.dto.quiz.QuizResponse;
import com.lostfound.lostfound.model.Item;
import com.lostfound.lostfound.repository.ItemRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;


@Service
@RequiredArgsConstructor
@Slf4j
public class QuizGenerationService {

	private final ChatModel chatModel;
	private final ItemRepository itemRepository;
	private final QuizService quizService;
	private final ObjectMapper objectMapper;

	public QuizResponse generateOwnershipVerificationQuiz(Long itemId) {
		Item item = itemRepository.findById(itemId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
						"Item not found with id: " + itemId));

		validateItemOwnership(item);

		String itemDetails = buildItemDetailsForAnalysis(item);
		String generatedQuizJson = generateQuizWithAI(itemDetails, item);
		QuizResponse quizResponse = parseAndCreateQuiz(generatedQuizJson, item);

		log.info("Generated ownership verification quiz for item ID: {}", itemId);
		return quizResponse;
	}

	private void validateItemOwnership(Item item) {
		if (item == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Item data is invalid");
		}
		
	}

	private String buildItemDetailsForAnalysis(Item item) {
		StringBuilder details = new StringBuilder();

		details.append("ITEM DETAILS FOR ANALYSIS:\n");
		details.append("Name: ").append(item.getName()).append("\n");
		details.append("Description: ").append(item.getDescription()).append("\n");
		details.append("Status: ").append(item.getStatus()).append("\n");

		if (item.getCategory() != null) {
			details.append("Category: ").append(item.getCategory().getName()).append("\n");
		}

		if (item.getLocation() != null) {
			details.append("Location: ").append(item.getLocation().getName()).append("\n");
		}

		if (item.getPhotos() != null && !item.getPhotos().isEmpty()) {
			details.append("Photo Count: ").append(item.getPhotos().size()).append("\n");
			details.append("Photos: ");
			for (int i = 0; i < item.getPhotos().size(); i++) {
				details.append("[Photo ").append(i + 1).append("] ");
			}
			details.append("\n");
		}

		return details.toString();
	}

	private String generateQuizWithAI(String itemDetails, Item item) {
		String template = """
				You are a security system helping verify the true owner of a lost item.
				
				Analyze the following item details and photos to generate a challenging ownership verification quiz.
				The quiz should verify that the person answering truly owns or knows this item deeply.
				
				{itemDetails}
				
				Generate ONE security verification question that:
				1. Cannot be guessed from the item name alone
				2. Requires deep knowledge of the item (from the description and photo details)
				3. Is specific to unique identifying features
				4. Has a clear, verifiable answer
				
				IMPORTANT: Respond ONLY with valid JSON in this exact format:
				{
					"question": "Your specific question here",
					"answer": "Expected answer"
				}
				
				Do NOT include any other text before or after the JSON.
				""";

		try {
			PromptTemplate promptTemplate = new PromptTemplate(template);
			Prompt prompt = promptTemplate.create(java.util.Map.of("itemDetails", itemDetails));

			ChatResponse response = chatModel.call(prompt);

			String result = response.getResult().getOutput().getText();

			result = result.trim();
			if (result.startsWith("```json")) {
				result = result.substring(7);
			}
			if (result.startsWith("```")) {
				result = result.substring(3);
			}
			if (result.endsWith("```")) {
				result = result.substring(0, result.length() - 3);
			}
			result = result.trim();

			log.debug("AI generated quiz response: {}", result);
			return result;

		} catch (Exception e) {
			log.error("Error calling AI service for quiz generation", e);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"Failed to generate quiz: " + e.getMessage());
		}
	}

	private QuizResponse parseAndCreateQuiz(String quizJson, Item item) {
		try {
			JsonNode jsonNode = objectMapper.readTree(quizJson);

			String question = jsonNode.get("question").asText();
			String answer = jsonNode.get("answer").asText();

			if (question == null || question.isBlank() || answer == null || answer.isBlank()) {
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
						"Invalid quiz data generated by AI");
			}

			QuizRequest quizRequest = new QuizRequest();
			quizRequest.setQuestion(question);
			quizRequest.setAnswer(answer);
			quizRequest.setItemId(item.getId());

			QuizResponse savedQuiz = quizService.addQuiz(quizRequest);

			log.info("Quiz saved successfully for item: {}", item.getId());
			return savedQuiz;

		} catch (Exception e) {
			log.error("Error parsing AI quiz response: {}", quizJson, e);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"Failed to process generated quiz: " + e.getMessage());
		}
	}

	public List<QuizResponse> generateMultipleOwnershipQuizzes(Long itemId, int count) {
		if (count < 1 || count > 5) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Quiz count must be between 1 and 5");
		}

		return java.util.stream.IntStream.range(0, count)
				.mapToObj(i -> {
					try {
						return generateOwnershipVerificationQuiz(itemId);
					} catch (Exception e) {
						log.warn("Failed to generate quiz #{} for item {}: {}", i + 1, itemId, e.getMessage());
						throw e;
					}
				})
				.toList();
	}
}
