package com.myteam.work.management.handler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.myteam.work.management.data.TeachClass;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TeachClassHandler {
	private Connection connection;

	public TeachClassHandler() {
		this.connection = SQLHandler.getConnection();
	}

	public List<TeachClass> getClass(int year, int teacher) {
		try {
			var classInformation = this.connection.prepareStatement("""
									SELECT TeachClass.* FROM TeachClass
									INNER JOIN Semester ON TeachClass.id = Semester.id
									INNER JOIN Subject ON TeachClass.id = Subject.id
								""").executeQuery();
			List<TeachClass> result = new LinkedList<>();

			while(classInformation.next())
				result.add(new TeachClass(
						classInformation.getInt("id"), 
						classInformation.getInt("semester"),
						classInformation.getString("className"),
						classInformation.getInt("subject"),
						classInformation.getFloat("gpa")));

			if(!result.isEmpty())
				return result;
		} catch (SQLException e) {
			log.error(e.toString());
		}

		return null;
	}
}
