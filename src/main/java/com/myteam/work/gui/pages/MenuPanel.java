package com.myteam.work.gui.pages;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import com.myteam.work.Configuration;
import com.myteam.work.controller.PageController;
import com.myteam.work.gui.Window;

import lombok.Getter;

public class MenuPanel extends JPanel {
	private static final Color background = new Color(15, 23, 42);
	private static final JButton[] teacherBtns = new JButton[2];
	private static final JButton[] managerBtns = new JButton[5];
	private static MenuPanel mp;
	private Boolean current;
	@Getter
	private boolean expand;
	private Timer sliding;
	private int currentWidth;

	private MenuPanel() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		var windowSize = Window.getWindow().getSize();
		this.currentWidth = 0;
		var height = windowSize.height / 10 * 9;
		this.setPreferredSize(new Dimension(0, height));
		this.setBackground(background);
		this.expand = false;
		this.sliding = new Timer(16, e -> {
			var targetWidth = expand ? windowSize.width / 7 : 0;
			currentWidth = currentWidth < targetWidth ? Math.min(currentWidth + 15, targetWidth) : Math.max(currentWidth - 15, targetWidth);

			setPreferredSize(new Dimension(currentWidth, height));
			revalidate();

			if(currentWidth == targetWidth) sliding.stop();
		});
		this.add(Box.createRigidArea(new Dimension(0, 20)));
		teacherBtns[0] = createMenuBtn("Student Management", "student");
		teacherBtns[1] = createMenuBtn("Subjects", "subject");
		managerBtns[0] = createMenuBtn("Student Management", "student");
		managerBtns[1] = createMenuBtn("Teacher Management", "teacher");
		managerBtns[2] = createMenuBtn("Subject Management", "subject");
		managerBtns[3] = createMenuBtn("Classes", "class");
		managerBtns[4] = createMenuBtn("Class Management", "classManagement");
	}

	public static JPanel getPage() {
		if(mp == null) mp = new MenuPanel();

		return mp;
	}

	public void changeMenu(boolean teacher) {
		if(teacher) {
			if(this.current != null && this.current) return;
			if(this.current != null) for(var i = 0; i < managerBtns.length << 1; i++) this.remove(this.getComponentCount() - 1);

			for(var i = 0; i < teacherBtns.length; i++) {
				this.add(teacherBtns[i]);
				this.add(Box.createRigidArea(new Dimension(0, 5)));
			}

			this.current = true;
		}else {
			if(this.current != null && !this.current) return;
			if(this.current != null) for(var i = 0; i < teacherBtns.length << 1; i++) this.remove(this.getComponentCount() - 1);

			for(var i = 0; i < managerBtns.length; i++) {
				this.add(managerBtns[i]);
				this.add(Box.createRigidArea(new Dimension(0, 5)));
			}

			this.current = false;
		}
	}

	public void toggleMenu() {
		this.expand = !this.expand;
		this.sliding.start();
	}

	private JButton createMenuBtn(String title, String content) {
		var btn = new JButton(title);
		btn.addActionListener(e -> PageController.getController().getContent(content));
		btn.setAlignmentX(Component.CENTER_ALIGNMENT);
		btn.setForeground(Color.WHITE);
		btn.setBackground(background);
		btn.setContentAreaFilled(false);
		btn.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
		btn.setFocusPainted(false);
		btn.setHorizontalAlignment(SwingConstants.LEFT);
		btn.setCursor(Configuration.getConfiguration().getHandCursor());

		return btn;
	}
}
