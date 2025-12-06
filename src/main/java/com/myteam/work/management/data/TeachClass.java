package com.myteam.work.management.data;

import java.util.List;
import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;
import lombok.NonNull;
import lombok.EqualsAndHashCode;

@Getter
@EqualsAndHashCode
public class TeachClass {
	private int id;
	private int semester;
	@Setter
<<<<<<< HEAD
	private String className;
	@Setter
	private int subject;
	private float gpa;

	public TeachClass(int id, int semester, @NonNull String className, @NonNull int subject, @NonNull float gpa) {
=======
	@NonNull
	private String className;
	private int subject;
	private float gpa;
	private List<Integer> students;
	private List<Float> firstTests;
	private List<Float> secondTests;
	private List<Float> endTests;
	private List<Float> scores;
	private List<Float> normalizedScores;
	private List<String> rates;

	public TeachClass(int id, int semester, @NonNull String className, int subject, int gpa, 
			@NonNull List<Integer> students, @NonNull List<Float> firstTests, @NonNull List<Float> secondTests, @NonNull List<Float> endTests, 
			@NonNull List<Float> scores, @NonNull List<Float> normalizedScores, @NonNull List<String> rates) {
		if(id < 0 || semester < 0 || subject < 0 || gpa < 0 || gpa > 4) throw new IllegalArgumentException("Id, subject id and semester id cannot be negative and gpa must be in range 0 to 4");

		if(students.size() != firstTests.size() || firstTests.size() != secondTests.size() || 
				secondTests.size() != endTests.size() || endTests.size() != scores.size() || 
				scores.size() != normalizedScores.size() || normalizedScores.size() != rates.size()) throw new IllegalArgumentException("Incompetible data");

		for(Integer student : student) if(student == null || student < 0) throw new IllegalArgumentException("Exists invalid student id");
		for(Float firstTest : firstTests) if(firstTest == null || firstTest < 0 || firstTest > 10) throw new IllegalArgumentException("Exists invalid first test score");
		for(Float secondTest : secondTests) if(secondTest == null || secondTest < 0 || secondTest > 10) throw new IllegalArgumentException("Exists invalid second test score");
		for(Float endTest : endTests) if(endTest == null || endTest < 0 || endTest > 10) throw new IllegalArgumentException("Exists invalid end test score");
		for(Float score : scores) if(score == null || score < 0 || score > 10) throw new IllegalArgumentException("Exists invalid total score");
		for(Float normalizedScore : normalizedScores) if(score == null || normalizedScore < 0 || normalizedScore > 10) throw new IllegalArgumentException("Exists invalid normalized score");
		for(String rate : rates) switch(rate) {
			case "A":
			case "B+":
			case "B":
			case "C+":
			case "C":
			case "D+":
			case "D":
			case "F":
				break;
			default: throw new IllegalArgumentException("Exists invalid rate");
		}

>>>>>>> baa1a2b (Finishing Datum Class)
		this.id = id;
		this.semester = semester;
		this.className = className;
		this.subject = subject;
		this.gpa = gpa;
<<<<<<< HEAD

	}

	public void setId(int id) {
		if(id < 0) 
			throw new IllegalArgumentException("Id cannot be negative");
=======
		this.students = new ArrayList<>(students);
		this.firstTests = new ArrayList<>(firstTests);
		this.secondTests = new ArrayList<>(secondTests);
		this.endTests = new ArrayList<>(endTests);
		this.scores = new ArrayList<>(scores);
		this.normalizedScores = new ArrayList<>(normalizedScores);
		this.rates = new ArrayList<>(rates);
	}

	public void setId(int id) {
		if(id < 0) throw new IllegalArgumentException("Id cannot be negative");
>>>>>>> baa1a2b (Finishing Datum Class)

		this.id = id;
	}

	public void setGpa(float gpa) {
<<<<<<< HEAD
		if(gpa < 0 || gpa > 4) 
			throw new IllegalArgumentException("Gpa must be in range of 0 to 4");

		this.gpa = gpa;
	}
	
	public void setSemester(int semester) {
		if(semester < 0) 
			throw new IllegalArgumentException("Semester id cannot be negative");
=======
		if(gpa < 0 || gpa > 4) throw new IllegalArgumentException("Gpa must be in range of 0 to 4");

		this.gpa = gpa;
	}

	public void setSemester(int semester) {
		if(semester < 0) throw new IllegalArgumentException("semester id cannot be negative");
>>>>>>> baa1a2b (Finishing Datum Class)

		this.semester = semester;
	}

<<<<<<< HEAD
=======
	public void setSubject(int subject) {
		if(semester < 0) throw new IllegalArgumentException("Subject id cannot be negative");

		this.subject = subject;
	}

	public void addStudent(@NonNull Integer id) {
		if(id < 0) throw new IllegalArgumentException("Student id cannot be negative");

		this.students.add(student);
		this.firstTests.add(0F);
		this.secondTests.add(0F);
		this.endTests.add(0F);
		this.scores.add(0F);
		this.normalizedScores.add(0F);
		this.rates.add("F");
	}

	public void removeStudent(@NonNull Integer id) {
		if(id < 0) throw new IllegalArgumentException("Student id cannot be negative");

		var index = students.indexOf(id);

		if(index == -1) return;

		this.students.remove(index);
		this.firstTests.remove(index);
		this.secondTest.remove(index);
		this.endTests.remove(index);
		this.scores.remove(index);
		this.normalizedScores.remove(index);
		this.rates.remove(index);
	}
>>>>>>> baa1a2b (Finishing Datum Class)
}
