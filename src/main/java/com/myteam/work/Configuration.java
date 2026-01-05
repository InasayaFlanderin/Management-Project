package com.myteam.work;

import java.awt.Color;
import java.awt.Cursor;

import com.myteam.work.gui.pages.ComboBoxNullNameRenderer;

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
	private static final Cursor handCursor;
	@Getter
	private static final Color fieldColor;
	@Getter
	private static final ComboBoxNullNameRenderer comboBoxRenderer;

	static {
		sqlURL = "jdbc:postgresql://localhost:5432/doanoop";
		sqlUsername = "postgres";
		sqlPassword = "12345678";
		handCursor = new Cursor(Cursor.HAND_CURSOR);
		fieldColor = new Color(150, 150, 150);
		comboBoxRenderer = new ComboBoxNullNameRenderer();
	}

	public static Configuration getConfiguration() {
		if(config == null) config = new Configuration();

		return config;
	}
}
