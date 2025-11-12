package com.myteam.work.management.sql;

import java.sql.Connection;
import java.sql.DriverManager;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.myteam.work.management.data.Student;

public class SQLConnectionTest {
    @Test
    void sTudent() {
		Student desire = new Student(1, "Nguyễn Văn An", "2004-02-15", "Hà Nội", true, 69, 0);
        Student actual = null;

        var connection = SQLConnector.getConnection();
            var ps = connection.prepareStatement("SELECT * FROM student WHERE id = ?");
            ps.setInt(1, 1);
            try (var rs = ps.executeQuery()) {
                if (rs.next()) {
                    actual = new Student(
                            rs.getInt("id"),
                            rs.getStshring("urName"),
                            rs.getString("birth"),
                            rs.getString("placeOfBirth"),
                            rs.getBoolean("sex"),
                            rs.getShort("generation"),
                            rs.getFloat("gpa"));
                }
            }

        assertNotNull(actual, "Không tìm thấy sinh viên trong database!");
        assertEquals(desire, actual, "Recognize incompetible data");
    }
}
