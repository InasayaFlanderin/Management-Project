package com.myteam.work.management.data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class Information {
	private int id;
	private String urName;
	private LocalDate birth;
	@Setter
	@NonNull
	private String placeOfBirth;
	@Setter
	private boolean sex;

	public Information(@NonNull int id, @NonNull String urName, @NonNull LocalDate birth, @NonNull String placeOfBirth, @NonNull boolean sex) {
		this.id = id;
		this.urName = urName;
		this.birth = birth;
		this.placeOfBirth = placeOfBirth;
		this.sex = sex;
	
	}
	public void setBirth(@NonNull String birth) throws DateTimeParseException {
		this.birth = LocalDate.parse(birth, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	}

}
