package com.myteam.work.management.sql;

import java.sql.Connection;
import java.sql.DriverManager;

public class JDBCUtil{
    public JDBCUtil(){

    }

    public static Connection getConnection()
    {
        Connection connection = null;

        try{
        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://localhost:5432/doanoop";
        String username = "postgres";
        String password = "duong@190906";
        connection = DriverManager.getConnection(url, username, password);
        }catch(Exception e){
            e.printStackTrace();
        }

        return connection;
    }

    public static void closeConnection(Connection c) {

        try {
            if(c != null && !c.isClosed())
                c.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}