package com.myteam.work.management.data;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class User {
	private int id;
	@Setter
	@NonNull
	private String authName;
	@Setter
	@NonNull
	private String authPassword;
	private Information info;
	@Setter
	private boolean role;
	private List<Integer> subjects;
	private List<Integer> classes;

	public User(int id, @NonNull String name, @NonNull String birth, @NonNull String placeOfBirth, boolean sex, @NonNull String authName, @NonNull String authPassword, boolean role, @NonNull List<Integer> subjects, @NonNull List<Integer> classes) throws DateTimeParseException {
		if(id < 0) throw new IllegalArgumentException("Id cannot be negative");

		for(Integer subject : subjects) if(subject == null || subject < 0) throw new IllegalArgumentException("Exists invalid subject id");
		for(Integer classN : classes) if(classN == null || classN < 0) throw new IllegalArgumentException("Exists invalid class id");

		this.authName = authName;
		this.authPassword = authPassword;
		this.info = new Information(name, birth, placeOfBirth, sex);
		this.role = role;
		this.subjects = new ArrayList<>(subjects);
		this.classes = new ArrayList<>(classes);
	}

	public void setId(int id) {
		if(id < 0) throw new IllegalArgumentException("Id cannot be less than 0");

		this.id = id;
	}

	public boolean addSubject(@NonNull Integer subject) {
		if(subject < 0) throw new IllegalArgumentException("Subject id cannot be negative");

		return this.subjects.add(subject);
	}

	public boolean removeSubject(@NonNull Integer subject) {
		return ((Collection<Integer>) this.subjects).remove(subject);
	}

	public boolean addClass(@NonNull Integer classN) {
		if(classN < 0) throw new IllegalArgumentException("Class id cannot be negative");

		return this.subjects.add(classN);
	}

	public boolean removeClass(@NonNull Integer classN) {
		return ((Collection<Integer>) this.subjects).remove(classN);
	}
}
