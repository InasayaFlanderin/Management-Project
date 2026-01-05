package com.myteam.work.gui.pages;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import com.myteam.work.Configuration;
import com.myteam.work.controller.LoginController;
import com.myteam.work.gui.Window;

public class PageHeader extends JPanel {
	private static final Color background = new Color(231, 76, 60);
	private static final ImageIcon menuIcon = new ImageIcon(new ImageIcon(PageHeader.class.getClassLoader().getResource("menu.png")).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
	private static final Font defaultFont = new JLabel().getFont();
	private static final Font headerFont = new Font(defaultFont.getName(), Font.BOLD | Font.ITALIC, defaultFont.getSize() * 2);
	private static final Configuration config = Configuration.getConfiguration();
	private static PageHeader ph;
	private final JLabel userLabel;

	private PageHeader() {
		super(new BorderLayout(20, 0));
		var windowSize = Window.getWindow().getSize();
		this.setPreferredSize(new Dimension(windowSize.width, windowSize.height / 10));
		this.setBackground(background);
		this.setBorder(BorderFactory.createCompoundBorder(
			BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(99, 102, 241)),
			BorderFactory.createEmptyBorder(20, 30, 20, 30)
		));
		var mp = MenuPanel.getPage();
		var menuBtn = new JButton();
		menuBtn.setBackground(background);
		menuBtn.setBorder(null);
		menuBtn.setContentAreaFilled(false);
		menuBtn.setIcon(menuIcon);
		menuBtn.setCursor(config.getHandCursor());
		menuBtn.setBorder(new LineBorder(Color.BLACK));
		menuBtn.addActionListener(e -> ((MenuPanel) mp).toggleMenu());
		var titleLabel = new JLabel("Score Management System");
		titleLabel.setFont(headerFont);
		titleLabel.setForeground(Color.WHITE);
		var userPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
		userPanel.setBackground(background);
		this.userLabel = new JLabel("");
		userLabel.setForeground(Color.WHITE);
		var logoutBtn = new JButton("Log out");
		logoutBtn.setBorder(new LineBorder(Color.WHITE));
		logoutBtn.setForeground(Color.WHITE);
		logoutBtn.setBackground(background);
		logoutBtn.setCursor(config.getHandCursor());
		logoutBtn.addActionListener(e -> {
			LoginController.getController().logout();

			if(((MenuPanel) mp).isExpand()) ((MenuPanel) mp).toggleMenu();
		});
		userPanel.add(this.userLabel);
		userPanel.add(new JSeparator(SwingConstants.VERTICAL));
		userPanel.add(logoutBtn);
		this.add(menuBtn, BorderLayout.WEST);
		this.add(titleLabel, BorderLayout.CENTER);
		this.add(userPanel, BorderLayout.EAST);
	}

	public static JPanel getPage() {
		if(ph == null) ph = new PageHeader();

		return ph;
	}

	public void updateUsername(String username) {
		this.userLabel.setText(username);
	}
}
