package com.myteam.work.management.handler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.myteam.work.management.data.Pair;
import com.myteam.work.management.data.TeachClass;
import com.myteam.work.management.data.Triple;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TeachClassHandler {
	private final Connection connection;

	public TeachClassHandler() {
		this.connection = SQLHandler.getConnection();
	}

	public List<TeachClass> getClass(int semester, int teacher, int subject) {
		try {
			var prepareStatement = this.connection.prepareStatement("""
									SELECT DISTINCT
									tc.id AS class_id,
    								tc.className AS class_name
									FROM SubjectClass sc
									JOIN TeachClass tc ON tc.id = sc.classes
									JOIN Subject sb ON sb.id = sc.subject
									JOIN Semester se ON se.id = sc.semester
									JOIN TeacherTeachClass ttc ON ttc.classes = tc.id
									JOIN TeachSubject ts ON ts.subject = sb.id AND ts.teacher = ttc.teacher
									JOIN Users u ON u.id = ts.teacher
									WHERE sc.semester = ? AND ts.teacher  = ? AND sc.subject  = ?
								""");
			prepareStatement.setInt(1, semester);
			prepareStatement.setInt(2, teacher);
			prepareStatement.setInt(3, subject);
			var classInformation = prepareStatement.executeQuery();
			List<TeachClass> result = new LinkedList<>();

			while(classInformation.next())
				result.add(new TeachClass(
						classInformation.getInt("class_id"), 
						classInformation.getString("class_name")));

			if(!result.isEmpty())
				return result;
		} catch (SQLException e) {
			log.error(e.toString());
		}

		return null;
	}

	public List<TeachClass> getClass(int semester, int subject) {
		try {
			var prepareStatement = this.connection.prepareStatement("""
									SELECT DISTINCT
									tc.id AS class_id,
    								tc.className AS class_name
									FROM SubjectClass sc
									JOIN TeachClass tc ON tc.id = sc.classes
									JOIN Subject sb ON sb.id = sc.subject
									JOIN Semester se ON se.id = sc.semester
									JOIN TeacherTeachClass ttc ON ttc.classes = tc.id
									JOIN TeachSubject ts ON ts.subject = sb.id AND ts.teacher = ttc.teacher
									JOIN Users u ON u.id = ts.teacher
									WHERE sc.semester = ? AND sc.subject  = ?
								""");
			prepareStatement.setInt(1, semester);
			prepareStatement.setInt(2, subject);
			var classInformation = prepareStatement.executeQuery();
			List<TeachClass> result = new LinkedList<>();

			while(classInformation.next())
				result.add(new TeachClass(
						classInformation.getInt("class_id"), 
						classInformation.getString("class_name")));
			if(!result.isEmpty())
				return result;
		} catch (SQLException e) {
			log.error(e.toString());
		}

		return null;
	}

	public void updateClassGpa(int classes) {
		try(var prepareStatement = this.connection.prepareStatement("""
				UPDATE SubjectClass sc
				SET gpa = sub.class_gpa
				FROM (
					SELECT
						classes,
						AVG(normalizedScore) AS class_gpa
					FROM StudentListTeachClass
					WHERE classes = ?
					GROUP BY classes
				) sub
				WHERE sc.classes = sub.classes;
			""")) {

			prepareStatement.setInt(1, classes);

			prepareStatement.executeUpdate();

		} catch (SQLException e) {
			log.error(e.toString());
		}
	}

     public void submit1(float test1, int student, int classes) {
        try {

            var submitInformation = this.connection.prepareStatement("""
                update studentlistteachclass
                set test1 = ?
                where student = ? and classes = ?
            """);

            submitInformation.setFloat(1, test1);
            submitInformation.setInt(2, student);
            submitInformation.setInt(3, classes);

            submitInformation.executeUpdate();

            } catch (SQLException e) {
            log.error(e.toString());
        }
    }

	public void submit2(float test2, int student, int classes) {
        try {

            var submitInformation = this.connection.prepareStatement("""
                update studentlistteachclass
                set test2 = ?
                where student = ? and classes = ?
            """);

            submitInformation.setFloat(1, test2);
            submitInformation.setInt(2, student);
            submitInformation.setInt(3, classes);

            submitInformation.executeUpdate();

            } catch (SQLException e) {
            log.error(e.toString());
        }
    }

	public void endtest(float endtest, int student, int classes) {
        try {

            var submitInformation = this.connection.prepareStatement("""
                update studentlistteachclass
                set endtest = ?
                where student = ? and classes = ?
            """);

            submitInformation.setFloat(1, endtest);
            submitInformation.setInt(2, student);
            submitInformation.setInt(3, classes);

            submitInformation.executeUpdate();

            } catch (SQLException e) {
            log.error(e.toString());
        }
    }

	public List<String> getTeachesClass(int id) {
		try {
			var prepareStatement = this.connection.prepareStatement("""
					SELECT className
					FROM TeacherTeachClass ttc
					JOIN TeachClass tc ON tc.id = ttc.classes
					WHERE ttc.teacher = ?""");
			prepareStatement.setInt(1, id);
			List<String> results = new LinkedList<>();
			var classNames = prepareStatement.executeQuery();

			while(classNames.next()) results.add(classNames.getString("classname"));

			if(!results.isEmpty()) return results;
		} catch(SQLException e) {
			log.error(e.toString());
		}

		return null;
	}

	public List<Pair<Integer, String>> getTeachesClassWithId(int id) {
		try {
			var prepareStatement = this.connection.prepareStatement("""
					SELECT
				    tc.id,
				    tc.className
					FROM TeacherTeachClass ttc
					JOIN TeachClass tc ON tc.id = ttc.classes
					WHERE ttc.teacher = ?;
					""");
			prepareStatement.setInt(1, id);
			List<Pair<Integer, String>> results = new LinkedList<>();
			var classInfo = prepareStatement.executeQuery();

			while(classInfo.next()) results.add(new Pair<>(classInfo.getInt("id"), classInfo.getString("classname")));

			if(!results.isEmpty()) return results;
		} catch(SQLException e) {
			log.error(e.toString());
		}

		return null;
	}

	public List<Triple<Integer, String, Integer>> loadAllClasses() {
		try {
			var prepareStatement = this.connection.prepareStatement("""
				SELECT
    			tc.id,
    			tc.className,
    			s.id as sid
				FROM TeachClass tc
				JOIN SubjectClass sc ON sc.classes = tc.id
				JOIN Subject s ON s.id = sc.subject;
					""");
			var classInfo = prepareStatement.executeQuery();
			List<Triple<Integer, String, Integer>> result = new LinkedList<>();

			while(classInfo.next()) result.add(new Triple(classInfo.getInt("id"), classInfo.getString("className"), classInfo.getInt("sid")));

			if(!result.isEmpty()) return result;
		} catch(SQLException e) {
			log.error(e.toString());
		}

		return null;
	}

	public void addClass(int teacher, int classes) {
		try {
			var prepareStatement = this.connection.prepareStatement("""
					INSERT INTO TeacherTeachClass (teacher, classes)
					VALUES (?, ?);
					""");
			prepareStatement.setInt(1, teacher);
			prepareStatement.setInt(2, classes);
			prepareStatement.executeUpdate();
		} catch(SQLException e) {
			log.error(e.toString());
		}
	}

	public void removeClass(int teacher, int classes) {
		try {
			var prepareStatement = this.connection.prepareStatement("""
				DELETE FROM TeacherTeachClass
				WHERE teacher = ?
				  AND classes = ?;
					""");
			prepareStatement.setInt(1, teacher);
			prepareStatement.setInt(2, classes);
		} catch(SQLException e) {
			log.error(e.toString());
		}
	}

	public List<Integer> getTeachesClassIdWithId(int id) {
		try {
			var prepareStatement = this.connection.prepareStatement("""
					SELECT
				    tc.id,
					FROM TeacherTeachClass ttc
					JOIN TeachClass tc ON tc.id = ttc.classes
					WHERE ttc.teacher = ?;
					""");
			prepareStatement.setInt(1, id);
			List<Integer> results = new LinkedList<>();
			var classInfo = prepareStatement.executeQuery();

			while(classInfo.next()) results.add(classInfo.getInt("id"));

			if(!results.isEmpty()) return results;
		} catch(SQLException e) {
			log.error(e.toString());
		}

		return null;
	}

	public List<Integer> loadAllClassId() {
		try {
			var classInfo = this.connection.prepareStatement("""
						SELECT id
						FROM TeachClass;
					""").executeQuery();
			List<Integer> result = new LinkedList<>();

			while(classInfo.next()) result.add(classInfo.getInt("id"));

			if(!result.isEmpty()) return result;
		} catch(SQLException e) {
			log.error(e.toString());
		}

		return null;
	}

	public Pair<String, Float> fetchNameAGpa(int id) {
		try {
			var prepareStatement = this.connection.prepareStatement("""
				SELECT
    				tc.className,
    				sc.gpa
				FROM TeachClass tc
				JOIN SubjectClass sc ON sc.classes = tc.id
				WHERE tc.id = ?;
					""");
			prepareStatement.setInt(1, id);
			var info = prepareStatement.executeQuery();

			if(info.next()) return new Pair<>(info.getString("classname"), info.getFloat("gpa"));
		} catch(SQLException e) {
			log.error(e.toString());
		}

		return null;
	}

	public Pair<String, String> fetchSemesterASubject(int id) {
		try {
			var prepareStatement = this.connection.prepareStatement("""
				SELECT
    				se.id,
    				se.semester,
    				se.years,
    				sb.subjectName
				FROM SubjectClass sc
				JOIN Semester se ON se.id = sc.semester
				JOIN Subject sb  ON sb.id = sc.subject
				WHERE sc.classes = ?;
					""");
				prepareStatement.setInt(1, id);
				var info = prepareStatement.executeQuery();
				
				if(info.next()) return new Pair<>(info.getShort("semester") + "-" + info.getShort("years"), info.getString("subjectname"));
		} catch(SQLException e) {
			log.error(e.toString());
		}

		return null;
	}

	public List<String> getTeacherName(int id) {
		try {
			var prepareStatement = this.connection.prepareStatement("""
				SELECT
    			u.urName 
				FROM TeacherTeachClass ttc
				JOIN Users u ON u.id = ttc.teacher
				WHERE ttc.classes = ?;
					""");
			prepareStatement.setInt(1, id);
			var info = prepareStatement.executeQuery();
			List<String> result = new LinkedList<>();

			while(info.next()) result.add(info.getString("urname"));

			if(!result.isEmpty()) return result;
		} catch(SQLException e) {
			log.error(e.toString());
		}

		return null;
	}

	public List<Integer> searchClass(String s) {
		try {
			List<Integer> result = new LinkedList<>();

			PreparedStatement statement;

			try {
				statement = this.connection.prepareStatement("""
					SELECT
    				id
					FROM TeachClass
					WHERE id = ?;
						""");
				statement.setInt(1, Integer.parseInt(s));
			} catch(NumberFormatException _) {
				statement = this.connection.prepareStatement("""
					SELECT
    				id
					FROM TeachClass
					WHERE className ILIKE ?;
						""");
				statement.setString(1, "%" + s + "%");
			}

			var info = statement.executeQuery();

			while(info.next()) result.add(info.getInt("id"));
		} catch (SQLException e) {
			log.error(e.toString());
		}

		return null;
	}

    public void insertStudentIntoClass(TeachClass classes, int int1) {
        try {
			var prepareStatement = this.connection.prepareStatement("""
				INSERT INTO StudentListTeachClass (
    student,
    classes,
    test1,
    test2,
    endtest,
    normalizedScore
)
VALUES (
    ?,    
    ?,    
    ?,  
    ?,  
    ?,   
    ? ;
					""");
			prepareStatement.setInt(1, classes.getId());
			prepareStatement.setInt(2, int1);
		} catch (SQLException e) {
			log.error(e.toString());
		}
    }

    public void deleteStudentOutOfClass(TeachClass classes, Integer valueAt) {
        try {
			var prepareStatement = this.connection.prepareStatement("""
				DELETE FROM StudentListTeachClass
WHERE student = ?
  AND classes = ?;
					""");
			prepareStatement.setInt(1, classes.getId());
			prepareStatement.setInt(2, valueAt);
		} catch (SQLException e) {
			log.error(e.toString());
		}
    }
}
