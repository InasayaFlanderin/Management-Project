package com.myteam.work.gui.pages;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.util.Collections;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.myteam.work.Configuration;
import com.myteam.work.controller.ManagerPageEventController;

import lombok.Getter;

public class ManagerPage extends JPanel {
	private static final String subjectTableDefaultText = "Search by subject name or subject id";
	private static final Configuration config = Configuration.getConfiguration();
	private static ManagerPageEventController mpec;
	private static ManagerPage mp;
	private CardLayout pager;
	private JPanel contentPanel;
	@Getter
	private MSTable studentTable;
	@Getter
	private MSTable subjectTable;
	@Getter
	private MSTable teacherTable;
	@Getter
	private MSTable classTable;
	@Getter
	private MSTable semesterTable;
	@Getter
	private MSTable studentClassTable;
	@Getter
	private JComboBox classSemesterSelector;
	@Getter
	private JComboBox classManagementSemesterSelector;
	private JTextField subjectSearchField;

	private ManagerPage() {
		this.setLayout(new BorderLayout());
		this.pager = new CardLayout();
		this.contentPanel = new JPanel(this.pager);
		this.contentPanel.add(studentManagementPage(), "student");
		this.contentPanel.add(subjectManagementPage(), "subject");
		this.contentPanel.add(teacherManagementPage(), "teacher");
		this.contentPanel.add(classPage(), "class");
		this.contentPanel.add(classManagement(), "classManagement");
		this.add(contentPanel, BorderLayout.CENTER);
	}

	public static JPanel getPage() {
		if(mp == null) mp = new ManagerPage();

		return mp;
	}

	public void changeContent(String content) {
		this.pager.show(this.contentPanel, content);
	}

	private JPanel studentManagementPage() {
		var contentPanel = new JPanel();
		contentPanel.add(new JLabel("student"));

		this.studentTable = new MSTable(new String[]{"ID", "Student Name", "Birth", "Place of birth", "Sex", "Generation", "Gpa"},
				List.<Class<?>>of(Integer.class, String.class, String.class, String.class, String.class, Short.class, Float.class), Collections.EMPTY_LIST);

		return contentPanel;
	}

	private JPanel subjectManagementPage() {
		var contentPanel = new JPanel(new BorderLayout(15, 15));
		contentPanel.setBorder(new EmptyBorder(20, 25, 20, 25));
		contentPanel.setOpaque(false);
		var searchPanel = new JPanel(new BorderLayout(15, 0));
		searchPanel.setOpaque(false);
		var searchBtn = new JPanel(new BorderLayout());
		searchBtn.setOpaque(false);
		this.subjectSearchField = new JTextField(subjectTableDefaultText);
		this.subjectSearchField.setBorder(null);
		this.subjectSearchField.setForeground(config.getFieldColor());
		this.subjectSearchField.addFocusListener(new DefaultTextDisplayer(subjectTableDefaultText));
		this.subjectSearchField.getDocument().addDocumentListener(new DocumentListener() {
			private Timer updater = new Timer(125, e -> {
				if(subjectSearchField.getText().equals(subjectTableDefaultText)) mpec.loadAllSubject();
				else mpec.searchSubject(subjectSearchField.getText());

			});

			public void changedUpdate(DocumentEvent e) {
				updater.setRepeats(false);
				updater.restart();
			}

			public void insertUpdate(DocumentEvent e) {
				updater.setRepeats(false);
				updater.restart();
			}

			public void removeUpdate(DocumentEvent e) {
				updater.setRepeats(false);
				updater.restart();
			}
		});
		var subjectCreateBtn = new JButton("Create subject");
		var subjectEditBtn = new JButton("Edit subject");
		var subjectDeleteBtn = new JButton("Delete subject");
		searchBtn.add(subjectCreateBtn, BorderLayout.WEST);
		searchBtn.add(subjectEditBtn, BorderLayout.CENTER);
		searchBtn.add(subjectDeleteBtn, BorderLayout.EAST);
		searchPanel.add(this.subjectSearchField, BorderLayout.CENTER);
		searchPanel.add(searchPanel, BorderLayout.EAST);
		this.subjectTable = new MSTable(new String[]{"ID", "Subject name", "Prerequisites", "Credits", "Require"}, 
				List.<Class<?>>of(String.class, String[].class, Short.class, String.class), Collections.EMPTY_LIST);
		this.subjectTable.setRowHeight(42);
		this.subjectTable.setShowGrid(true);
		this.subjectTable.setPreferredWidth(0, 191);
		this.subjectTable.setPreferredWidth(3, 191);
		this.subjectTable.setPreferredWidth(4, 191);
		this.subjectTable.setIntercellSpacing(new Dimension(1, 1));
		this.subjectTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.subjectTable.setReorderingColumn(false);
		this.subjectTable.setResizingColumn(false);
		contentPanel.add(searchPanel, BorderLayout.NORTH);
		contentPanel.add(this.subjectTable.getDisplayer(), BorderLayout.CENTER);

		return contentPanel;
	}

