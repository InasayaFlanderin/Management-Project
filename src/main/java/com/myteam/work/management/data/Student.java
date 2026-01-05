package com.myteam.work.management.data;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@EqualsAndHashCode
@Setter
public class Student {
	private int id;
	private short generation;
	private float gpa;
	private Information info;

	public Student(int id, short generation, float gpa, @NonNull String urName, @NonNull String birth, @NonNull String placeOfBirth, boolean sex) {
		this.id = id;
		this.generation = generation;
		this.gpa = gpa;
		this.info = new Information(urName, birth, placeOfBirth, sex);
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

	@Override
	public String toString() {
		return this.info.getName();
	}
}
