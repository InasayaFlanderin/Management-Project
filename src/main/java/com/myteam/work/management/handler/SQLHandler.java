package com.myteam.work.management.handler;

import java.util.List;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import lombok.extern.slf4j.Slf4j;

import com.myteam.work.Configuration;
import com.myteam.work.management.data.User;

@Slf4j
public class SQLHandler {
	private static Connection connection;
	private static String authenticationTemplate = """
			select * from users
			where (auth).authName = ? and (auth).authPass = ?
		""";
	private static String userSubjectTemplate = """
			select subject from teachsubject
			where teacher = ?
		""";
	private static String userClassTemplate = """
			select classes from teacherteachclass
			where teacher = ?
		""";

    public static Connection getConnection() {
		if(connection == null) {
			try {
				var config = Configuration.getConfiguration();
				Class.forName("org.postgresql.Driver");
				connection = DriverManager.getConnection(config.getSqlURL(), config.getSqlUsername(), config.getSqlPassword());
			} catch (ClassNotFoundException | SQLException e) {
				log.error(e.toString());
			}
		}

		return connection;
	}

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed())
                connection.close();
        } catch (Exception e) {
            log.error("Error closing connection: ", e);
        }
    }

	public static User getUserByAuthentication(String username, String password) {
		try {
			var prepareStatement = getConnection().prepareStatement(authenticationTemplate);
			prepareStatement.setString(1, username.trim());
			prepareStatement.setString(2, password.trim());
			var userInformation = prepareStatement.executeQuery();

			if(userInformation.next()) {
				var id = userInformation.getInt("id");
				var auth = userInformation.getString("auth").replace("(", " ").replace(")", " ").replace(",", " ").trim().split(" ");

				return new User(
							id,
							userInformation.getString("urName"),
							userInformation.getString("birth"),
							userInformation.getString("placeOfBirth"),
							userInformation.getBoolean("sex"),
							auth[0],
							auth[1],
							userInformation.getBoolean("ur"),
							getUserSubject(id),
							getUserClass(id)
						);
			}
		} catch(SQLException e) {
			log.error(e.toString());
		}

		return null;
	}

	public static List<Integer> getUserSubject(int id) {
		List<Integer> result = new ArrayList<>();

		try {
			var prepareStatement = getConnection().prepareStatement(userSubjectTemplate);
			prepareStatement.setInt(1, id);
			var teachSubject = prepareStatement.executeQuery();

			while(teachSubject.next()) result.add(teachSubject.getInt("subject"));
		} catch(SQLException e) {
			log.error(e.toString());
		}

		return result;
	}

	public static List<Integer> getUserClass(int id) {
		List<Integer> result = new ArrayList<>();

		try {
			var prepareStatement = getConnection().prepareStatement(userClassTemplate);
			prepareStatement.setInt(1, id);
			var teachClass = prepareStatement.executeQuery();

			while(teachClass.next()) result.add(teachClass.getInt("classes"));
		} catch(SQLException e) {
			log.error(e.toString());
		}

		return result;
	}
}
