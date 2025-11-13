package com.myteam.work;

import lombok.Getter;
import lombok.Setter;
import lombok.NonNull;

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

	static {
		sqlURL = "jdbc:postgresql://localhost:5432/doanoop";
		sqlUsername = "postgres";
		sqlPassword = "0928765431";
	}

	public static Configuration getConfiguration() {
		if(config == null) config = new Configuration();

		return config;
	}
}
