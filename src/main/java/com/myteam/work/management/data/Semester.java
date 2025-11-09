package com.myteam.work.management.data;

import lombok.Getter;
import lombok.EqualsAndHashCode;

@Getter
@EqualsAndHashCode
public class Semester {
	private int id;
	private short semester;
	private short year;

	public Semester(int id, short semester, short year) {
		if(id < 0 || semester < 0 || year < 0) throw new IllegalArgumentException("Id, semester and year cannot be negative");

		this.id = id;
		this.semester = semester;
		this.year = year;
	}

	public void setId(int id) {
		if(id < 0) throw new IllegalArgumentException("Id cannot be negative");

		this.id = id;
	}

	public void setSemester(short semester) {
		if(semester < 0) throw new IllegalArgumentException("Semester cannot be negative");

		this.semester = semester;
	}

	public void setYear(short year) {
		if(year < 0) throw new IllegalArgumentException("Year cannot be negative");

		this.year = year;
	}
}
