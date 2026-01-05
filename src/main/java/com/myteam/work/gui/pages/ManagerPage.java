package com.myteam.work.gui.pages;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

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
import javax.swing.table.TableModel;

import com.myteam.work.Configuration;
import com.myteam.work.controller.ManagerPageEventController;
import com.myteam.work.gui.pages.utilwin.ClassManagementWindow;
import com.myteam.work.gui.pages.utilwin.ClassWindow;
import com.myteam.work.gui.pages.utilwin.SemesterWindow;
import com.myteam.work.gui.pages.utilwin.StudentWindow;
import com.myteam.work.gui.pages.utilwin.SubjectWindow;
import com.myteam.work.gui.pages.utilwin.SubmitWindow;
import com.myteam.work.gui.pages.utilwin.TeacherWindow;
import com.myteam.work.management.data.Semester;
import com.myteam.work.management.data.Student;
import com.myteam.work.management.data.Subject;
import com.myteam.work.management.data.TeachClass;
import com.myteam.work.management.data.User;

import lombok.Getter;

public class ManagerPage extends JPanel {
	private static final String subjectTableDefaultText = "Search by subject name or subject id";
	private static final String teacherTableDefaultText = "Search by teacher name or teacher id";
	private static final String studentTableDefaultText = "Search by student name or student id or student's place";
	private static final String classTableDefaultText = "Search by class id or class name";
	private static final String semesterTableDefaultText = "Search by semester";
	private static final String classManagementTableDefaultText = "Search by class";
	private static final Configuration config = Configuration.getConfiguration();
	private static ManagerPageEventController mpec;
	private static ManagerPage mp;
	private final CardLayout pager;
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
	private JComboBox<Semester> classSemesterSelector;
	@Getter
	private JComboBox<Semester> classManagementSemesterSelector;
	@Getter
	private JComboBox<Subject> classManagementSubjectSelector;
	@Getter
	private JComboBox<TeachClass> classManagementClassSelector;
	@Getter
	private JComboBox<User> classManagementTeacherSelector;
	@Getter
	private JComboBox<User> teacherSelector;
	@Getter
	private JComboBox<Student> studentSelector;
	@Getter
	private JComboBox<Semester> semesterSelector;
	@Getter
	private JComboBox<String> birth;
	private JTextField subjectSearchField;
	private JTextField teacherSearchField;
	private JTextField studentSearchField;
	private Runnable updateStudent;
	private Runnable updateTeacher;
	private Runnable updateSubject;
	private Runnable updateClass;

	private ManagerPage() {
		this.mpec = ManagerPageEventController.getController();
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
		if (mp == null)
			mp = new ManagerPage();

		return mp;
	}

	public void changeContent(String content) {
		this.pager.show(this.contentPanel, content);
	}

