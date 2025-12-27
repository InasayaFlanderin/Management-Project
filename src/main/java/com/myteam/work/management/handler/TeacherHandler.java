package com.myteam.work.management.handler;

import java.sql.Connection;
import java.sql.SQLException;

import com.myteam.work.management.data.Subject;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TeacherHandler {

    private Connection connection;

    public TeacherHandler() {
        this.connection = SQLHandler.getConnection();
    }

    public Subject submit(double test1, double test2, double endtest, int student, int classes) {
        try {

            var submitInformation = this.connection.prepareStatement("""
                update studentlistteachclass
                set test1 = ?, test2 = ?, endtest = ?
                where student = ? and classes = ?
            """);

            submitInformation.setDouble(1, test1);
            submitInformation.setDouble(2, test2);
            submitInformation.setDouble(3, endtest);
            submitInformation.setInt(4, student);
            submitInformation.setInt(5, classes);

            int result = submitInformation.executeUpdate();

            } catch (SQLException e) {
            log.error(e.toString());
        }

        return null;
    }

}