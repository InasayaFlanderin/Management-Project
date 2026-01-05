package com.myteam.work.controller;

import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.JPanel;

import com.myteam.work.gui.Window;
import com.myteam.work.gui.pages.ManagerPage;
import com.myteam.work.gui.pages.MenuPanel;
import com.myteam.work.gui.pages.PageHeader;
import com.myteam.work.gui.pages.TeacherPage;

public class PageController {
	private static PageController pc;
	private final CardLayout pageSwitcher;

	private PageController() {
		this.pageSwitcher = Window.getWindow().getPageSwitcher();
	}

	public static PageController getController() {
		if(pc == null) pc = new PageController();

		return pc;
	}

	public void getLoginPage() {
		pageSwitcher.show(Window.getWindow().getContentPane(), "login");
		setUsername("");
	}

	public void getTeacherPage(String username) {
		pageSwitcher.show(Window.getWindow().getContentPane(), "teacher");
		setUsername(username);
		getHeader(TeacherPage.getPage());
		getMenu(TeacherPage.getPage());
		var tpec = TeacherPageEventController.getController();
		tpec.loadAllSubject();
		tpec.loadSemester();
		tpec.loadTeacherSubject();
	}

	public void getManagerPage(String username) {
		pageSwitcher.show(Window.getWindow().getContentPane(), "manager");
		setUsername(username);
		getHeader(ManagerPage.getPage());
		getMenu(ManagerPage.getPage());
		var mpec = ManagerPageEventController.getController();
		mpec.loadAllSubject();
		mpec.loadManagementSemester();
		mpec.loadManagementAllSubject();
		mpec.searchTeacher("");
		mpec.searchStudent("");
	}

	public void setUsername(String username) {
		((PageHeader) PageHeader.getPage()).updateUsername(username);
	}

	public void getContent(String content) {
		if(LoginController.getController().getCurrentUser().isRole()) ((TeacherPage) TeacherPage.getPage()).changeContent(content);
		else ((ManagerPage) ManagerPage.getPage()).changeContent(content);
	}

	private void getHeader(JPanel needed) {
		needed.add(PageHeader.getPage(), BorderLayout.NORTH);
	}

	private void getMenu(JPanel needed) {
		var mp = MenuPanel.getPage();
		needed.add(mp, BorderLayout.WEST);
		((MenuPanel) mp).changeMenu(needed instanceof TeacherPage);
	}
}
