package com.myteam.work.management.data;

import lombok.Getter;
<<<<<<< HEAD
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
=======
import lombok.EqualsAndHashcode;

@Getter
@EqualsAndHashcode
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
>>>>>>> baa1a2b (Finishing Datum Class)

		this.id = id;
	}

<<<<<<< HEAD
=======
	public void setSemester(short semester) {
		if(semester < 0) throw new IllegalArgumentException("Semester cannot be negative");

		this.semester = semester;
	}

	public void setYear(short year) {
		if(year < 0) throw new IllegalArgumentException("Year cannot be negative");

		this.year = year;
	}
>>>>>>> baa1a2b (Finishing Datum Class)
}
