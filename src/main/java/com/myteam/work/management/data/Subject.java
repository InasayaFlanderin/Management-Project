package com.myteam.work.management.data;

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
<<<<<<< HEAD
	private String subjectName;

	public Subject(int id, @NonNull short credits, @NonNull boolean required, @NonNull String subjectName) {
=======
	@NonNull
	private String subjectName;
	private List<Integer> prerequisites;

	public Subject(int id, short credits, boolean required, @NonNull String subjectName, @NonNull List<Integer> prerequisites) {
		if(id < 0 || credits < 1) throw new IllegalArgumentException("Id cannot be less than 0 and credits cannot be less than 1");

		for(Integer subject : prerequisites) if(subject == null || subject < 0) throw new IllegalArgumentException("Exists invalid subject id");

>>>>>>> baa1a2b (Finishing Datum Class)
		this.id = id;
		this.credits = credits;
		this.required = required;
		this.subjectName = subjectName;
<<<<<<< HEAD
	
	}

	public void setId(int id) {
		if(id < 0)
		   throw new IllegalArgumentException("ID cannot be less than 0");
		this.id = id;
=======
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
>>>>>>> baa1a2b (Finishing Datum Class)
	}
	
	public void setCredits(short credits) {
		if(credits < 1)
		   throw new IllegalArgumentException("Credits cannot be less than 1");
		this.credits = credits;
	}

} 

