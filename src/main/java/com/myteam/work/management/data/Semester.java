package com.myteam.work.management.data;

import lombok.Getter;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@Getter
@EqualsAndHashCode
public class Semester {
	private int id;
	private short semester;
	private short years;

	public Semester(int id, @NonNull short semester, @NonNull short years) {
		this.id = id;
		this.semester = semester;
		this.years = years;
	}

	public void setSemester(short semester) {
		if (semester < 0)
			throw new IllegalArgumentException("Semester id cannot be negative");

		this.semester = semester;
	}

	public void setYears(short years) {
		if (years < 0)
			throw new IllegalArgumentException("Years cannot be less than 0");

		this.years = years;
	}

	public void setId(int id) {
		if (id < 0)
			throw new IllegalArgumentException("Id cannot be negative");

		this.id = id;
	}

}
