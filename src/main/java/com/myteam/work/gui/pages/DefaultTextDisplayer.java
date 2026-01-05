package com.myteam.work.gui.pages;

import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JTextField;

import com.myteam.work.Configuration;

public class DefaultTextDisplayer extends FocusAdapter {
	private final String defaultText;

		public DefaultTextDisplayer(String defaultText) {
			this.defaultText = defaultText;
		}

		@Override
		public void focusGained(FocusEvent e) {
			var textField = (JTextField) e.getSource();

			if(textField.getText().equals(defaultText)) {
				textField.setText("");
				textField.setForeground(new Color(30, 30, 30));
			}
		}

		@Override
		public void focusLost(FocusEvent e) {
			var textField = (JTextField) e.getSource();

			if(textField.getText().equals("")) {
				textField.setText(defaultText);
				textField.setForeground(Configuration.getConfiguration().getFieldColor());
			}
		}
}
