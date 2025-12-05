package com.myteam.work.management.data;

import java.time.LocalDate;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
@Setter
public class Student {

	private int id;
	private short generation;
	private float gpa;
	private String urName;
	private LocalDate birth;
	private String placeOfBirth;
	private boolean sex;
	
	public Student(int id, short generation, float gpa, String urName, @NonNull LocalDate birth, @NonNull String placeOfBirth, boolean sex) {
		this.id = id;
		this.generation = generation;
		this.gpa = gpa;
		this.urName = urName;
		this.birth = birth;
		this.placeOfBirth = placeOfBirth;
		this.sex = sex;

	}
	public void setId(int id) {
		if(id < 0) 
			throw new IllegalArgumentException("Id cannot be negative");

		this.id = id;
	}

	public void setGeneration(short generation) {
		if(generation < 0) 
			throw new IllegalArgumentException("Generation cannot be negative");

		this.generation = generation;
	}

	public void setGpa(float gpa) {
		if(gpa < 0 || gpa > 4) 
			throw new IllegalArgumentException("Gpa must be in range of 0 to 4");

		this.gpa = gpa;
	}
	
}
