package com.myteam.work.gui.pages.utilwin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.myteam.work.Configuration;
import com.myteam.work.controller.TeacherWinController;
import com.myteam.work.gui.pages.DefaultTextDisplayer;
import com.myteam.work.gui.pages.MSTable;
import com.myteam.work.gui.pages.ManagerPage;
import com.myteam.work.management.data.Pair;
import com.myteam.work.management.data.Subject;
import com.myteam.work.management.data.User;
import com.myteam.work.management.handler.SubjectHandler;
import com.myteam.work.management.handler.TeachClassHandler;

import lombok.Getter;

public class TeacherWindow extends JFrame {
    private static final Configuration config = Configuration.getConfiguration();
	private static final String defaultNameText = "Please enter name here";
	private static final String defaultBirthText = "yyyy-MM-dd";
	private static final String defaultPlaceOfBirthText = "Please enter place of birth here";
	private static final String defaultUsernameText = "Please enter username here";
	private static final String defaultPasswordText = "Please enter password here";
    private static final Color PRIMARY_COLOR = new Color(41, 128, 185);
    private static final Color BACKGROUND_COLOR = new Color(236, 240, 241);

    @Getter
    private User target;
    @Getter
    private MSTable choosenTeacherTable;
	@Getter
	private MSTable availableSubjectTable;
    @Getter
    private MSTable teacherTable;
	@Getter
	private MSTable availableClassTable;
    private TeacherWinController ttwc;

