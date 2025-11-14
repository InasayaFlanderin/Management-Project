package com.myteam.work.gui.pages;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.FocusEvent;
import java.awt.GridBagConstraints;
import java.awt.event.FocusAdapter;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

import com.myteam.work.gui.Window;
import com.myteam.work.controller.LoginController;

public class LoginPage extends JPanel {
	private static LoginPage lp;
	private static LoginController controller;

	private class DefaultTextDisplayer extends FocusAdapter {
		private String defaultText;

		public DefaultTextDisplayer(String defaultText) {
			this.defaultText = defaultText;
		}

		@Override
		public void focusGained(FocusEvent e) {
			var tf = (JTextField) e.getSource();

			if(tf.getText().equals(defaultText)) {
				tf.setText("");
				tf.setForeground(Color.BLACK);
			}
		}

		@Override
		public void focusLost(FocusEvent e) {
			var tf = (JTextField) e.getSource();

			if(tf.getText().equals("")) {
				tf.setText(defaultText);
				tf.setForeground(Color.GRAY);
			}
		}
	}

	private LoginPage() {
		this.controller = new LoginController();
		var loginContainer = new JPanel(new GridBagLayout());
		var size = Window.getWindow().getSize();
		loginContainer.setPreferredSize(new Dimension(size.width / 2, size.height / 2));
		loginContainer.setBackground(Color.WHITE);
		var gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.LINE_END;
		var usernameText = new JLabel("Username: ");
		loginContainer.add(usernameText, gbc);
		gbc.gridy = 1;
		var passwordText = new JLabel("Password: ");
		loginContainer.add(passwordText, gbc);
		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.LINE_START;
		String defaultText = "Please enter password";
		var passwordPlaceHolder = new JPasswordField(defaultText);
		passwordPlaceHolder.addFocusListener(new DefaultTextDisplayer(defaultText));
		passwordPlaceHolder.setForeground(Color.GRAY);
		loginContainer.add(passwordPlaceHolder, gbc);
		gbc.gridy = 0;
		defaultText = "Please enter username";
		var usernamePlaceHolder = new JTextField(defaultText);
		usernamePlaceHolder.addFocusListener(new DefaultTextDisplayer(defaultText));
		usernamePlaceHolder.setForeground(Color.GRAY);
		loginContainer.add(usernamePlaceHolder, gbc);
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.fill = GridBagConstraints.NONE;
		var loginButton = new JButton("Login!");
		loginContainer.add(loginButton, gbc);
		loginButton.addActionListener(e -> {
			this.controller.login(usernamePlaceHolder.getText(), passwordPlaceHolder.getText());
		});
		this.setLayout(new GridBagLayout());
		this.setBackground(Color.BLACK);
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.weightx = 1;
		gbc.weighty = 1;
		this.add(loginContainer, gbc);
	}

	public static JPanel getPage() {
		if(lp == null) lp = new LoginPage();

		return lp;
	}
}
