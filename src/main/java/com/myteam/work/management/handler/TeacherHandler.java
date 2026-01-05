package com.myteam.work.management.handler;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import com.myteam.work.management.data.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TeacherHandler {

    private final Connection connection;

    public TeacherHandler() {
        this.connection = SQLHandler.getConnection();
    }

    public List<User> loadTeacher(String s) {
		try {
			List<User> results = new LinkedList<>();

			PreparedStatement statement;

            try {
                // Use the same aliased SELECT for both numeric id and name search
                statement = this.connection.prepareStatement("""
                            SELECT
                        u.id AS teacher_id,
                        u.urName AS teacher_name,
                        (u.auth).authName AS username,
                        (u.auth).authPass AS password,
                        u.birth,
                        u.placeOfBirth,
                        u.sex,
                        u.ur AS role,
                        sb.subjectName AS subject,
                        tc.className AS teach_class
                    FROM Users u
                    JOIN TeacherTeachClass ttc ON ttc.teacher = u.id
                    JOIN TeachClass tc ON tc.id = ttc.classes
                    JOIN SubjectClass sc ON sc.classes = tc.id
                    JOIN Subject sb ON sb.id = sc.subject
                    JOIN TeachSubject ts ON ts.teacher = u.id AND ts.subject = sb.id
                    WHERE u.ur = true AND u.id = ?;
                        """);
                statement.setInt(1, Integer.parseInt(s));
            } catch(NumberFormatException _) {
                statement = this.connection.prepareStatement("""
                            SELECT
                        u.id AS teacher_id,
                        u.urName AS teacher_name,
                        (u.auth).authName AS username,
                        (u.auth).authPass AS password,
                        u.birth,
                        u.placeOfBirth,
                        u.sex,
                        u.ur AS role,
                        sb.subjectName AS subject,
                        tc.className AS teach_class
                    FROM Users u
                    JOIN TeacherTeachClass ttc ON ttc.teacher = u.id
                    JOIN TeachClass tc ON tc.id = ttc.classes
                    JOIN SubjectClass sc ON sc.classes = tc.id
                    JOIN Subject sb ON sb.id = sc.subject
                    JOIN TeachSubject ts ON ts.teacher = u.id AND ts.subject = sb.id
                    WHERE u.ur = true AND u.urName ILIKE ?;
                        """);
                statement.setString(1, "%" + s + "%");
            }

			var teacherInformation = statement.executeQuery();

            while(teacherInformation.next()) {
                results.add(new User(
                    teacherInformation.getInt("teacher_id"),
                    teacherInformation.getString("username"),
                    teacherInformation.getString("password"),
                    teacherInformation.getBoolean("role"),
                    teacherInformation.getString("teacher_name"),
                    teacherInformation.getString("birth"),
                    teacherInformation.getString("placeOfBirth"),
                    teacherInformation.getBoolean("sex")));
            }

			if(!results.isEmpty()) return results;
		} catch(SQLException e) {
			log.error(e.toString());
		}

		return null;
	}

    public String getTeacherName(int id) {
        try {
            List<User> result = new LinkedList<>();
            var prepareStatement = SQLHandler.getConnection().prepareStatement("""
                SELECT
                    u.id,
                    u.urName AS teacher_name
                FROM Users u
                WHERE u.ur = true;""");
            prepareStatement.setInt(1, id);
            var teacherNameInfo = prepareStatement.executeQuery();

            while(teacherNameInfo.next())
                result.add(new User(
                    teacherNameInfo.getInt("id"),
                    teacherNameInfo.getString("authName"),
                    teacherNameInfo.getString("authPass"),
                    teacherNameInfo.getBoolean("role"),
                    teacherNameInfo.getString("urName"),
                    teacherNameInfo.getString("birth"),
                    teacherNameInfo.getString("placeOfBirth"),
                    teacherNameInfo.getBoolean("sex")));
        } catch (SQLException e) {
            log.error(e.toString());
        }

        return null;
    }

    public void addTeacher(User user, String birth) {
		try {
			PreparedStatement statement = this.connection.prepareStatement("""
				INSERT INTO Users (urName, birth, placeOfBirth, sex, auth, ur)
                VALUES (?, ?, ?, ?, ?, ?);""");
			
			statement.setString(1, user.getInfo().getName());
			statement.setString(2, birth);
			statement.setString(3, user.getInfo().getPlaceOfBirth());
			statement.setBoolean(4, user.getInfo().isSex());
			statement.setString(5, user.getAuthName());
			statement.setBoolean(6, user.isRole());

			statement.executeUpdate();
		} catch (SQLException e) {
			log.error(e.toString());
		}
	}

	public void removeTeacher(User user) {
		try {
			PreparedStatement statement = this.connection.prepareStatement("DELETE FROM Users WHERE id = ? AND ur = true;");

			statement.setInt(1, user.getId());

			statement.executeUpdate();
		} catch (SQLException e) {
			log.error(e.toString());
		}
	}

    public void removeTeacher(int id) {
		try {
			PreparedStatement statement = this.connection.prepareStatement("DELETE FROM Users WHERE id = ?");

			statement.setInt(1, id);

			statement.executeUpdate();
		} catch (SQLException e) {
			log.error(e.toString());
		}
	}

    public List<User> getAllTeacher() {
		try {
			List<User> results = new LinkedList<>();
			var teacherInformation = this.connection.prepareStatement("select * from users").executeQuery();

			while(teacherInformation.next()) {
				results.add(new User(
                    teacherInformation.getInt("id"),
                    teacherInformation.getString("authName"),
                    teacherInformation.getString("authPass"),
                    teacherInformation.getBoolean("role"),
                    teacherInformation.getString("urName"),
                    teacherInformation.getString("birth"),
                    teacherInformation.getString("placeOfBirth"),
                    teacherInformation.getBoolean("sex")));
			}

			if(!results.isEmpty()) return results;
		} catch(SQLException e) {
			log.error(e.toString());
		}

		return null;
	}

	public int countTeacher() {
		try {
			var number = this.connection.prepareStatement("""
				SELECT COUNT(*) as nc
				FROM Users
				WHERE ur = true;
					""").executeQuery();
			if(number.next()) return number.getInt("nc");
		} catch(SQLException e) {
			log.error(e.toString());
		}

		return -1;
	}

	public void createTeacher(boolean sex, String name, LocalDate birth, String birthPlace, String username, String password) {
		try {
			var prepareStatement = this.connection.prepareStatement("""
				INSERT INTO Users (urName, birth, placeOfBirth, sex, auth, ur)
				VALUES (?, ?, ?, ?, (?, ?), true);
					""");
			prepareStatement.setString(1, name);
			prepareStatement.setDate(2, Date.valueOf(birth));
			prepareStatement.setString(3, birthPlace);
			prepareStatement.setBoolean(4, sex);
			prepareStatement.setString(5, username);
			prepareStatement.setString(6, password);

			prepareStatement.executeUpdate();
		} catch(SQLException e) {
			log.error(e.toString());
		}
	}

	public int getLatestId() {
		try {
			var number = this.connection.prepareStatement("""
				SELECT MAX(id) as mi 
    			FROM Users
    			WHERE ur = true
					""").executeQuery();

			if(number.next()) return number.getInt("mi");
		} catch(SQLException e) {
			log.error(e.toString());
		}

		return -1;
	}

	public void editTeacher(int id, boolean sex, String name, LocalDate birth, String birthPlace, String username, String password) {
		try {
			var prepareStatement = this.connection.prepareStatement("""
				UPDATE Users
				SET
    				urName       = ?,
    				birth        = ?,
    				placeOfBirth = ?,
    				sex          = ?,
    				auth         = (?, ?)
				WHERE id = ?
					""");
			prepareStatement.setString(1, name);
			prepareStatement.setDate(2, Date.valueOf(birth));
			prepareStatement.setString(3, birthPlace);
			prepareStatement.setBoolean(4, sex);
			prepareStatement.setString(5, username);
			prepareStatement.setString(6, password);
			prepareStatement.executeUpdate();
		} catch(SQLException e) {
			log.error(e.toString());
		}
	}
}
