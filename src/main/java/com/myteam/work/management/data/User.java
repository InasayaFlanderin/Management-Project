package com.myteam.work.management.data;

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
	@Setter
	@NonNull
	private String authName;
	@Setter
	@NonNull
	private String authPass;
	private boolean role;
	private Information info;

	public User (int id, @NonNull String authName, @NonNull String authPass, boolean role, String urName, String birth, String placeOfBirth, boolean sex) {
		this.id = id;
		this.authName = authName;
		this.authPass =  authPass;
		this.role = role;
		this.info = new Information(urName, birth, placeOfBirth, sex);
	}

	public void setId(int id) {
		if(id < 0) throw new IllegalArgumentException("Id cannot be less than 0");

		this.id = id;
	}
}
