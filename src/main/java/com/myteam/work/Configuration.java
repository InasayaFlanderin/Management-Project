package com.myteam.work;

import java.awt.Color;
import java.awt.Cursor;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

public class Configuration {
	private static Configuration config;
	@Getter
	@Setter
	@NonNull
	private static String sqlURL;
	@Getter
	@Setter
	@NonNull
	private static String sqlUsername;
	@Getter
	@Setter
	@NonNull
	private static String sqlPassword;
	@Getter
	private static Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);
	@Getter
	private static Color fieldColor = new Color(150, 150, 150);

	static {
		sqlURL = "jdbc:postgresql://localhost:5432/doanoop";
		sqlUsername = "postgres";
		sqlPassword = "duong@190906";
	}

	public static Configuration getConfiguration() {
		if(config == null) config = new Configuration();

		return config;
	}
}
