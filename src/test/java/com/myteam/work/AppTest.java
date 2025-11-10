package com.myteam.work;

import java.sql.Connection;
import java.sql.DriverManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;

import com.myteam.work.management.data.Student;

/**
 * Unit test for simple App.
 */
public class AppTest {

    @Test
    void testJDBC() {
        String url = "jdbc:postgresql://localhost:5432/doanoop";
        String username = "postgres";
        String password = "duong@190906";
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            var ps = connection.prepareStatement("SELECT * FROM public.information");
            var rs = ps.executeQuery();
            boolean hasRow = rs.next();
            assertTrue(hasRow);
        } catch (Exception e) {
            // System.out.println("Connection failed: " + e.getMessage());
            fail("SQL Failed: " + e.getMessage());
        }

    }

    @Test
    void testUser()
    {
        String url = "jdbc:postgresql://localhost:5432/doanoop";
        String username = "postgres";
        String password = "duong@190906";
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            var ps = connection.prepareStatement("SELECT * FROM student WHERE id = 1");
            try (var rs = ps.executeQuery()) {
                boolean hasRow = rs.next();
                assertTrue(hasRow);
            }
        } catch (Exception e) {
            fail("SQL Failed: " + e.getMessage());
        }
    }

    @Test
    void student()
    {
        Student student = new Student(1, "Nguyễn Thị B", "14", "02", "2006", "Ha Noi", true, (short)2024, 3.5f);
        String url = "jdbc:postgresql://localhost:5432/doanoop";
        String username = "postgres";
        String password = "duong@190906";

        int actual = -1;
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            var ps = connection.prepareStatement("SELECT * FROM student WHERE id = ?");
            ps.setInt(1, student.getId());
            try (var rs = ps.executeQuery()) {
                if(rs.next())
                {
                    //assertTrue(student.getId() == 1);
                    actual = rs.getInt("id");
                }  
            }
        } catch (Exception e) {
            fail("SQL Failed: " + e.getMessage());
        }
        assertEquals(student.getId(), actual);

    }

   
}
