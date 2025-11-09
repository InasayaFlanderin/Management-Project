package com.myteam.work.management.data;

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collection;

import lombok.Getter;
import lombok.Setter;
import lombok.NonNull;
import lombok.EqualsAndHashCode;

@Getter
@EqualsAndHashCode
public class Subject {
	private int id;
	private short credits;
	@Setter
	private boolean required;
	@Setter
	@NonNull
	private String subjectName;
	private List<Integer> prerequisites;

	public Subject(int id, short credits, boolean required, @NonNull String subjectName, @NonNull List<Integer> prerequisites) {
		if(id < 0 || credits < 1) throw new IllegalArgumentException("Id cannot be less than 0 and credits cannot be less than 1");

		for(Integer subject : prerequisites) if(subject == null || subject < 0) throw new IllegalArgumentException("Exists invalid subject id");

		this.id = id;
		this.credits = credits;
		this.required = required;
		this.subjectName = subjectName;
		this.prerequisites = new ArrayList<>(prerequisites);
	}

	public void setId(int id) {
		if(id < 0) throw new IllegalArgumentException("Id cannot be less than 0");

		this.id = id;
	}

	public void setCredits(short credits) {
		if(credits < 1) throw new IllegalArgumentException("Credit cannot be less than 1");
		
		this.credits = credits;
	}

	public boolean addPrerequiste(@NonNull Integer id) {
		if(id < 0) throw new IllegalArgumentException("Subject id cannot be negative");

		return this.prerequisites.add(id);
	}

	public boolean removePrerequiste(@NonNull Integer id) {
		return ((Collection<Integer>) this.prerequisites).remove(id);
	}
}
