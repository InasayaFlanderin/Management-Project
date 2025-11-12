package com.myteam.work.management.data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
public class Information {
	@Setter
	@NonNull 
	private String name;
	private LocalDate birth;
	@Setter
	@NonNull
	private String placeOfBirth;
	@Setter
	private boolean sex;

	public Information(@NonNull String name, @NonNull String birth, @NonNull String placeOfBirth, boolean sex) throws DateTimeParseException {
		this.name = name;
		this.birth = LocalDate.parse(birth, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		this.placeOfBirth = placeOfBirth;
		this.sex = sex;
	}

	public void setBirth(@NonNull String birth) throws DateTimeParseException {
		this.birth = LocalDate.parse(birth, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
	}
}
