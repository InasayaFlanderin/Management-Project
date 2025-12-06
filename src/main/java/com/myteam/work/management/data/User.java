package com.myteam.work.management.data;

import java.time.LocalDate;

<<<<<<< HEAD
import lombok.EqualsAndHashCode;
=======
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

>>>>>>> baa1a2b (Finishing Datum Class)
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
<<<<<<< HEAD
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
=======
import lombok.NonNull;
import lombok.EqualsAndHashCode;

@Getter
@EqualsAndHashCode
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

	public User(int id, @NonNull String name, @NonNull String day, @NonNull String month, @NonNull String year, @NonNull String placeOfBirth, boolean sex, @NonNull String authName, @NonNull String authPassword, boolean role, @NonNull List<Integer> subjects, @NonNull List<Integer> classes) throws DateTimeParseException {
		if(id < 0) throw new IllegalArgumentException("Id cannot be negative");

		for(Integer subject : subjects) if(subject == null || subject < 0) throw new IllegalArgumentException("Exists invalid subject id");
		for(Integer classN : classes) if(classN == null || classN < 0) throw new IllegalArgumentException("Exists invalid class id");

		this.authName = authName;
		this.authPassword = authPassword;
		this.info = new Information(name, day, month, year, placeOfBirth, sex);
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
>>>>>>> baa1a2b (Finishing Datum Class)
	}

	public void setId(int id) {
		if(id < 0) 
			throw new IllegalArgumentException("Id cannot be less than 0");

		this.id = id;
	}
	
}
