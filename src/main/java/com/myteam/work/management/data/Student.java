package com.myteam.work.management.data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

@Getter
@EqualsAndHashCode
public class Student {
	private int id;
	private short generation;
	private float gpa;
	private Information info;

	public Student(int id, @NonNull String name, @NonNull String birth, @NonNull String placeOfBirth, boolean sex, short generation, float gpa) {
		if(id < 0 || generation < 0 || gpa < 0 || gpa > 4) throw new IllegalArgumentException("Id, generation cannot be negative and gpa must be in range of 0 to 4");

		LocalDate birthDate = LocalDate.parse(birth, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		this.info = new Information(name, String.format("%02d", birthDate.getDayOfMonth()), String.format("%02d", birthDate.getMonthValue()), birthDate.getYear() + "", placeOfBirth, sex);
		this.id = id;
		this.generation = generation;
		this.gpa = gpa;
	}

	public void setId(int id) {
		if(id < 0) throw new IllegalArgumentException("Id cannot be negative");

		this.id = id;
	}

	public void setGeneration(short generation) {
		if(generation < 0) throw new IllegalArgumentException("Generation cannot be negative");

		this.generation = generation;
	}

	public void setGpa(float gpa) {
		if(gpa < 0 || gpa > 4) throw new IllegalArgumentException("Gpa must be in range of 0 to 4");

		this.gpa = gpa;
	}
}