	private JPanel teacherManagementPage() {
		var contentPanel = new JPanel();
		contentPanel.add(new JLabel("teacher"));

		this.teacherTable = new MSTable(new String[]{"ID", "Teacher name", "Username", "Password", "Birth", "Place of birth", "Sex", "Subject", "Teach class"},
				List.<Class<?>>of(Integer.class, String.class, String.class, String.class, String.class, String.class, String.class, String[].class, String[].class), Collections.EMPTY_LIST);

		return contentPanel;
	}

	private JScrollPane classPage() {
		var contentPanel = new JPanel(new BorderLayout(15, 15));
		contentPanel.setBorder(new EmptyBorder(20, 25, 20, 25));
		contentPanel.setOpaque(false);
		var classPanel = new JPanel(new BorderLayout(15, 0));
		var classSearchPanel = new JPanel(new BorderLayout(15, 0));
		var classSearchBtn = new JPanel(new BorderLayout());
		this.classSemesterSelector = new JComboBox<>();
		var classSearchField = new JTextField();
		var classCreateBtn = new JButton("Create class");
		var classEditBtn = new JButton("Edit class");
		var classDeleteBtn = new JButton("Delete class");
		classSearchBtn.add(classCreateBtn, BorderLayout.WEST);
		classSearchBtn.add(classEditBtn, BorderLayout.CENTER);
		classSearchBtn.add(classDeleteBtn, BorderLayout.EAST);
		classSearchPanel.add(this.classSemesterSelector, BorderLayout.WEST);
		classSearchPanel.add(classSearchField, BorderLayout.CENTER);
		classSearchPanel.add(classSearchBtn, BorderLayout.EAST);
		this.classTable = new MSTable(new String[]{"ID", "Class name", "Semester", "Subject", "GPA", "Teacher"},
				List.<Class<?>>of(Integer.class, String.class, String.class, String.class, Float.class, String[].class), Collections.EMPTY_LIST);
		classPanel.add(classSearchPanel, BorderLayout.NORTH);
		classPanel.add(this.classTable.getDisplayer(), BorderLayout.CENTER);
		var semesterPanel = new JPanel(new BorderLayout(15, 0));
		var semesterSearchPanel = new JPanel(new BorderLayout(15, 0));
		var semesterSearchBtn = new JPanel(new BorderLayout());
		var semesterSearchField = new JTextField();
		var semesterCreateBtn = new JButton("Create semester");
		var semesterEditBtn = new JButton("Edit semester");
		var semesterDeleteBtn = new JButton("Delete semester");
		semesterSearchBtn.add(semesterCreateBtn, BorderLayout.WEST);
		semesterSearchBtn.add(semesterEditBtn, BorderLayout.CENTER);
		semesterSearchBtn.add(semesterDeleteBtn, BorderLayout.EAST);
		semesterSearchPanel.add(semesterSearchField, BorderLayout.CENTER);
		semesterSearchPanel.add(semesterSearchBtn, BorderLayout.EAST);
		this.semesterTable = new MSTable(new String[]{"ID", "Semester", "Year"}, List.<Class<?>>of(Integer.class, Short.class, Short.class), Collections.EMPTY_LIST);
		semesterPanel.add(semesterSearchPanel, BorderLayout.NORTH);
		semesterPanel.add(this.semesterTable.getDisplayer(), BorderLayout.CENTER);
		contentPanel.add(classPanel, BorderLayout.CENTER);
		contentPanel.add(semesterPanel, BorderLayout.SOUTH);

		return new JScrollPane(contentPanel);
	}

	private JPanel classManagement() {
		var contentPanel = new JPanel(new BorderLayout(15, 15));
		contentPanel.setBorder(new EmptyBorder(20, 25, 20, 25));
		var searchPanel = new JPanel(new BorderLayout(15, 0));
		var searchBtn = new JPanel(new BorderLayout());
		searchPanel.setOpaque(false);
		this.classManagementSemesterSelector = new JComboBox();
		var searchField = new JTextField();
		var addStudentBtn = new JButton("Add student");
		var removeStudentBtn = new JButton("Remove student");
		searchBtn.add(addStudentBtn, BorderLayout.WEST);
		searchBtn.add(removeStudentBtn, BorderLayout.CENTER);
		searchPanel.add(this.classManagementSemesterSelector, BorderLayout.WEST);
		searchPanel.add(searchField, BorderLayout.CENTER);
		searchPanel.add(searchBtn, BorderLayout.EAST);
		this.studentClassTable = new MSTable(new String[]{"ID", "Student name", "Sex", "Generation", "Test 1", "Test 2", "End test", "Total Score", "Normalized Score", "Rate"},
				List.<Class<?>>of(Integer.class, String.class, String.class, Short.class, Float.class, Float.class, Float.class, Float.class, Float.class, String.class), Collections.EMPTY_LIST);
		contentPanel.add(searchPanel, BorderLayout.NORTH);
		contentPanel.add(this.studentClassTable.getDisplayer(), BorderLayout.CENTER);

		return contentPanel;
	}

	public void logout() {
		
	}
}
