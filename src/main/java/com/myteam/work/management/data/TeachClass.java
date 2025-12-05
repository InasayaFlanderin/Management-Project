package com.myteam.work.management.data;

import java.util.List;
import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;
import lombok.NonNull;
import lombok.EqualsAndHashCode;

@Getter
@EqualsAndHashCode
public class TeachClass {
	private int id;
	private int semester;
	@Setter
	private String className;
	@Setter
	private int subject;
	private float gpa;

	public TeachClass(int id, int semester, @NonNull String className, @NonNull int subject, @NonNull float gpa) {
		this.id = id;
		this.semester = semester;
		this.className = className;
		this.subject = subject;
		this.gpa = gpa;

	}

	public void setId(int id) {
		if(id < 0) 
			throw new IllegalArgumentException("Id cannot be negative");

		this.id = id;
	}

	public void setGpa(float gpa) {
		if(gpa < 0 || gpa > 4) 
			throw new IllegalArgumentException("Gpa must be in range of 0 to 4");

		this.gpa = gpa;
	}
	
	public void setSemester(int semester) {
		if(semester < 0) 
			throw new IllegalArgumentException("Semester id cannot be negative");

		this.semester = semester;
	}

}
