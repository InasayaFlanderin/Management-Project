package com.myteam.work.management.data;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class Semester {
	private int id;
	private short semester;
	private short years;

	public Semester(int id, short semester, short years) {
		this.id = id;
		this.semester = semester;
		this.years = years;
	}

	public void setId(int id) {
		if(id < 0) throw new IllegalArgumentException("Id cannot be negative");

		this.id = id;
	}

	public void setSemester(short semester) {
		if(semester < 0) throw new IllegalArgumentException("Semester id cannot be negative");

		this.semester = semester;
	}

	public void setYears(short years) {
		if(years < 0) throw new IllegalArgumentException("Years cannot be less than 0");

		this.years = years;
	}

	@Override
	public String toString() {
		return semester + "-" + years;
	}
}
