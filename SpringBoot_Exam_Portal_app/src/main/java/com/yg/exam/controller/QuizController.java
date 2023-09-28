package com.yg.exam.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yg.exam.entity.exam.Category;
import com.yg.exam.entity.exam.Quiz;
import com.yg.exam.service.QuizService;

@RestController
@CrossOrigin("*")
@RequestMapping("/quiz")
public class QuizController {
	
	@Autowired
	private QuizService quizService;
	
	//add
	@PostMapping("/")
	public ResponseEntity<Quiz> addQuiz(@RequestBody Quiz quiz)
	{
		Quiz quiz1=this.quizService.addQuiz(quiz);
		return ResponseEntity.ok(quiz1);
	}
		
	// get quiz
	@GetMapping("/get/{qId}")
	public Quiz getQuiz(@PathVariable("qId") Long quizId)
	{
		return this.quizService.getQuiz(quizId);
	}
		
	//get all quiz
	@GetMapping("/")
	public ResponseEntity<Set<Quiz>> getAllQuiz()
	{
		Set<Quiz> set=this.quizService.getQuizzes();
		return ResponseEntity.ok(set);
	}
		
		
	//update quiz
	@PutMapping("/")
	public Quiz updateQuiz(@RequestBody Quiz quiz)
	{
		return this.quizService.updateQuiz(quiz);
	}
		
	//delete
	
	@DeleteMapping("/delete/{qId}")
	public void deleteQuiz(@PathVariable("qId") Long quizId)
	{
		this.quizService.deleteQuiz(quizId);
	}
	
	
	//get quizzes of category
	
	@GetMapping("/category/{cid}")
	public ResponseEntity<?> getQuizzesOfCategory(@PathVariable("cid") Long cid)
	{
		Category category=new Category();
		category.setCid(cid);
		return ResponseEntity.ok(this.quizService.getQuizzesOfCategory(category)); 
	}
	
	//get active quizzes
	
	@GetMapping("/active")
	public ResponseEntity<?> getActiveQuizzes()
	{
		return ResponseEntity.ok(this.quizService.getActiveQuizzes());
	}
	
	//get active quizzes of category
	
	@GetMapping("/category/active/{cid}")
	public ResponseEntity<?> getActiveQuizzes(@PathVariable("cid") long cid)
	{
		Category category=new Category();
		category.setCid(cid);
		return ResponseEntity.ok(this.quizService.getActiveQuizzesOfCategory(category));
	}

}
