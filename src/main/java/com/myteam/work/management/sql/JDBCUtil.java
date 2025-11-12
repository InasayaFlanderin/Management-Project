package com.myteam.work.management.sql;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

import lombok.extern.slf4j.Slf4j;

import com.myteam.work.Configuration;

@Slf4j
public class SQLConnector {
	private static Connection connect;

    public static Connection getConnection() {
		if(connect == null) {
			try {
				var config = Configuration.getConfiguration();
				Class.forName("org.postgresql.Driver");
				connection = DriverManager.getConnection(config.getSqlURL(), config.getSqlUsername(), config.getSqlPassword());
			} catch (ClassNotFoundException | SQLException e) {
				log.error(e);
			}
		}
	}

    public static void closeConnection() {
        try {
            if (c != null && !c.isClosed())
                c.close();
        } catch (Exception e) {
            log.error("Error closing connection: ", e);
        }
    }

}
