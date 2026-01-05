package com.myteam.work.gui.pages.utilwin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.myteam.work.Configuration;
import com.myteam.work.controller.StudentWinController;
import com.myteam.work.gui.pages.DefaultTextDisplayer;
import com.myteam.work.gui.pages.MSTable;
import com.myteam.work.gui.pages.ManagerPage;
import com.myteam.work.management.data.Student;

import lombok.Getter;    

public class StudentWindow extends JFrame {
    private static final Configuration config = Configuration.getConfiguration();
    private static final String defaultStudentNameText = "Please enter student name here";
    private static final String defaultDateOfBirthText = "yyyy-MM-dd";
    private static final String defaultBirthPlaceText = "Please enter birth place here";
	private static final String defaultGenerationText = "Please enter generation here";
    private static final Color BACKGROUND_COLOR = new Color(236, 240, 241);
    
    @Getter
    private Student target;
    @Getter
    private MSTable choosenStudentTable;
    @Getter
    private MSTable studentTable;
    private StudentWinController stwc;
    private JButton add;
    private JButton submit;
    
    public StudentWindow(Student target) {
        this.setTitle("Student Management");
        this.setSize(new Dimension(900, 300));
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
            new EmptyBorder(15, 15, 15, 15)
        ));
        
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
        
        var studentNameField = createStyledTextField(defaultStudentNameText, 250);
        var dateOfBirthField = createStyledTextField(defaultDateOfBirthText, 150);
        var birthPlaceField = createStyledTextField(defaultBirthPlaceText, 300);
		var generationField = createStyledTextField(defaultGenerationText, 300);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        topPanel.add(createLabel("Student Sex:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.3;
        topPanel.add(sexPanel, gbc);
        
        gbc.gridx = 2;
        gbc.weightx = 0;
        topPanel.add(createLabel("Student Name:"), gbc);
        
        gbc.gridx = 3;
        gbc.weightx = 0.7;
        topPanel.add(studentNameField, gbc);

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
		topPanel.add(createLabel("Generation"), gbc);

		gbc.gridx = 1;
		topPanel.add(generationField, gbc);

        var bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        bottomPanel.setBackground(BACKGROUND_COLOR);
        
        var submitBtn = createStyledButton("Submit", new Color(39, 174, 96));
        submitBtn.setPreferredSize(new Dimension(150, 40));
        
        bottomPanel.add(submitBtn);
        
        mainPanel.add(topPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        this.add(mainPanel);

        studentNameField.addFocusListener(new DefaultTextDisplayer(defaultStudentNameText));
        dateOfBirthField.addFocusListener(new DefaultTextDisplayer(defaultDateOfBirthText));
        birthPlaceField.addFocusListener(new DefaultTextDisplayer(defaultBirthPlaceText));
		generationField.addFocusListener(new DefaultTextDisplayer(defaultGenerationText));
        
        this.target = target;
        this.stwc = new StudentWinController();
        
        if(this.target != null) {
            if(this.target.getInfo().isSex()) {
                femaleRadio.setSelected(true);
            } else {
                maleRadio.setSelected(true);
            }
            studentNameField.setText(this.target.getInfo().getName());
            dateOfBirthField.setText((this.target.getInfo().getBirth()).toString());
            birthPlaceField.setText(this.target.getInfo().getPlaceOfBirth() != "" ? 
                this.target.getInfo().getPlaceOfBirth() : "");
			generationField.setText(this.target.getGeneration() + "");
			studentNameField.setForeground(new Color(30, 30, 30));
			dateOfBirthField.setForeground(new Color(30, 30, 30));
			birthPlaceField.setForeground(new Color(30, 30, 30));
			generationField.setForeground(new Color(30, 30, 30));
        }

        submitBtn.addActionListener(_ -> {
            var submit = new SubmitWindow(false);
            submit.setCancelAction(_ -> submit.dispose());
            submit.setSubmitAction(_ -> {
                
                if(target == null) {
                    stwc.createStudent(studentNameField.getText(), LocalDate.parse(dateOfBirthField.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd")), maleRadio.isSelected(), birthPlaceField.getText(), generationField.getText());
                } else {
                    stwc.updateStudent(this.target, studentNameField.getText(), LocalDate.parse(dateOfBirthField.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd")), maleRadio.isSelected(), birthPlaceField.getText(), generationField.getText());
                }
                
                submit.dispose();
                StudentWindow.this.dispose();
				((ManagerPage) ManagerPage.getPage()).refreshStudent();
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
        textField.setForeground(config.getConfiguration().getFieldColor());
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        if (width > 0) {
            textField.setPreferredSize(new Dimension(width, 35));
        } else {
            textField.setPreferredSize(new Dimension(textField.getPreferredSize().width, 35));
        }
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            new EmptyBorder(5, 10, 5, 10)
        ));
        return textField;
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

    public void setAddAction(ActionListener al) {
        this.add.addActionListener(al);
    }

    public void setSubmitAction(ActionListener al) {
        this.submit.addActionListener(al);
    }
}
