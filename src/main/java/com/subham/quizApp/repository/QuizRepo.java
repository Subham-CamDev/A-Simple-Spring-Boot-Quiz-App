package com.subham.quizApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.subham.quizApp.model.Quiz;

@Repository
public interface QuizRepo extends JpaRepository<Quiz, Integer>{

}
