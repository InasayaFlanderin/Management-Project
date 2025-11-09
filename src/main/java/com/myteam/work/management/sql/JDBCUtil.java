package com.myteam.work.management.sql;

import java.sql.Connection;
import java.sql.DriverManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JDBCUtil {

    private static final Logger log = LoggerFactory.getLogger(JDBCUtil.class);

    public JDBCUtil() {

    }

    public static Connection getConnection() {
        Connection connection = null;

        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/doanoop";
            String username = "postgres";
            String password = "";
            connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            log.error("Error getting connection: ", e);
        }

        return connection;
    }

    public static void closeConnection(Connection c) {

        try {
            if (c != null && !c.isClosed())
                c.close();
        } catch (Exception e) {
            log.error("Error closing connection: ", e);
        }
    }

}