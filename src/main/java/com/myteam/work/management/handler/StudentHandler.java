package com.myteam.work.management.handler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.myteam.work.management.data.Student;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StudentHandler {
	private Connection connection;

	public StudentHandler() {
		this.connection = SQLHandler.getConnection();
	}

	public List<Student> getStudent() {
		try {
			List<Student> results = new LinkedList<>();
			var studentInfo = this.connection.prepareStatement("""
				SELECT
					s.id AS student_id,
					s.urName AS student_name,
					s.sex AS student_sex,
					s.generation,
					s.gpa,
					s.birth,
					s.placeOfBirth,
					sl.test1,
					sl.test2,
					sl.endTest,
					sl.score AS total_score,
					sl.normalizedScore,
					sl.rate
				FROM Student s
				JOIN StudentListTeachClass sl ON sl.student = s.id;
			""").executeQuery();

			while(studentInfo.next())
				results.add(new Student(
							studentInfo.getInt("student_id"),
							studentInfo.getShort("generation"),
							studentInfo.getFloat("gpa"),
							studentInfo.getString("student_name"),
							studentInfo.getString("birth"),
							studentInfo.getString("placeOfBirth"),
							studentInfo.getBoolean("student_sex")));
			
			if(!results.isEmpty()) return results;

		} catch (SQLException e) {
			log.error(e.toString());
		}


		return null;
	}

	public List<List<String>> loadStudentListInfo(int semester, int teachclass, int subject) {
		try {
			List<List<String>> rows = new LinkedList<>();
			var prepareStatement = this.connection.prepareStatement("""
				SELECT
					s.id AS student_id,
					s.urName AS student_name,
					s.sex AS student_sex,
					s.generation,
					s.gpa,
					s.birth,
					s.placeOfBirth,
					sl.test1,
					sl.test2,
					sl.endTest,
					sl.score AS total_score,
					sl.normalizedScore,
					sl.rate
				FROM Student s
				JOIN StudentListTeachClass sl ON sl.student = s.id
				JOIN SubjectClass sc ON s.id = sl.classes
				WHERE sc.semester = ? AND sc.classes = ? AND sc.subject = ?
				ORDER BY s.urName
			""");

			prepareStatement.setInt(1, semester);
			prepareStatement.setInt(2, teachclass);
			prepareStatement.setInt(3, subject);

			var rs = prepareStatement.executeQuery();

			while (rs.next()) {
				List<String> row = new LinkedList<>();
				row.add(Integer.toString(rs.getInt("student_id")));
				row.add(rs.getString("student_name"));
				row.add(Boolean.toString(rs.getBoolean("student_sex")));
				row.add(Short.toString(rs.getShort("generation")));
				row.add(Float.toString(rs.getFloat("gpa")));
				row.add(rs.getString("birth"));
				row.add(rs.getString("placeOfBirth"));
				// row.add(rs.getString("test1"));
				// row.add(rs.getString("test2"));
				// row.add(rs.getString("endTest"));
				// row.add(Float.toString(rs.getFloat("total_score")));
				// row.add(Float.toString(rs.getFloat("normalizedScore")));
				// row.add(rs.getString("rate"));
				rows.add(row);
			}

			if (!rows.isEmpty()) return rows;

		} catch (SQLException e) {
			log.error(e.toString());
		}

		return null;
	}
}
