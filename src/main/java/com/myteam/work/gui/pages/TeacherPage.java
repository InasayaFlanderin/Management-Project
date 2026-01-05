package com.myteam.work.gui.pages;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.util.Collections;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.myteam.work.Configuration;
import com.myteam.work.controller.TeacherPageEventController;
import com.myteam.work.gui.pages.utilwin.SubmitWindow;
import com.myteam.work.management.data.Semester;
import com.myteam.work.management.data.Subject;
import com.myteam.work.management.data.TeachClass;

import lombok.Getter;

public class TeacherPage extends JPanel {
	private static final Configuration config = Configuration.getConfiguration();
	private static final String defaultText = "Search by subject name or subject id";
	private static TeacherPage tp;
	private final CardLayout pager;
	private final JPanel contentPanel;
	@Getter
	private MSTable subjectTable;
	private final TeacherPageEventController tpec;
	@Getter
	private JComboBox<Semester> semesterSelector;
	@Getter
	private JComboBox<TeachClass> classSelector;
	@Getter
	private JComboBox<Subject> subjectSelector;
	@Getter
	private MSTable studentTable;
	private JTextField searchField;

	private TeacherPage() {
		this.tpec = TeacherPageEventController.getController();
		this.setLayout(new BorderLayout());
		this.pager = new CardLayout();
		this.contentPanel = new JPanel(this.pager);
		this.contentPanel.add(studentManagementPage(), "student");
		this.contentPanel.add(subjectPage(), "subject");
		this.add(this.contentPanel, BorderLayout.CENTER);
	}

	public static JPanel getPage() {
		if(tp == null) tp = new TeacherPage();

		return tp;
	}

	public void changeContent(String content) {
		this.pager.show(this.contentPanel, content);
	}

	private JPanel studentManagementPage() {
		var contentPanel = new JPanel(new BorderLayout(15, 15));
		contentPanel.setBorder(new EmptyBorder(20, 25, 20, 25));
		contentPanel.setOpaque(false);
		var searchPanel = new JPanel(new BorderLayout(15, 0));
		searchPanel.setOpaque(false);
		var selectorPanel = new JPanel(new BorderLayout(12, 0));
		selectorPanel.setOpaque(false);
		this.semesterSelector = new JComboBox<>();
		this.semesterSelector.setPreferredSize(new Dimension(300, 0));
		this.semesterSelector.addActionListener(e -> loadTeachClass());
		this.semesterSelector.setRenderer(config.getComboBoxRenderer());
		this.classSelector = new JComboBox<>();
		this.classSelector.addActionListener(e -> tpec.loadStudentInTeachClass((TeachClass) classSelector.getSelectedItem()));
		this.classSelector.setRenderer(config.getComboBoxRenderer());
		this.subjectSelector = new JComboBox<>();
		this.subjectSelector.addActionListener(e -> loadTeachClass());
		this.subjectSelector.setPreferredSize(new Dimension(500, 0));
		this.subjectSelector.setRenderer(config.getComboBoxRenderer());
		var submitBtn = new JButton("Submit Change");
		submitBtn.addActionListener(e -> createSubmitWindow());
		selectorPanel.add(this.semesterSelector, BorderLayout.WEST);
		selectorPanel.add(this.subjectSelector, BorderLayout.CENTER);
		selectorPanel.add(this.classSelector, BorderLayout.EAST);
		searchPanel.add(selectorPanel, BorderLayout.CENTER);
		searchPanel.add(submitBtn, BorderLayout.EAST);
		this.studentTable = new MSTable(new String[]{"ID", "Student Name", "Sex", "Generation", "Test 1", "Test 2", "End test", "Total Score", "Normalized Score", "Rate"},
				List.<Class<?>>of(String.class, String.class, Short.class, Float.class, Float.class, Float.class, Float.class, Float.class, String.class),
				List.of(3, 4, 5));
		this.studentTable.setRowHeight(42);
		this.studentTable.setShowGrid(true);
		this.studentTable.setPreferredWidth(0, 100);
		this.studentTable.setPreferredWidth(2, 110);
		this.studentTable.setPreferredWidth(3, 110);
		this.studentTable.setPreferredWidth(4, 112);
		this.studentTable.setPreferredWidth(5, 112);
		this.studentTable.setPreferredWidth(6, 112);
		this.studentTable.setPreferredWidth(7, 112);
		this.studentTable.setPreferredWidth(8, 112);
		this.studentTable.setPreferredWidth(9, 111);
		this.studentTable.setIntercellSpacing(new Dimension(1, 1));
		this.studentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.studentTable.setReorderingColumn(false);
		this.studentTable.setResizingColumn(false);
		this.studentTable.setDestination(this.tpec.getRecorder());
		contentPanel.add(searchPanel, BorderLayout.NORTH);
		contentPanel.add(this.studentTable.getDisplayer(), BorderLayout.CENTER);

		return contentPanel;
	}

