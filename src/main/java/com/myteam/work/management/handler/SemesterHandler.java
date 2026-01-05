package com.myteam.work.management.handler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.myteam.work.management.data.Semester;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SemesterHandler {
	private final Connection connection;

	public SemesterHandler() {
		this.connection = SQLHandler.getConnection();
	}

	public List<Semester> getAllSemester() {
		try {
			var semesterInformation = this.connection.prepareStatement("select * from semester").executeQuery();
			List<Semester> result = new LinkedList<>();

			while(semesterInformation.next()) result.add(new Semester(
							semesterInformation.getInt("id"),
							semesterInformation.getShort("semester"),
							semesterInformation.getShort("years")
						));

			if(!result.isEmpty()) return result;
		} catch(SQLException e) {
			log.error(e.toString());
		}

		return null;
	}

	public void createSemester(short semester, short years) {
		try {
			PreparedStatement statement = this.connection.prepareStatement("insert into semester(semester, years) values (?, ?)");
			
			statement.setShort(1, semester);
			statement.setShort(2, years);
			
			statement.executeUpdate();
			
		} catch (SQLException e) {
			log.error(e.toString());
		}
	}

	public void editSemester(int id, int semester, long years) {
		try {
			PreparedStatement statement = this.connection.prepareStatement("update semester set semester = ?, years = ? where id = ?");

			statement.setInt(1, semester);
			statement.setLong(2, years);
			statement.setInt(3, id);
			
			statement.executeUpdate();
		} catch (SQLException e) {
			log.error(e.toString());
		}
	}

	public void removeSemester(int id) {
		try {
			PreparedStatement statement = this.connection.prepareStatement("delete from semester where id = ?");

			statement.setInt(1, id);

			statement.executeUpdate();
		} catch (SQLException e) {
			log.error(e.toString());
		}
	}
}
