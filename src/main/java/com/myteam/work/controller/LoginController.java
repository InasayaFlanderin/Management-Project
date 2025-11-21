package com.myteam.work.controller;

import java.util.List;
import java.util.ArrayList;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import com.myteam.work.management.data.User;
import com.myteam.work.management.handler.SQLHandler;

@Slf4j
public class LoginController {
	private static LoginController lgCtrl;

	@Getter
	private User currentUser;

	public LoginController() {}

	public boolean login(String username, String password) {
		var result = SQLHandler.getUserByAuthentication(username, password);
		var logInfo = "Incorrect login as " + username + " password " + password;

		if(result != null) {
			this.currentUser = result;
			logInfo = "Login as " + this.currentUser.toString();
		}

		log.info(logInfo);

		return result != null;
	}
}
