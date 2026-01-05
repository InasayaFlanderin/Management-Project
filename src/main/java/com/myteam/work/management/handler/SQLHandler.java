package com.myteam.work.management.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.myteam.work.Configuration;
import com.myteam.work.management.data.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SQLHandler {
	private static Connection connection;

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
        } catch (SQLException e) {
            log.error("Error closing connection: ", e);
        }
    }

	public static User getUserByAuthentication(String username, String password) {
		try {
			var prepareStatement = getConnection().prepareStatement("""
						select * from users
						where (auth).authName = ? and (auth).authPass = ?
					""");
			prepareStatement.setString(1, username.trim());
			prepareStatement.setString(2, password.trim());
			var userInformation = prepareStatement.executeQuery();

			if(userInformation.next()) {
				var id = userInformation.getInt("id");
				var auth = userInformation.getString("auth").replace("(", " ").replace(")", " ").replace(",", " ").trim().split(" ");

				return new User(
							id,
							auth[0],
							auth[1],
							userInformation.getBoolean("ur"),
							userInformation.getString("urName"),
							userInformation.getString("birth"),
							userInformation.getString("placeOfBirth"),
							userInformation.getBoolean("sex")
						);
			}
		} catch(SQLException e) {
			log.error(e.toString());
		}

		return null;
	}
}
