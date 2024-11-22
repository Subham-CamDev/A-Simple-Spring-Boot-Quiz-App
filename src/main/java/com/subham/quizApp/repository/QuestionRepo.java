package com.subham.quizApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.subham.quizApp.model.Question;

@Repository
public interface QuestionRepo extends JpaRepository<Question, Integer>{

	List<Question> findByCategory(String category);
	
	@Query(value = "select * from question q where q.category=:category order by RAND() limit :numOfQ", nativeQuery = true)
	List<Question> findRandomQuestionsByCategory(String category, int numOfQ);

}
