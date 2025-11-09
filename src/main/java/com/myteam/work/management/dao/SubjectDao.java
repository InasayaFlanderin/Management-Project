package com.myteam.work.management.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.myteam.work.management.data.Subject;
import com.myteam.work.management.sql.JDBCUtil;

public class SubjectDao {

    public static SubjectDao getInstance() {
        return new SubjectDao();
    }

    public int insert(Subject s)
    {
        try {
            Connection connection = JDBCUtil.getConnection();

            String sql = "INSERT INTO Subject(credits, required, subjectName) " + 
                        " VALUES (?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setShort(1, s.getCredits());
            preparedStatement.setBoolean(2, s.isRequired());
            preparedStatement.setString(3, s.getSubjectName());

            preparedStatement.executeUpdate();

            JDBCUtil.closeConnection(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