	private JPanel studentManagementPage() {
		this.contentPanel = new JPanel(new BorderLayout(15, 15));
		contentPanel.add(new JLabel("student"));
		contentPanel.setBorder(new EmptyBorder(20, 25, 20, 25));
		var searchPanel = new JPanel(new BorderLayout(15, 0));
		var selectorPanel = new JPanel(new BorderLayout(12, 0));
		var searchBtn = new JPanel(new BorderLayout());
		searchPanel.setOpaque(false);

		this.studentSearchField = new JTextField(studentTableDefaultText);
		this.studentSearchField.setBorder(null);
		this.studentSearchField.setForeground(config.getFieldColor());
		this.studentSearchField.addFocusListener(new DefaultTextDisplayer(studentTableDefaultText));
		this.updateStudent = () -> {
			if (studentSearchField.getText().equals(studentTableDefaultText))
				mpec.searchStudent("");
			else
				mpec.searchStudent(studentSearchField.getText());
		};
		this.studentSearchField.getDocument().addDocumentListener(new DocumentListener() {
			private Timer updater = new Timer(125, e -> updateStudent.run());

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

		var addStudentBtn = new JButton("Add student");
		addStudentBtn.addActionListener(_ -> new StudentWindow(null));
		var removeStudentBtn = new JButton("Remove student");
		var editStudentBtn = new JButton("Edit Student");
		searchBtn.add(addStudentBtn, BorderLayout.WEST);
		searchBtn.add(editStudentBtn, BorderLayout.CENTER);
		searchBtn.add(removeStudentBtn, BorderLayout.EAST);

		selectorPanel.add(this.studentSearchField, BorderLayout.CENTER);

		searchPanel.add(selectorPanel, BorderLayout.CENTER);
		searchPanel.add(searchBtn, BorderLayout.EAST);

		this.studentTable = new MSTable(new String[] { "ID", "Student Name", "Birth",
				"Place of birth", "Sex", "Generation", "Gpa" },
				List.<Class<?>>of(Integer.class, String.class, String.class, String.class,
						String.class, Short.class, Float.class),
				Collections.EMPTY_LIST);

		contentPanel.add(searchPanel, BorderLayout.NORTH);
		contentPanel.add(this.studentTable.getDisplayer(), BorderLayout.CENTER);

		this.studentTable.setReorderingColumn(false);
		this.studentTable.setResizingColumn(false);

		editStudentBtn.addActionListener(_ -> {
			var selectedRow = studentTable.getSelectedRow();

			if(selectedRow == -1) return;

			var contentModel = studentTable.getContentModel();

			new StudentWindow(new Student(
						(Integer) studentTable.getIDModel().getValueAt(selectedRow, 0),
						(Short) contentModel.getValueAt(selectedRow, 4),
						(Float) contentModel.getValueAt(selectedRow, 5),
						(String) contentModel.getValueAt(selectedRow, 0),
						((LocalDate) contentModel.getValueAt(selectedRow, 1)).toString(),
						(String) contentModel.getValueAt(selectedRow, 2),
						((String) contentModel.getValueAt(selectedRow, 3)).equals("Male") ? true : false
						));
		});
		Runnable update = () -> mpec.searchStudent("");
		removeStudentBtn.addActionListener(_ -> createDeleteWindow(studentTable, mpec::deleteStudent, studentSearchField, studentTableDefaultText, update, mpec::searchStudent));
		return contentPanel;
	}

	private JPanel subjectManagementPage() {
		this.contentPanel = new JPanel(new BorderLayout(15, 15));
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
		this.updateSubject = () -> {
			if (subjectSearchField.getText().equals(subjectTableDefaultText))
				mpec.loadAllSubject();
			else
				mpec.searchSubject(subjectSearchField.getText());
		};
		this.subjectSearchField.getDocument().addDocumentListener(new DocumentListener() {
			private final Timer updater = new Timer(125, e -> updateSubject.run());

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
		var subjectCreateBtn = new JButton("Create subject");
		subjectCreateBtn.addActionListener(_ -> new SubjectWindow(null));
		var subjectEditBtn = new JButton("Edit subject");	
		var subjectDeleteBtn = new JButton("Delete subject");

		searchBtn.add(subjectCreateBtn, BorderLayout.WEST);
		searchBtn.add(subjectEditBtn, BorderLayout.CENTER);
		searchBtn.add(subjectDeleteBtn, BorderLayout.EAST);
		searchPanel.add(this.subjectSearchField, BorderLayout.CENTER);
		searchPanel.add(searchBtn, BorderLayout.EAST);
		this.subjectTable = new MSTable(new String[] { "ID", "Subject name", "Prerequisites", "Credits", "Require" },
				List.<Class<?>>of(Integer.class, String.class, String[].class, Short.class, String.class),
				Collections.EMPTY_LIST);
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
		subjectEditBtn.addActionListener(_ -> {
			var selectedRow = subjectTable.getSelectedRow();

			if(selectedRow == -1) return;

			var contentModel = subjectTable.getContentModel();

			new SubjectWindow(new Subject(
						(Integer) subjectTable.getIDModel().getValueAt(selectedRow, 0),
						(Short) contentModel.getValueAt(selectedRow, 2),
						((String) contentModel.getValueAt(selectedRow, 3)).equals("yes") ? true : false,
						(String) contentModel.getValueAt(selectedRow, 0)
						));
		});
		subjectDeleteBtn.addActionListener(_ -> createDeleteWindow(subjectTable, mpec::deleteSubject, subjectSearchField, subjectTableDefaultText, mpec::loadAllSubject, mpec::searchSubject));

		return contentPanel;
	}

	@Deprecated
	private JPanel teacherManagementPage() {
		this.contentPanel = new JPanel(new BorderLayout(15, 15));
		contentPanel.add(new JLabel("teacher"));
		contentPanel.setBorder(new EmptyBorder(20, 25, 20, 25));
		var searchPanel = new JPanel(new BorderLayout(15, 0));
		var selectorPanel = new JPanel(new BorderLayout(12, 0));
		var searchBtn = new JPanel(new BorderLayout());
		searchPanel.setOpaque(false);

		this.teacherSelector = new JComboBox<>();
		this.teacherSelector.setRenderer(config.getComboBoxRenderer());
		this.teacherSelector.addActionListener(e -> loadManagementTeachClass());

		this.teacherSearchField = new JTextField(teacherTableDefaultText);
		this.teacherSearchField.setBorder(null);
		this.teacherSearchField.setForeground(config.getFieldColor());
		this.teacherSearchField.addFocusListener(new DefaultTextDisplayer(teacherTableDefaultText));
		this.updateTeacher = () -> {
				if (teacherSearchField.getText().equals(teacherTableDefaultText))
					mpec.searchTeacher("");
				else
					mpec.searchTeacher(teacherSearchField.getText());

		};
		this.teacherSearchField.getDocument().addDocumentListener(new DocumentListener() {
			private final Timer updater = new Timer(125, e -> updateTeacher.run());

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

		var addTeacherBtn = new JButton("Add teacher");
		addTeacherBtn.addActionListener(_ -> new TeacherWindow(null));
		var editTeacherBtn = new JButton("Edit Teacher");
		var removeTeacherBtn = new JButton("Remove teacher");
		searchBtn.add(addTeacherBtn, BorderLayout.WEST);
		searchBtn.add(editTeacherBtn, BorderLayout.CENTER);
		searchBtn.add(removeTeacherBtn, BorderLayout.EAST);

		selectorPanel.add(this.teacherSearchField, BorderLayout.CENTER);

		searchPanel.add(selectorPanel, BorderLayout.CENTER);
		searchPanel.add(searchBtn, BorderLayout.EAST);

		this.teacherTable = new MSTable(
				new String[] { "ID", "Teacher name", "Username", "Password", "Birth", "Place of birth", "Sex",
						"Subject", "Teach class" },
				List.<Class<?>>of(Integer.class, String.class, String.class, String.class, String.class, String.class,
						String.class, String[].class, String[].class),
				Collections.EMPTY_LIST);

		contentPanel.add(searchPanel, BorderLayout.NORTH);
		contentPanel.add(this.teacherTable.getDisplayer(), BorderLayout.CENTER);
		editTeacherBtn.addActionListener(_ -> {
			var selectedRow = teacherTable.getSelectedRow();

			if(selectedRow == -1) return;

			var contentModel = teacherTable.getContentModel();

			new TeacherWindow(new User(
				(Integer) teacherTable.getIDModel().getValueAt(selectedRow, 0),
				(String) contentModel.getValueAt(selectedRow, 1),
				(String) contentModel.getValueAt(selectedRow, 2),
				true,
				(String) contentModel.getValueAt(selectedRow, 0),
				((LocalDate) contentModel.getValueAt(selectedRow, 3)).toString(),
				(String) contentModel.getValueAt(selectedRow, 4),
				((String) contentModel.getValueAt(selectedRow, 5)).equals("Male") ? true : false

			));
		});		
		this.teacherTable.setReorderingColumn(false);
		this.teacherTable.setResizingColumn(false);
		this.teacherTable.setRowHeight(42);
		this.teacherTable.setShowGrid(true);
		this.teacherTable.setIntercellSpacing(new Dimension(1, 1));
		Runnable update = () -> mpec.searchTeacher("");
		removeTeacherBtn.addActionListener(_ -> createDeleteWindow(teacherTable, mpec::deleteTeacher, teacherSearchField, teacherTableDefaultText, update , mpec::searchTeacher));
		return contentPanel;
	}

	private JScrollPane classPage() {
		this.contentPanel = new JPanel(new BorderLayout(15, 15));
		contentPanel.setBorder(new EmptyBorder(20, 25, 20, 25));
		contentPanel.setOpaque(false);
		var classPanel = new JPanel(new BorderLayout(15, 0));
		var classSearchPanel = new JPanel(new BorderLayout(15, 0));
		var classSearchBtn = new JPanel(new BorderLayout());
		var classSearchField = new JTextField(classTableDefaultText);
		classSearchField.setForeground(config.getFieldColor());
		classSearchField.addFocusListener(new DefaultTextDisplayer(classTableDefaultText));
		var classCreateBtn = new JButton("Create class");
		classCreateBtn.addActionListener(e -> new ClassWindow());
		var classEditBtn = new JButton("Edit class");
		classEditBtn.addActionListener(e -> new ClassWindow());
		var classDeleteBtn = new JButton("Delete class");
		classDeleteBtn.addActionListener(e -> new ClassWindow());
		classSearchBtn.add(classCreateBtn, BorderLayout.WEST);
		classSearchBtn.add(classEditBtn, BorderLayout.CENTER);
		classSearchBtn.add(classDeleteBtn, BorderLayout.EAST);
		classSearchPanel.add(classSearchField, BorderLayout.CENTER);
		classSearchPanel.add(classSearchBtn, BorderLayout.EAST);
		this.classTable = new MSTable(new String[] { "ID", "Class name", "Semester", "Subject", "Average", "Teacher" },
				List.<Class<?>>of(Integer.class, String.class, String.class, String.class, Float.class, String[].class),
				Collections.EMPTY_LIST);
		this.updateClass = () -> {
			if(classSearchField.getText().equals(classTableDefaultText)) mpec.loadAllClass();
			else mpec.searchClass(classSearchField.getText());
		};
		classSearchField.getDocument().addDocumentListener(new DocumentListener() {
			private final Timer updater = new Timer(125, e -> updateClass.run());

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
		classPanel.add(classSearchPanel, BorderLayout.NORTH);
		classPanel.add(this.classTable.getDisplayer(), BorderLayout.CENTER);
		var semesterPanel = new JPanel(new BorderLayout(15, 0));
		var semesterSearchPanel = new JPanel(new BorderLayout(15, 0));
		var semesterSearchBtn = new JPanel(new BorderLayout());
		var semesterSearchField = new JTextField();
		var semesterCreateBtn = new JButton("Create semester");
		semesterCreateBtn.addActionListener(e -> new SemesterWindow(null));
		var semesterEditBtn = new JButton("Edit semester");
		var semesterDeleteBtn = new JButton("Delete semester");
		semesterSearchBtn.add(semesterCreateBtn, BorderLayout.WEST);
		semesterSearchBtn.add(semesterEditBtn, BorderLayout.CENTER);
		semesterSearchBtn.add(semesterDeleteBtn, BorderLayout.EAST);
		semesterSearchPanel.add(semesterSearchField, BorderLayout.CENTER);
		semesterSearchPanel.add(semesterSearchBtn, BorderLayout.EAST);
		this.semesterTable = new MSTable(new String[] { "ID", "Semester", "Year" },
				List.<Class<?>>of(Integer.class, Short.class, Short.class), Collections.EMPTY_LIST);
		// semesterEditBtn.addActionListener(e -> new SemesterWindow(new Semester(

		// 				)));
		//semesterDeleteBtn.addActionListener(_ -> createDeleteWindow(this.semesterTable, ManagerPageEventController::deleteSemester, semesterSearchField, them default text o day, ham reload toan bo, ham reload khi co search))
		semesterPanel.add(semesterSearchPanel, BorderLayout.NORTH);
		semesterPanel.add(this.semesterTable.getDisplayer(), BorderLayout.CENTER);
		contentPanel.add(classPanel, BorderLayout.CENTER);
		contentPanel.add(semesterPanel, BorderLayout.SOUTH);

		semesterDeleteBtn.addActionListener(_ -> createDeleteWindow(semesterTable, mpec::deleteSemester, semesterSearchField, semesterTableDefaultText, mpec::loadSemester, mpec::searchSemester));
		return new JScrollPane(contentPanel);
	}

	private JPanel classManagement() {
		this.contentPanel = new JPanel(new BorderLayout(15, 15));
		contentPanel.setBorder(new EmptyBorder(20, 25, 20, 25));
		var searchPanel = new JPanel(new BorderLayout(15, 0));
		var selectorPanel = new JPanel(new BorderLayout(12, 0));
		var searchBtn = new JPanel(new BorderLayout(8, 0)); // Thêm gap giữa các components
		searchPanel.setOpaque(false);
		this.classManagementSemesterSelector = new JComboBox<>();
		this.classManagementSemesterSelector.setRenderer(config.getComboBoxRenderer());
		this.classManagementSemesterSelector.addActionListener(e -> loadManagementTeachClass());
		this.classManagementSubjectSelector = new JComboBox<>();
		this.classManagementSubjectSelector.setRenderer(config.getComboBoxRenderer());
		this.classManagementSubjectSelector.addActionListener(e -> loadManagementTeachClass());
		this.classManagementClassSelector = new JComboBox<>();
		this.classManagementClassSelector.setRenderer(config.getComboBoxRenderer());
		this.classManagementClassSelector.addActionListener(e -> mpec.loadStudentInTeachClass((TeachClass) ((JComboBox) e.getSource()).getSelectedItem()));
		var addStudentBtn = new JButton("Add student");
		//addStudentBtn.addActionListener(e -> new ClassManagementWindow(null));
		var editStudentBtn = new JButton("Edit student"); 
		var deleteStudentBtn = new JButton("Delete student");
		var inputArea = new JTextField();
		inputArea.setPreferredSize(new java.awt.Dimension(200, 30)); 
		addStudentBtn.addActionListener(e -> {
			var classes = (TeachClass) classManagementClassSelector.getSelectedItem();

			mpec.addStudentTeachClass(classes, inputArea.getText());
			mpec.loadStudentInTeachClass(classes);
		});
		searchBtn.add(inputArea, BorderLayout.WEST);
		searchBtn.add(addStudentBtn, BorderLayout.CENTER);
		//searchBtn.add(editStudentBtn, BorderLayout.CENTER);
		searchBtn.add(deleteStudentBtn, BorderLayout.EAST);
		searchBtn.setPreferredSize(new java.awt.Dimension(500, 35)); // Set kích thước cho cả panel searchBtn
		//searchBtn.add(removeStudentBtn, BorderLayout.EAST);
		selectorPanel.add(this.classManagementSemesterSelector, BorderLayout.WEST);
		selectorPanel.add(this.classManagementSubjectSelector, BorderLayout.CENTER);
		selectorPanel.add(this.classManagementClassSelector, BorderLayout.EAST);
		searchPanel.add(selectorPanel, BorderLayout.CENTER);
		searchPanel.add(searchBtn, BorderLayout.EAST);
		this.studentClassTable = new MSTable(
				new String[] { "ID", "Student name", "Sex", "Generation", "Test 1", "Test 2", "End test", "Total Score",
						"Normalized Score", "Rate" },
				List.<Class<?>>of(Integer.class, String.class, String.class, Short.class, Float.class, Float.class,
						Float.class, Float.class, Float.class, String.class),
				Collections.EMPTY_LIST);
		contentPanel.add(searchPanel, BorderLayout.NORTH);
		contentPanel.add(this.studentClassTable.getDisplayer(), BorderLayout.CENTER);
		this.studentClassTable.setReorderingColumn(false);
		this.studentClassTable.setResizingColumn(false);
		editStudentBtn.addActionListener(_ -> {
			var selectedRow = classTable.getSelectedRow();

			if(selectedRow == -1) return;

			var contentModel = studentClassTable.getContentModel();

			new ClassManagementWindow(new TeachClass(
				(Integer) classTable.getIDModel().getValueAt(selectedRow, 0),
				(String) contentModel.getValueAt(selectedRow, 1)
			));
		});
		deleteStudentBtn.addActionListener(e -> {
			var selectedRow =studentClassTable.getSelectedRow();

			if(selectedRow == -1) return;

			var classes = (TeachClass) classManagementClassSelector.getSelectedItem();

			mpec.removeStudentTeachClass(classes, (Integer) studentClassTable.getIDModel().getValueAt(selectedRow, 0));
			mpec.loadStudentInTeachClass(classes);
		});
		//deleteStudentBtn.addActionListener(_ -> createDeleteWindow(classManagementTable, mpec::deleteClassManagement, classManagementSearchField, classManagementTableDefaultText, mpec::loadAllClassManagement, mpec::searchClassManagement));
		return contentPanel;
	}

	public void logout() {
		changeContent("student");
	}

	public void refreshStudent() {
		this.updateStudent.run();
	}

	public void refreshSubject() {
		this.updateSubject.run();
	}

	private void loadManagementTeachClass() {
		mpec.loadTeachClass((Semester) this.classManagementSemesterSelector.getSelectedItem(),
				(Subject) this.classManagementSubjectSelector.getSelectedItem());
	}

	private void createDeleteWindow(MSTable table, Consumer<Integer> deleteFunc, JTextField searchBar,
			String defaultText, Runnable loadAll, Consumer<String> search) {
		var submitWin = new SubmitWindow(false);
		submitWin.setSubmitAction(_ -> {
			var selectedRow = table.getSelectedRow();

			if (selectedRow == -1)
				return;

			deleteFunc.accept((Integer) table.getIDModel().getValueAt(selectedRow, 0));

			if (searchBar.getText().equals(defaultText))
				loadAll.run();
			else
			 	search.accept(searchBar.getText());
			
			submitWin.dispose();
		});
		submitWin.setCancelAction(_ -> submitWin.dispose());
	}

	public void refreshTeacher() {
		this.updateTeacher.run();
	}

	public Object[] getTwoColumns(TableModel model, int row, int col1, int col2) {
		return new Object[] {
			model.getValueAt(row, col1),
			model.getValueAt(row, col2)
		};
	}

	
}
