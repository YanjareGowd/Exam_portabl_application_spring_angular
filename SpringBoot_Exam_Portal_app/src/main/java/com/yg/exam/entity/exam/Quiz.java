package com.yg.exam.entity.exam;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Quiz {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long qid;
	
	private String title;
	
	private String description;
	
	private String maxMarks;
	
	private String numberOfQuestion;
	
	private boolean active=false;
	//One Category can have many quiz similary one many quizzes can have one Quiz
	@ManyToOne(fetch = FetchType.EAGER)
	private Category category;
	
	//One quiz can have many question
	@OneToMany(mappedBy = "quiz",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<Question> questions= new HashSet<>();
	
	
	
	
}