	private JPanel subjectPage() {
		var contentPanel = new JPanel(new BorderLayout(15, 15));
		contentPanel.setBorder(new EmptyBorder(20, 25, 20, 25));
		contentPanel.setOpaque(false);
		var searchPanel = new JPanel(new BorderLayout(15, 0));
		searchPanel.setOpaque(false);
		searchPanel.setBorder(new EmptyBorder(0, 0, 15, 0));
		this.searchField = new JTextField(defaultText);
		this.searchField.setBorder(null);
		this.searchField.setForeground(config.getFieldColor());
		this.searchField.addFocusListener(new DefaultTextDisplayer(defaultText));
		this.searchField.getDocument().addDocumentListener(new DocumentListener() {
			private final Timer updater = new Timer(125, e -> {
				if(searchField.getText().equals(defaultText)) tpec.loadAllSubject();
				else tpec.searchSubject(searchField.getText());
			});

			@Override
			public void changedUpdate(DocumentEvent e) {
				updater.setRepeats(false);
				updater.restart();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				updater.setRepeats(false);
				updater.restart();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				updater.setRepeats(false);
				updater.restart();
			}
		});
		searchPanel.add(this.searchField, BorderLayout.CENTER);
		this.subjectTable = new MSTable(new String[]{"ID", "Subject name", "Prerequisites", "Credits", "Require"}, 
				List.<Class<?>>of(String.class, String[].class, Short.class, String.class), Collections.EMPTY_LIST);
		this.subjectTable.setRowHeight(42);
		this.subjectTable.setShowGrid(true);
		this.subjectTable.setPreferredWidth(0, 191);
		this.subjectTable.setPreferredWidth(1, 500);
		this.subjectTable.setPreferredWidth(2, 500);
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

	public void logout() {
		this.subjectTable.clearData();
		this.searchField.setText(defaultText);
		this.searchField.setForeground(config.getFieldColor());
		this.studentTable.clearData();
		this.semesterSelector.removeAll();
		this.subjectSelector.removeAll();
		this.classSelector.removeAll();
		this.tpec.getRecorder().clear();
		changeContent("student");
	}

	private void loadTeachClass() {
		tpec.loadTeachClass((Semester) this.semesterSelector.getSelectedItem(), (Subject) this.subjectSelector.getSelectedItem());	
	}

	private void createSubmitWindow() {
		var submitWin = new SubmitWindow(true);
		submitWin.setSubmitAction(e -> {
			tpec.submit((TeachClass) this.classSelector.getSelectedItem());
			submitWin.dispose();
		});
		submitWin.setRevokeAction(e -> {
			tpec.getRecorder().clear();
			tpec.loadStudentInTeachClass((TeachClass) this.classSelector.getSelectedItem());
			submitWin.dispose();
		});
		submitWin.setCancelAction(e -> submitWin.dispose());
	}
}
