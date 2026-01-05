package com.myteam.work.gui.pages;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.myteam.work.Configuration;
import com.myteam.work.controller.LoginController;
import com.myteam.work.gui.Window;

public class LoginPage extends JPanel {
	private static final Configuration config = Configuration.getConfiguration();
	private static final Color fieldColor = config.getFieldColor();
	private static final Color labelColor = new Color(50, 50, 50);
	private static final String defaultUsernamePlaceholder = "Please enter username!";
	private static final String defaultPasswordPlaceholder = "Please enter password!";
	private static final Image eye = new ImageIcon(LoginPage.class.getClassLoader().getResource("eye.png")).getImage();
	private static final Image eyeHide = new ImageIcon(LoginPage.class.getClassLoader().getResource("eyeHide.png")).getImage();

	public LoginPage() {
		var windowSize = Window.getWindow().getSize();
		var loginContainer = new JPanel(new GridBagLayout()) {
			@Override
			protected void paintComponent(Graphics g) {
				var g2d = (Graphics2D) g.create();
				g2d.setColor(Color.WHITE);
				g2d.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 100, 100);
				super.paintComponent(g);
			}
		};
		loginContainer.setPreferredSize(new Dimension(windowSize.width / 2 + 100, windowSize.height / 2 + 50));
		loginContainer.setBackground(Color.WHITE);
		loginContainer.setBorder(new EmptyBorder(40, 50, 40, 50));
		loginContainer.setOpaque(false);
		var gbc = new GridBagConstraints();
		gbc.insets = new Insets(8, 8, 8, 8);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		var loginStatus = new JLabel("");
		loginStatus.setForeground(Color.RED);
		gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.EAST;
		loginContainer.add(loginStatus, gbc);
		var usernameLabel = new JLabel("Username:");
		usernameLabel.setForeground(labelColor);
		gbc.gridy = 2; gbc.gridwidth = 1;
		loginContainer.add(usernameLabel, gbc);
		var passwordLabel = new JLabel("Password:");
		passwordLabel.setForeground(labelColor);
		gbc.gridy = 3;
		loginContainer.add(passwordLabel, gbc);
		var passwordField = new JPasswordField(defaultPasswordPlaceholder, 20) {
			public final char echoChar = this.getEchoChar();
			public boolean hide = true;

			@Override
			public void paint(Graphics g) {
				super.paint(g);
				var g2d = (Graphics2D) g;
				g2d.drawImage(hide ? eyeHide : eye, this.getWidth() - 25, (this.getHeight() - 20) / 2, null);
			}
		};
		passwordField.setForeground(fieldColor);
		passwordField.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(e.getX() >= passwordField.getWidth() - 30) passwordField.setFocusable(false);
				else {
					passwordField.setFocusable(true);
					passwordField.requestFocus();
				}

				if(new Rectangle(passwordField.getWidth() - 30, 0, 30, 30).contains(e.getPoint())) {
					passwordField.setEchoChar(passwordField.hide ? '\u0000' : passwordField.echoChar);
					passwordField.hide = !passwordField.hide;
					passwordField.repaint();
				}
			}
		});
		passwordField.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				if(new Rectangle(passwordField.getWidth() - 30, 0, 30, 30).contains(e.getPoint())) passwordField.setCursor(config.getHandCursor());
				else passwordField.setCursor(new Cursor(Cursor.TEXT_CURSOR));
			}
		});
		passwordField.addFocusListener(new DefaultTextDisplayer(defaultPasswordPlaceholder));
		gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
		loginContainer.add(passwordField, gbc);
		var usernameField = new JTextField(defaultUsernamePlaceholder, 20);
		usernameField.setForeground(fieldColor);
		usernameField.addFocusListener(new DefaultTextDisplayer(defaultUsernamePlaceholder));
		gbc.gridy = 2;
		loginContainer.add(usernameField, gbc);
		var title = new JLabel("Login");
		title.setForeground(new Color(40, 60, 120));
		gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER; gbc.fill = GridBagConstraints.NONE;
		loginContainer.add(title, gbc);
		var loginBtn = new JButton("Login!");
		loginBtn.setBackground(new Color(0, 120, 215));
		loginBtn.setForeground(Color.WHITE);
		loginBtn.setFocusPainted(false);
		loginBtn.setCursor(config.getHandCursor());
		loginBtn.addActionListener(e -> {
			var result = LoginController.getController().login(usernameField.getText(), new String(passwordField.getPassword()));
			loginStatus.setText(result ? "" : "Incorrect username or password! Please try again!");
			usernameField.setText(defaultUsernamePlaceholder);
			usernameField.setForeground(fieldColor);
			passwordField.setText(defaultPasswordPlaceholder);
			passwordField.setForeground(fieldColor);
			passwordField.hide = false;
			passwordField.setEchoChar(passwordField.echoChar);
			passwordField.repaint();
		});
		gbc.gridy = 4;
		loginContainer.add(loginBtn, gbc);
		this.setLayout(new GridBagLayout());
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		this.add(loginContainer, gbc);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		var g2d = (Graphics2D) g;
		g2d.setPaint(new GradientPaint(
					0, 0, new Color(74, 144, 226),
					this.getWidth(), this.getHeight(), new Color(155, 89, 182)));
		g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
	}
}
