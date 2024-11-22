package com.subham.quizApp.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.subham.quizApp.model.QuestionWrapper;
import com.subham.quizApp.model.QuizResponse;
import com.subham.quizApp.service.QuizService;

@RestController
@RequestMapping("quiz")
public class QuizController {
	
	private QuizService service;
	
	public QuizController(QuizService service)
	{
		this.service = service;
	}
	
	//Request for creating the quiz by the category with fixed number of questions and a quiz Title
	@PostMapping("create")
	public ResponseEntity<String> createQuiz(@RequestParam String category, @RequestParam int numOfQ, @RequestParam String title)
	{
		return service.createQuiz(category, numOfQ, title);
	}
	
	//Request for fetching the questions from the created quiz by the ID
	@GetMapping("get/{id}")
	public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable int id)
	{
		return service.getQuizQuestions(id);
	}
	
	//Request for submission of the quiz
	@PostMapping("submit/{id}")
	public ResponseEntity<Integer> submitQuiz(@PathVariable int id, @RequestBody List<QuizResponse> response)
	{
		return service.calculateScore(id, response);
	}

}
