package com.myteam.work.management.data;

import java.time.LocalDate;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class User {
	private int id;
	@NonNull
	private String authName;
	@NonNull
	private String authPass;
	private boolean ur;
	private String urName;
	private LocalDate birth;
	private String placeOfBirth;
	private boolean sex;

	public User(int id, String authName, String authPass, boolean ur, String urName, LocalDate birth, String placeOfBirth, boolean sex) {
		this.id = id;
		this.authName = authName;
		this.authPass =  authPass;
		this.ur = ur;
		this.urName = urName;
		this.birth = birth;
		this.placeOfBirth = placeOfBirth;
		this.sex = sex;
	}

	public void setId(int id) {
		if(id < 0) 
			throw new IllegalArgumentException("Id cannot be less than 0");

		this.id = id;
	}
	
}
