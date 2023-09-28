package com.yg.exam.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
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

import com.yg.exam.entity.exam.Question;
import com.yg.exam.entity.exam.Quiz;
import com.yg.exam.service.QuestionService;
import com.yg.exam.service.QuizService;

import jakarta.transaction.Transactional;

@RestController
@CrossOrigin("*")
@RequestMapping("/question")
public class QuestionController {
	
	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private QuizService quizService;
	
	
	//add
	@PostMapping("/")
	public ResponseEntity<Question> addQuestion(@RequestBody Question question)
	{
		Question question1=this.questionService.addQuestion(question);
		return ResponseEntity.ok(question1);
	}
			
	// get single Question		
	@GetMapping("/get/{questionId}")		
	public Question getQuestion(@PathVariable("questionId") Long questionId)		
	{
		return this.questionService.getQuestion(questionId);
	}
			
			//get all questions of any quiz
			
			/*
			public ResponseEntity<?> getQuestionOfQuiz(@PathVariable("qid") Long quid )
			{
//				Quiz quiz= new Quiz();
//				quiz.setQid(quid);
//				Set<Question> set=this.questionService.getQuestionsOfQuiz(quiz);
//				return ResponseEntity.ok(set);
				/*
				Quiz quiz = this.quizService.getQuiz(quid);
				Set<Question> questions = quiz.getQuestions();
				
				List<Question> list=new ArrayList<Question>(questions);
				if(list.size()>Integer.parseInt(quiz.getNumberOfQuestion()))
				{
					list=list.subList(0, Integer.parseInt(quiz.getNumberOfQuestion()+1));
				}
				
				java.util.Collections.shuffle(list);
					
				return ResponseEntity.ok(list);
				
				
			}*/
	/*
	@GetMapping("/quiz/{qid}")		
	@Transactional
	public ResponseEntity<?> getQuestionOfQuiz(@PathVariable("qid") Long qid) {
		
		
		Quiz quiz = this.quizService.getQuiz(qid);
			    
			    
			    
		Set<Question> questions = quiz.getQuestions();
		System.out.println("quiz "+quiz);
		System.out.println("question "+questions);
		List<Question> questionList = new ArrayList<>(questions);
			    
		System.out.println("Number of questions in quiz: " + questionList);


		if (questionList.size() > Integer.parseInt(quiz.getNumberOfQuestion())) {
			Collections.shuffle(questionList); // Shuffle all questions.
			questionList = questionList.subList(0, Integer.parseInt(quiz.getNumberOfQuestion()));
			 }
			    
	 System.out.println("Number of questions after shuffle: " + questionList.size());


	  return ResponseEntity.ok(questionList);
		
	/*	Quiz quiz= new Quiz();
		quiz.setQid(qid);
		Set<Question> questionsOfQuiz = this.questionService.getQuestionsOfQuiz(quiz);
		List<Question> questionList = new ArrayList<>(questionsOfQuiz);
		
		if (questionList.size() > Integer.parseInt(quiz.getNumberOfQuestion())) 
		{
			Collections.shuffle(questionList); // Shuffle all questions.
			questionList = questionList.subList(0, Integer.parseInt(quiz.getNumberOfQuestion()));
			 }
		return ResponseEntity.ok(questionList);
	}
	*/
	
	@GetMapping("/quiz/{qid}")		
	public ResponseEntity<?> getQuestionOfQuiz(@PathVariable("qid") Long qid) {
	    
	    Quiz quiz2 = this.quizService.getQuiz(qid);
	    Set<Question> questionsOfQuiz = this.questionService.getQuestionsOfQuiz(quiz2);
	    List<Question> questionList = new ArrayList<>(questionsOfQuiz);
	    
	    
	    String numberOfQuestionsStr = quiz2.getNumberOfQuestion();
	    
	    System.out.println("number of question"+numberOfQuestionsStr);

	    if (numberOfQuestionsStr != null && !numberOfQuestionsStr.isEmpty()) {
	        int numberOfQuestions = Integer.parseInt(numberOfQuestionsStr);

	        if (questionList.size() > numberOfQuestions) {
	            Collections.shuffle(questionList); // Shuffle all questions.
	            questionList = questionList.subList(0, numberOfQuestions);
	        }
	    } else {
	        // Handle the case where numberOfQuestionsStr is null or empty (e.g., return an error response).
	        return ResponseEntity.badRequest().body("Number of questions is not specified for this quiz.");
	    }
	    
	    
	    questionList.forEach((q)->{
	    	q.setAnswer("");
	    });

	    return ResponseEntity.ok(questionList);
	}
	
	//get all question by admin
	
	@GetMapping("/quiz/all/{qid}")		
	public ResponseEntity<?> getQuestionOfQuizAdmin(@PathVariable("qid") Long qid) {
	    
	    Quiz quiz2 = this.quizService.getQuiz(qid);
	    Set<Question> questionsOfQuiz = this.questionService.getQuestionsOfQuiz(quiz2);
	    List<Question> questionList = new ArrayList<>(questionsOfQuiz);
	    
	    
	    

	    return ResponseEntity.ok(questionList);
	}

	
	//update the questions
	@PutMapping("/")
	public Question updateQuestion(@RequestBody Question question)
	{
		return this.questionService.updateQuestion(question);
	}
			
	//delete
			
	@DeleteMapping("/delete/{questionId}")
	public void deleteQuestion(@PathVariable("questionId") Long questionId)
	{
		this.questionService.deleteQuestion(questionId);
	}
	
	
	//eval quiz
	@PostMapping("/eval-quiz")
	public ResponseEntity<?> qvalQuiz(@RequestBody List<Question> questions)
	{
		System.out.println(questions);
		double marksGot=0;
		int correctAnswers=0;
		int  attempted=0;
		
		for(Question q: questions)
		{
			//single question
			Question question = this.questionService.get(q.getQuesid());
			
			
			
			
			
			if(question.getAnswer().equals(q.getGivenAnswer()))
			{
				correctAnswers++;
				
				double marksSingle=Double.parseDouble(questions.get(0).getQuiz().getMaxMarks()) / questions.size();
				marksGot += marksSingle;
			} 
			
			if(q.getGivenAnswer()!=null)
			{
				attempted++;
			}
		}
		
		
		Map<Object, Object> map=Map.of("marksGot",marksGot,"correctAnswers",correctAnswers,"attempted",attempted);
		return ResponseEntity.ok(map);
	}
			
			
			

}
