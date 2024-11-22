package com.subham.quizApp.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.subham.quizApp.model.Question;
import com.subham.quizApp.service.QuestionService;

@RestController
@RequestMapping("question")
public class QuestionController {
	
	private QuestionService service;
	
	public QuestionController(QuestionService service)
	{
		this.service = service;
	}
	
	@GetMapping("allQuestions")
	public ResponseEntity<List<Question>> getAllQuestions()
	{
		return service.getAllQuestions();
	}
	
	@GetMapping("category/{category}")
	public ResponseEntity<List<Question>> getQuestionByCategory(@PathVariable String category)
	{
		return service.getQuestionByCategory(category);
	}
	
	@PostMapping("addQuestion")
	public ResponseEntity<String> addQuestion(@RequestBody Question question)
	{
		return service.addQuestion(question);
	}
	
	@PutMapping("update/{id}")
	public ResponseEntity<String> updateQuestion(@PathVariable int id, @RequestBody Question question)
	{
		return service.updateQuestion(id, question);
	}
	
	@DeleteMapping("delete/{id}")
	public ResponseEntity<String> deleteQuestion(@PathVariable int id)
	{
		return service.deleteQuestion(id);
	}

}
