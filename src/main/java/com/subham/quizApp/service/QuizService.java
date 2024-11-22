package com.subham.quizApp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.subham.quizApp.model.Question;
import com.subham.quizApp.model.QuestionWrapper;
import com.subham.quizApp.model.Quiz;
import com.subham.quizApp.model.QuizResponse;
import com.subham.quizApp.repository.QuestionRepo;
import com.subham.quizApp.repository.QuizRepo;

@Service
public class QuizService {
	
	private QuizRepo quizRepo;
	private QuestionRepo quesRepo;
	
	public QuizService(QuizRepo quizRepo, QuestionRepo quesRepo)
	{
		this.quizRepo = quizRepo;
		this.quesRepo = quesRepo;
	}
	
	//Method to create a quiz by the selected category using questions from that category
	public ResponseEntity<String> createQuiz(String category, int numOfQ, String title) {
		
		//The list of questions given by the repository based on the category and number of questions provided by the user
		List<Question> quesList = quesRepo.findRandomQuestionsByCategory(category, numOfQ);
		
		//The questions are now getting stored in the Quiz
		//The Quiz will then be stored in the database
		Quiz quiz = new Quiz();
		quiz.setTitle(title);
		quiz.setQuestions(quesList);
		
		try{
			quizRepo.save(quiz);
			return new ResponseEntity<>("Quiz saved successfully", HttpStatus.CREATED);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>("Unable to create the quiz!", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	//Method to return the quiz by the ID given by the user to the user
	//A wrapper class for questions is used to send only the required fields
	public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(int id) {
		
		//Fetching the quiz by the ID
		Optional<Quiz> quiz = quizRepo.findById(id);
		
		//Storing the questions from the quiz
		List<Question> questions = quiz.get().getQuestions();
		
		//Adding the required fields from the question to the questionWrapper and sending that back to the user
		List<QuestionWrapper> questionsForUsers = new ArrayList<QuestionWrapper>();
		
		for(Question q : questions)
		{
			QuestionWrapper ques = new QuestionWrapper(q.getId(), q.getQuestionTitle(), q.getOption1(), q.getOption2(), q.getOption3(), q.getOption4());
			questionsForUsers.add(ques);
		}
		
		try {
			return new ResponseEntity<>(questionsForUsers, HttpStatus.OK);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	//Method for calculating the score of the user that attempted the quiz
	public ResponseEntity<Integer> calculateScore(int id, List<QuizResponse> response) {
		
		//Fetching the quiz by the provided ID
		Optional<Quiz> quiz = quizRepo.findById(id);
		
		//Fetching the questions from that quiz
		List<Question> questions = quiz.get().getQuestions();
		int score = 0;
		int i = 0;
		
		//Loop for checking if the option selected by the user is the right answer or not
		for(QuizResponse responses : response)
		{
			if(responses.getResponse().equals(questions.get(i).getRightAnswer()))
				score++;
			i++;
		}
		
		try {
			return new ResponseEntity<>(score, HttpStatus.OK);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
