package com.yg.exam.service;

import java.util.Set;

import com.yg.exam.entity.exam.Question;
import com.yg.exam.entity.exam.Quiz;

public interface QuestionService {
	
	public Question addQuestion(Question question);
	
	public Question updateQuestion(Question question);
	
	public Set<Question> getQuestions();
	
	public Question getQuestion(Long questionId);
	
	public Set<Question> getQuestionsOfQuiz(Quiz quiz);

	public void deleteQuestion(Long questionId);
	
	public Question get(Long questionId);
	
	

}
