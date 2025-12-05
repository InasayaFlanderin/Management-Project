package com.myteam.work.management.data;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class StudentListTeachClass {

	private int student;
	private int classes;
	private float test1;
	private float test2;
	private float endTest;
	private float score;
	private float normalizedScore;
	private String rate;

	public StudentListTeachClass(int student, int classes, float test1, float test2, float endTest, float score, float normalizedScore, String rate) {
		this.student = student;
		this.classes = classes;
		this.test1 = test1;
		this.test2 = test2;
		this.endTest = endTest;
		this.score = score;
		this.normalizedScore = normalizedScore;
		this.rate = rate;
	}

	public void setTest1(float test1) {
		if(test1 < 0 || test1 > 10) 
			throw new IllegalArgumentException("Test1 must be in range of 0 to 10");

		this.test1 = test1;
	}

	public void setTest2(float test2) {
		if(test2 < 0 || test2 > 10) 
			throw new IllegalArgumentException("Test2 must be in range of 0 to 10");

		this.test2 = test2;
	}
	
	public void setEndTest(float endTest) {
		if(endTest < 0 || endTest > 10) 
			throw new IllegalArgumentException("End test must be in range of 0 to 10");

		this.endTest = endTest;
	}
	
	public float CalculateScore(){
		return (float) (0.25*test1 + 0.25*test2 + 0.5*endTest);
	}

	public void setRate(String rate) {
    		switch(rate) {
       			case "A":
        		case "B+":
        		case "B":
        		case "C+":
        		case "C":
        		case "D+":
        		case "D":
        		case "F":
            			this.rate = rate;
            			break;
       	 		default: 
           			 throw new IllegalArgumentException("Invalid rate: " + rate);
    		}
	} 
		
	public void setNormalizedScore(float normalizedScore) {
    		if(normalizedScore == 4.0 || normalizedScore == 3.5 || normalizedScore == 3.0 || 
       			normalizedScore == 2.5 || normalizedScore == 2.0 || normalizedScore == 1.5 || 
       			normalizedScore == 1.0 || normalizedScore == 0) 
        		this.normalizedScore = normalizedScore;
     		else 
        		throw new IllegalArgumentException("Invalid normalized score: " + normalizedScore);
    
	}
	
}