    @Deprecated
    public TeacherWindow(User target) {
        this.setTitle("Teacher Management");
        this.setSize(new Dimension(1100, 750));
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(BACKGROUND_COLOR);

        var mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        var topPanel = new JPanel(new GridBagLayout());
        topPanel.setBackground(Color.WHITE);
        topPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
                new EmptyBorder(15, 15, 15, 15)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        var maleRadio = new JRadioButton("Nam");
        var femaleRadio = new JRadioButton("Nữ");
        maleRadio.setSelected(true);
        var sexGroup = new ButtonGroup();
        sexGroup.add(maleRadio);
        sexGroup.add(femaleRadio);
        maleRadio.setBackground(Color.WHITE);
        femaleRadio.setBackground(Color.WHITE);
        maleRadio.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        femaleRadio.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        maleRadio.setCursor(config.getHandCursor());
        femaleRadio.setCursor(config.getHandCursor());
        var sexPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        sexPanel.setBackground(Color.WHITE);
        sexPanel.add(maleRadio);
        sexPanel.add(femaleRadio);

        var teacherNameField = createStyledTextField(defaultNameText, 250);
        var dateOfBirthField = createStyledTextField(defaultBirthText, 150);
        var birthPlaceField = createStyledTextField(defaultPlaceOfBirthText, 300);
        var usernameField = createStyledTextField(defaultUsernameText, 250);
        var passwordField = createStyledPasswordField(defaultPasswordText, 250);
		teacherNameField.addFocusListener(new DefaultTextDisplayer(defaultNameText));
		dateOfBirthField.addFocusListener(new DefaultTextDisplayer(defaultBirthText));
		birthPlaceField.addFocusListener(new DefaultTextDisplayer(defaultPlaceOfBirthText));
		usernameField.addFocusListener(new DefaultTextDisplayer(defaultUsernameText));
		passwordField.addFocusListener(new DefaultTextDisplayer(defaultPasswordText));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        topPanel.add(createLabel("Teacher Sex:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.3;
        topPanel.add(sexPanel, gbc);
        gbc.gridx = 2;
        gbc.weightx = 0;
        topPanel.add(createLabel("Teacher Name:"), gbc);
        gbc.gridx = 3;
        gbc.weightx = 0.7;
        topPanel.add(teacherNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        topPanel.add(createLabel("Date of Birth:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.3;
        topPanel.add(dateOfBirthField, gbc);
        gbc.gridx = 2;
        gbc.weightx = 0;
        topPanel.add(createLabel("Birth Place:"), gbc);
        gbc.gridx = 3;
        gbc.weightx = 0.7;
        topPanel.add(birthPlaceField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        topPanel.add(createLabel("Username:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.3;
        topPanel.add(usernameField, gbc);
        gbc.gridx = 2;
        gbc.weightx = 0;
        topPanel.add(createLabel("Password:"), gbc);
        gbc.gridx = 3;
        gbc.weightx = 0.7;
        topPanel.add(passwordField, gbc);

        var middlePanel = new JPanel(new GridLayout(2, 1, 0, 15));
        middlePanel.setBackground(BACKGROUND_COLOR);

        var subjectSection = new JPanel(new BorderLayout(10, 0));
        subjectSection.setBackground(BACKGROUND_COLOR);

        var subjectTablesPanel = new JPanel(new GridLayout(1, 2, 15, 0));
        subjectTablesPanel.setBackground(BACKGROUND_COLOR);

        var selectedSubjectPanel = new JPanel(new BorderLayout(5, 5));
        selectedSubjectPanel.setBackground(Color.WHITE);
        selectedSubjectPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
                new EmptyBorder(10, 10, 10, 10)));

        var selectedSubjectLabel = new JLabel("Selected Subjects");
        selectedSubjectLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        selectedSubjectLabel.setForeground(PRIMARY_COLOR);
        selectedSubjectPanel.add(selectedSubjectLabel, BorderLayout.NORTH);

        String[] selectedSubjectColumns = { "ID", "Subject Name" };
        var selectedSubjectTypes = new java.util.ArrayList<Class<?>>();
        selectedSubjectTypes.add(String.class);
        var selectedSubjectEditable = new java.util.ArrayList<Integer>();

        this.choosenTeacherTable = new MSTable(selectedSubjectColumns, selectedSubjectTypes, selectedSubjectEditable);

        var selectedSubjectScroll = choosenTeacherTable.getDisplayer();
        selectedSubjectScroll.setPreferredSize(new Dimension(250, 150));
        selectedSubjectPanel.add(selectedSubjectScroll, BorderLayout.CENTER);

        var availableSubjectPanel = new JPanel(new BorderLayout(5, 5));
        availableSubjectPanel.setBackground(Color.WHITE);
        availableSubjectPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
                new EmptyBorder(10, 10, 10, 10)));

        var availableSubjectLabel = new JLabel("Available Subjects");
        availableSubjectLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        availableSubjectLabel.setForeground(PRIMARY_COLOR);
        availableSubjectPanel.add(availableSubjectLabel, BorderLayout.NORTH);

        String[] availableSubjectColumns = { "ID", "Subject Name" };
        var availableSubjectTypes = new java.util.ArrayList<Class<?>>();
        availableSubjectTypes.add(String.class);
        var availableSubjectEditable = new java.util.ArrayList<Integer>();

        this.availableSubjectTable = new MSTable(availableSubjectColumns, availableSubjectTypes,
                availableSubjectEditable);

        var availableSubjectScroll = availableSubjectTable.getDisplayer();
        availableSubjectScroll.setPreferredSize(new Dimension(250, 150));
        availableSubjectPanel.add(availableSubjectScroll, BorderLayout.CENTER);

        subjectTablesPanel.add(selectedSubjectPanel);
        subjectTablesPanel.add(availableSubjectPanel);

        var subjectButtonsPanel = new JPanel(new GridLayout(2, 1, 0, 10));
        subjectButtonsPanel.setBackground(BACKGROUND_COLOR);

        var addSubjectBtn = createStyledButton("Add Subject", new Color(52, 152, 219));
        addSubjectBtn.setPreferredSize(new Dimension(150, 35));

        var removeSubjectBtn = createStyledButton("Remove Subject", new Color(231, 76, 60));
        removeSubjectBtn.setPreferredSize(new Dimension(150, 35));

        subjectButtonsPanel.add(addSubjectBtn);
        subjectButtonsPanel.add(removeSubjectBtn);

        var subjectButtonWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        subjectButtonWrapper.setBackground(BACKGROUND_COLOR);
        subjectButtonWrapper.add(subjectButtonsPanel);

        subjectSection.add(subjectTablesPanel, BorderLayout.CENTER);
        subjectSection.add(subjectButtonWrapper, BorderLayout.EAST);

        var classSection = new JPanel(new BorderLayout(10, 0));
        classSection.setBackground(BACKGROUND_COLOR);

        var classTablesPanel = new JPanel(new GridLayout(1, 2, 15, 0));
        classTablesPanel.setBackground(BACKGROUND_COLOR);

        var selectedClassPanel = new JPanel(new BorderLayout(5, 5));
        selectedClassPanel.setBackground(Color.WHITE);
        selectedClassPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
                new EmptyBorder(10, 10, 10, 10)));

        var selectedClassLabel = new JLabel("Selected Classes");
        selectedClassLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        selectedClassLabel.setForeground(PRIMARY_COLOR);
        selectedClassPanel.add(selectedClassLabel, BorderLayout.NORTH);

        String[] selectedClassColumns = { "ID", "Class Name" };
        var selectedClassTypes = new java.util.ArrayList<Class<?>>();
        selectedClassTypes.add(String.class);
        var selectedClassEditable = new java.util.ArrayList<Integer>();

        this.teacherTable = new MSTable(selectedClassColumns, selectedClassTypes, selectedClassEditable);

        var selectedClassScroll = teacherTable.getDisplayer();
        selectedClassScroll.setPreferredSize(new Dimension(250, 150));
        selectedClassPanel.add(selectedClassScroll, BorderLayout.CENTER);

        var availableClassPanel = new JPanel(new BorderLayout(5, 5));
        availableClassPanel.setBackground(Color.WHITE);
        availableClassPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
                new EmptyBorder(10, 10, 10, 10)));

        var availableClassLabel = new JLabel("Available Classes");
        availableClassLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        availableClassLabel.setForeground(PRIMARY_COLOR);
        availableClassPanel.add(availableClassLabel, BorderLayout.NORTH);

        String[] availableClassColumns = { "ID", "Class Name" };
        var availableClassTypes = new java.util.ArrayList<Class<?>>();
        availableClassTypes.add(String.class);
        var availableClassEditable = new java.util.ArrayList<Integer>();

        this.availableClassTable = new MSTable(availableClassColumns, availableClassTypes, availableClassEditable);

        var availableClassScroll = availableClassTable.getDisplayer();
        availableClassScroll.setPreferredSize(new Dimension(250, 150));
        availableClassPanel.add(availableClassScroll, BorderLayout.CENTER);

        classTablesPanel.add(selectedClassPanel);
        classTablesPanel.add(availableClassPanel);

        var classButtonsPanel = new JPanel(new GridLayout(2, 1, 0, 10));
        classButtonsPanel.setBackground(BACKGROUND_COLOR);

        var addClassBtn = createStyledButton("Add Class", new Color(52, 152, 219));
        addClassBtn.setPreferredSize(new Dimension(150, 35));

        var removeClassBtn = createStyledButton("Remove Class", new Color(231, 76, 60));
        removeClassBtn.setPreferredSize(new Dimension(150, 35));

        classButtonsPanel.add(addClassBtn);
        classButtonsPanel.add(removeClassBtn);

        var classButtonWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        classButtonWrapper.setBackground(BACKGROUND_COLOR);
        classButtonWrapper.add(classButtonsPanel);

        classSection.add(classTablesPanel, BorderLayout.CENTER);
        classSection.add(classButtonWrapper, BorderLayout.EAST);

        middlePanel.add(subjectSection);
        middlePanel.add(classSection);

        var bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        bottomPanel.setBackground(BACKGROUND_COLOR);

        var submitBtn = createStyledButton("Submit", new Color(39, 174, 96));
        submitBtn.setPreferredSize(new Dimension(150, 40));

        bottomPanel.add(submitBtn);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(middlePanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        this.add(mainPanel);

        this.target = target;
        this.ttwc = new TeacherWinController();

        if (this.target != null) {
            if (this.target.getInfo().isSex()) {
                maleRadio.setSelected(true);
            } else {
                femaleRadio.setSelected(true);
            }
            teacherNameField.setText(this.target.getInfo().getName());
            dateOfBirthField.setText((this.target.getInfo().getBirth()).toString());
            birthPlaceField.setText(
                    this.target.getInfo().getPlaceOfBirth() != "" ? this.target.getInfo().getPlaceOfBirth() : "");
			usernameField.setText(this.target.getAuthName());
			passwordField.setText(this.target.getAuthPass());
			teacherNameField.setForeground(new Color(30, 30, 30));
			dateOfBirthField.setForeground(new Color(30, 30, 30));
			birthPlaceField.setForeground(new Color(30, 30, 30));
			usernameField.setForeground(new Color(30, 30, 30));
			passwordField.setForeground(new Color(30, 30, 30));

			var teachedSubject = new SubjectHandler().loadTeacherSubject(this.target.getId());
			if(teachedSubject != null) {
				var objectList = new LinkedList<Object[]>();
				for(Subject s : teachedSubject) objectList.add(new Object[] {s.getId(), s.getSubjectName()});
				this.choosenTeacherTable.addData(objectList.toArray(Object[][]::new));
			}

			var teachedClass = new TeachClassHandler().getTeachesClassWithId(this.target.getId());
			if(teachedClass != null) {
				var objectList = new LinkedList<Object[]>();
				for(Pair<Integer, String> p : teachedClass) objectList.add(new Object[] {p.first(), p.second()});

				this.teacherTable.addData(objectList.toArray(Object[][]::new));
			}
        }

		this.ttwc.loadAvailableSubject(this);
		this.ttwc.loadAvailableClass(this);
		addSubjectBtn.addActionListener(_ -> {
			var selectedRow = availableSubjectTable.getSelectedRow();

			if(selectedRow == -1) return;

			choosenTeacherTable.addData(new Object[][]{new Object[]{
				availableSubjectTable.getIDModel().getValueAt(selectedRow, 0),
				availableSubjectTable.getContentModel().getValueAt(selectedRow, 0)
			}});
			ttwc.loadAvailableSubject(TeacherWindow.this);
			ttwc.loadAvailableClass(TeacherWindow.this);
		});
		removeSubjectBtn.addActionListener(_ -> {
			var selectedRow = choosenTeacherTable.getSelectedRow();

			if(selectedRow == -1) return;

			choosenTeacherTable.removeRow(selectedRow);
			ttwc.loadAvailableSubject(TeacherWindow.this);
			ttwc.loadAvailableClass(TeacherWindow.this);
		});

		addClassBtn.addActionListener(_ -> {
			var selectedRow = availableClassTable.getSelectedRow();

			if(selectedRow == -1) return;

			teacherTable.addData(new Object[][]{new Object[]{
				availableClassTable.getIDModel().getValueAt(selectedRow, 0),
				availableClassTable.getContentModel().getValueAt(selectedRow, 0)
			}});
			ttwc.loadAvailableSubject(TeacherWindow.this);
			ttwc.loadAvailableClass(TeacherWindow.this);
		});
		removeClassBtn.addActionListener(_ -> {
			var selectedRow = teacherTable.getSelectedRow();

			if(selectedRow == -1) return;

			teacherTable.removeRow(selectedRow);
			ttwc.loadAvailableSubject(TeacherWindow.this);
			ttwc.loadAvailableClass(TeacherWindow.this);
		});

        submitBtn.addActionListener(_ -> {
            var submit = new SubmitWindow(false);
            submit.setCancelAction(_ -> submit.dispose());
            submit.setSubmitAction(_ -> {
				var existedClass = new LinkedList<Integer>();
				var existedSubject = new LinkedList<Integer>();

				var existedSubjectTableModel = choosenTeacherTable.getIDModel();
				var existedClassTableModel = teacherTable.getIDModel();

				for(var i = 0; i < existedSubjectTableModel.getRowCount(); i++) existedSubject.add((Integer) existedSubjectTableModel.getValueAt(i, 0));
				for(var i = 0; i < existedClassTableModel.getRowCount(); i++) existedClass.add((Integer) existedClassTableModel.getValueAt(i, 0));

				if(target == null) ttwc.createTeacher(
						maleRadio.isSelected(),
						teacherNameField.getText(),
						LocalDate.parse(dateOfBirthField.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd")),
						birthPlaceField.getText(),
						usernameField.getText(),
						passwordField.getText(),
						existedSubject,
						existedClass
						);
				else ttwc.updateTeacher(
						target,
						maleRadio.isSelected(),
						teacherNameField.getText(),
						LocalDate.parse(dateOfBirthField.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd")),
						birthPlaceField.getText(),
						usernameField.getText(),
						passwordField.getText(),
						existedSubject,
						existedClass

						);
				
                submit.dispose();
                TeacherWindow.this.dispose();
                ((ManagerPage) ManagerPage.getPage()).refreshTeacher();
            });
        });

        this.setVisible(true);
    }

    private JLabel createLabel(String text) {
        var label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        label.setForeground(new Color(52, 73, 94));
        return label;
    }

    private JTextField createStyledTextField(String text, int width) {
        var textField = new JTextField(text);
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		textField.setForeground(config.getFieldColor());
        if (width > 0) {
            textField.setPreferredSize(new Dimension(width, 35));
        } else {
            textField.setPreferredSize(new Dimension(textField.getPreferredSize().width, 35));
        }
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
                new EmptyBorder(5, 10, 5, 10)));
        return textField;
    }

    private JPasswordField createStyledPasswordField(String text, int width) {
        var passwordField = new JPasswordField();
		passwordField.setForeground(config.getFieldColor());
        passwordField.setEchoChar((char) 0);
		passwordField.setText(text);
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        if (width > 0) {
            passwordField.setPreferredSize(new Dimension(width, 35));
        }
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
                new EmptyBorder(5, 10, 5, 10)));
        return passwordField;
    }

    private JButton createStyledButton(String text, Color bgColor) {
        var button = new JButton(text);
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(config.getHandCursor());
        button.setPreferredSize(new Dimension(180, 35));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(bgColor.brighter());
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }
}
