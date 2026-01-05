package com.myteam.work.gui;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

import com.myteam.work.gui.pages.LoginPage;
import com.myteam.work.gui.pages.ManagerPage;
import com.myteam.work.gui.pages.TeacherPage;
import com.myteam.work.management.handler.SQLHandler;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Window extends JFrame {
	private static Window window;
	@Getter
	private final CardLayout pageSwitcher;

	private Window() {
		var minimumSize = new Dimension(800, 600);
		this.setTitle("Score Managing System");
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setSize(minimumSize);
		this.setMinimumSize(minimumSize);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowListener() {
			@Override
			public void windowActivated(WindowEvent e) {}
			@Override
			public void windowClosed(WindowEvent e) {
				log.info("Window closed");
			}

			@Override
			public void windowClosing(WindowEvent e) {
				log.info("Window closing");
				SQLHandler.closeConnection();
				System.exit(0);
			}

			@Override
			public void windowDeactivated(WindowEvent e) {}
			@Override
			public void windowDeiconified(WindowEvent e) {}
			@Override
			public void windowIconified(WindowEvent e) {}
			@Override
			public void windowOpened(WindowEvent e) {
				log.info("Window opened");
			}
		});
		this.pageSwitcher = new CardLayout();
		this.setResizable(true);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	public void initializingPage() {
		log.info("initializing page");
		this.setLayout(this.pageSwitcher);
		this.add(new LoginPage(), "login");
		this.add(ManagerPage.getPage(), "manager");
		this.add(TeacherPage.getPage(), "teacher");
	}

	public static Window getWindow() {
		if(window == null) window = new Window();

		return window;
	}
}
