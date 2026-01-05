package com.myteam.work.management.data;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

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

	public TeachClass(int id, @NonNull String className) {
		this.id = id;
		//this.semester = semester;
		this.className = className;
		//this.subject = subject;
		//this.gpa = gpa;

	}

	public void setId(int id) {
		if(id < 0) 
			throw new IllegalArgumentException("Id cannot be negative");

		this.id = id;
	}

	@Override
	public String toString() {
		return this.className;
	}
}
