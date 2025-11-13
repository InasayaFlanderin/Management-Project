package com.myteam.work.management.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.myteam.work.Configuration;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SQLConnector {
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
        } catch (Exception e) {
            log.error("Error closing connection: ", e);
        }
    }

}
