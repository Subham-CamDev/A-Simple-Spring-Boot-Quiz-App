package com.subham.quizApp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.subham.quizApp.model.Question;
import com.subham.quizApp.repository.QuestionRepo;

@Service
public class QuestionService {
	
	private QuestionRepo repo;
	
	public QuestionService(QuestionRepo repo)
	{
		this.repo = repo;
	}
	
	//method for finding all questions
	public ResponseEntity<List<Question>> getAllQuestions() {
		
		try {
			return new ResponseEntity<>(repo.findAll(), HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
	}

	//method for finding questions by category
	public ResponseEntity<List<Question>> getQuestionByCategory(String category) {
		
		try {
			return new ResponseEntity<>(repo.findByCategory(category), HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
	}
	
	//method for adding a question
	public ResponseEntity<String> addQuestion(Question question) {
		
		try {
			repo.save(question);
			return new ResponseEntity<>("Added successfully", HttpStatus.CREATED);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>("Failed to add the question!", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	//method for updating a question
	public ResponseEntity<String> updateQuestion(int id, Question question) {
		
		Optional<Question> newQues = repo.findById(id);
		
		if(newQues.isPresent())
		{
			Question updatedQues = newQues.get();
			updatedQues.setQuestionTitle(question.getQuestionTitle());
			updatedQues.setOption1(question.getOption1());
			updatedQues.setOption2(question.getOption2());
			updatedQues.setOption3(question.getOption3());
			updatedQues.setOption4(question.getOption4());
			updatedQues.setRightAnswer(question.getRightAnswer());
			updatedQues.setDifficultyLevel(question.getDifficultyLevel());
			updatedQues.setCategory(question.getCategory());
			
			try{
				repo.save(updatedQues);
				return new ResponseEntity<>("Updated successfully", HttpStatus.OK);
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		return new ResponseEntity<>("Failed to update the question!", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	//method to find a question by the ID
//	public Question findQuestionById(int id) {
//		
//		return repo.findById(id).orElse(null);
//	}

	//method to delete a question
	public ResponseEntity<String> deleteQuestion(int id) {
		
		try {
			repo.deleteById(id);
			return new ResponseEntity<>("Deleted successfully", HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>("Failed to delete the question!", HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
