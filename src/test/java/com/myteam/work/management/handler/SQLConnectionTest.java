package com.myteam.work.management.handler;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import lombok.extern.slf4j.Slf4j;

import com.myteam.work.management.data.Student;

@Slf4j
public class SQLConnectionTest {
	@Test
	void student() {
		Student desire = new Student(1, "Nguyễn Văn An", "2004-02-15", "Hà Nội", true, (short) 69, 0);
		Student actual = null;

		try{
			var connection = SQLHandler.getConnection();
			var ps = connection.prepareStatement("SELECT * FROM student WHERE id = ?");
			ps.setInt(1, 1);

			var rs = ps.executeQuery();
			if (rs.next()) {
				actual = new Student(
					rs.getInt("id"),
					rs.getString("urName"),
					rs.getString("birth"),
					rs.getString("placeOfBirth"),
					rs.getBoolean("sex"),
					rs.getShort("generation"),
					rs.getFloat("gpa"));
			}
		}catch(SQLException e) {
			log.error(e.toString());
		}

		assertNotNull(actual, "Không tìm thấy sinh viên trong database!");
		assertTrue(desire.equals(actual), desire.toString() + "\n" + actual.toString());
    }
}
