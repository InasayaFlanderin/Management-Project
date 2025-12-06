package com.myteam.work.management.data;

<<<<<<< HEAD
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
=======
import lombok.Getter;
import lombok.NonNull;
import lombok.EqualsAndHashCode;

@Getter
@EqualsAndHashCode
public class Student {
	private int id;
	private short generation;
	private float gpa;
	private Information info;

	public Student(int id, @NonNull String name, @NonNull String day, @NonNull String month, @NonNull String year, @NonNull String placeOfBirth, boolean sex, short generation, float gpa) {
		if(id < 0 || generation < 0 || gpa < 0 || gpa > 4) throw new IllegalArgumentException("Id, generation cannot be negative and gpa must be in range of 0 to 4");

		this.info = new Information(name, day, month, year, placeOfBirth, sex);
		this.id = id;
		this.generation = generation;
		this.gpa = gpa;
	}

	public void setId(int id) {
		if(id < 0) throw new IllegalArgumentException("Id cannot be negative");
>>>>>>> baa1a2b (Finishing Datum Class)

		this.id = id;
	}

	public void setGeneration(short generation) {
<<<<<<< HEAD
		if(generation < 0) 
			throw new IllegalArgumentException("Generation cannot be negative");
=======
		if(generation < 0) throw new IllegalArgumentException("Generation cannot be negative");
>>>>>>> baa1a2b (Finishing Datum Class)

		this.generation = generation;
	}

	public void setGpa(float gpa) {
<<<<<<< HEAD
		if(gpa < 0 || gpa > 4) 
			throw new IllegalArgumentException("Gpa must be in range of 0 to 4");

		this.gpa = gpa;
	}
	
=======
		if(gpa < 0 || gpa > 4) throw new IllegalArgumentException("Gpa must be in range of 0 to 4");

		this.gpa = gpa;
	}
>>>>>>> baa1a2b (Finishing Datum Class)
}
