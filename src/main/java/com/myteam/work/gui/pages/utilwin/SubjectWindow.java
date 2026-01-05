package com.myteam.work.gui.pages.utilwin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.myteam.work.Configuration;
import com.myteam.work.controller.SubjectWinController;
import com.myteam.work.gui.pages.DefaultTextDisplayer;
import com.myteam.work.gui.pages.MSTable;
import com.myteam.work.gui.pages.ManagerPage;
import com.myteam.work.management.data.Subject;

import lombok.Getter;

public class SubjectWindow extends JFrame {
    private static final Configuration config = Configuration.getConfiguration();
	private static final String defaultSubjectText = "Please enter subject name here";
    private static final String defaultSubjectCredits = "0";
    private static final String defaultSearchText = "Search by subject name or subject id";
    private static final Color PRIMARY_COLOR = new Color(41, 128, 185);
    private static final Color BACKGROUND_COLOR = new Color(236, 240, 241);
	@Getter
    private MSTable subjectTable;
	@Getter
	private MSTable choosenPrerequisitesTable;
	@Getter
	private Subject target;
	private SubjectWinController swc;
    
    @SuppressWarnings("LeakingThisInConstructor")
    public SubjectWindow(Subject target) {
        this.setTitle("Class");
        this.setSize(new Dimension(900, 500));
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(BACKGROUND_COLOR);
        
        var mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        var topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        topPanel.setBackground(Color.WHITE);
        topPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            new EmptyBorder(15, 15, 15, 15)
        ));
        
        var subjectName = createStyledTextField(defaultSubjectText, 300);
        var credits = createStyledTextField(defaultSubjectCredits, 80);
        var required = new JCheckBox("Required");
        required.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        required.setBackground(Color.WHITE);
        
        topPanel.add(new JLabel("Subject:"));
        topPanel.add(subjectName);
        topPanel.add(new JLabel("Credits:"));
        topPanel.add(credits);
        topPanel.add(required);
        
        var centerPanel = new JPanel(new GridLayout(1, 2, 15, 0));
        centerPanel.setBackground(BACKGROUND_COLOR);
        
        var choosenPanel = new JPanel(new BorderLayout(10, 10));
        choosenPanel.setBackground(Color.WHITE);
        choosenPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            new EmptyBorder(15, 15, 15, 15)
        ));
        
        var choosenLabel = new JLabel("Selected Prerequisites");
        choosenLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        choosenLabel.setForeground(PRIMARY_COLOR);
        
        this.choosenPrerequisitesTable = new MSTable(
            new String[]{"Id", "Subject name"}, 
            List.<Class<?>>of(Integer.class, String.class), 
            Collections.EMPTY_LIST
        );
        
        var prerequisitesBtnPanel = new JPanel(new GridLayout(2, 1, 5, 10));
        prerequisitesBtnPanel.setBackground(Color.WHITE);
        
        var addPrerequisitesBtn = createStyledButton("Add Prerequisite", PRIMARY_COLOR);	
        var removePrerequisitesBtn = createStyledButton("Remove Prerequisite", new Color(231, 76, 60));
        
        prerequisitesBtnPanel.add(addPrerequisitesBtn);
        prerequisitesBtnPanel.add(removePrerequisitesBtn);
        
        choosenPanel.add(choosenLabel, BorderLayout.NORTH);
        choosenPanel.add(this.choosenPrerequisitesTable.getDisplayer(), BorderLayout.CENTER);
        choosenPanel.add(prerequisitesBtnPanel, BorderLayout.SOUTH);
        
        var prerequisitesPanel = new JPanel(new BorderLayout(10, 10));
        prerequisitesPanel.setBackground(Color.WHITE);
        prerequisitesPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            new EmptyBorder(15, 15, 15, 15)
        ));
        
        var prereqLabel = new JLabel("Available Subjects");
        prereqLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        prereqLabel.setForeground(PRIMARY_COLOR);
        
        var subjectSearchPanel = new JPanel(new BorderLayout(10, 10));
        subjectSearchPanel.setBackground(Color.WHITE);
        
        var subjectSearch = createStyledTextField(defaultSearchText, 0); 
        this.subjectTable = new MSTable(
            new String[]{"ID", "Subject name", "Prerequisites", "Credits", "Require"}, 
            List.<Class<?>>of(String.class, String.class, String[].class, Short.class, String.class), 
            Collections.EMPTY_LIST
        );
        this.subjectTable.setReorderingColumn(false);
        this.subjectTable.setResizingColumn(false);

        
        var topSearchPanel = new JPanel(new BorderLayout(10, 10));
        topSearchPanel.setBackground(Color.WHITE);
        topSearchPanel.add(prereqLabel, BorderLayout.NORTH);
        topSearchPanel.add(subjectSearch, BorderLayout.CENTER);
        
        subjectSearchPanel.add(topSearchPanel, BorderLayout.NORTH);
        subjectSearchPanel.add(subjectTable.getDisplayer(), BorderLayout.CENTER);
        
        prerequisitesPanel.add(subjectSearchPanel, BorderLayout.CENTER);
        
        centerPanel.add(choosenPanel);
        centerPanel.add(prerequisitesPanel);
        
        var bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setBackground(BACKGROUND_COLOR);
        
        var submitBtn = createStyledButton("Submit", new Color(39, 174, 96));
        submitBtn.setPreferredSize(new Dimension(150, 40));
        
        bottomPanel.add(submitBtn);
        
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        this.add(mainPanel);
       
		subjectName.addFocusListener(new DefaultTextDisplayer(defaultSubjectText));
		credits.addFocusListener(new DefaultTextDisplayer(defaultSubjectCredits));
		subjectSearch.addFocusListener(new DefaultTextDisplayer(defaultSearchText));
		this.target = target;
		this.swc = new SubjectWinController();
		
		if(this.target != null) {
			subjectName.setText(this.target.getSubjectName());
			credits.setText("" + this.target.getCredits());
			required.setSelected(this.target.isRequired());
			this.swc.loadPrerequisites(this.target, this);
		}

		this.swc.loadAllSubject(this);
		subjectSearch.getDocument().addDocumentListener(new DocumentListener() {
			private final Timer updater = new Timer(125, e -> {
				if(subjectSearch.getText().equals(defaultSearchText)) swc.loadAllSubject(SubjectWindow.this);
				else swc.searchSubject(SubjectWindow.this, subjectSearch.getText());
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
		addPrerequisitesBtn.addActionListener(e -> {
			var selectedRow = subjectTable.getSelectedRow();

			if(selectedRow == -1) return;

			choosenPrerequisitesTable.addData(new Object[][]{new Object[]{
				subjectTable.getIDModel().getValueAt(selectedRow, 0),
				subjectTable.getContentModel().getValueAt(selectedRow, 0)
			}});
			swc.loadAllSubject(SubjectWindow.this);
		});
		removePrerequisitesBtn.addActionListener(e -> {
			var selectedRow = choosenPrerequisitesTable.getSelectedRow();

			if(selectedRow == -1) return;

			choosenPrerequisitesTable.removeRow(selectedRow);
			swc.loadAllSubject(SubjectWindow.this);
		});
		submitBtn.addActionListener(_ -> {
			var prerequisitesModel = choosenPrerequisitesTable.getIDModel();
			List<Integer> prerequisites = new LinkedList<>();

			for(var i = 0; i < prerequisitesModel.getRowCount(); i++) prerequisites.add((Integer) prerequisitesModel.getValueAt(i, 0));

			var submit = new SubmitWindow(false);
			submit.setCancelAction(_ -> submit.dispose());
			submit.setSubmitAction(_ -> {
				if(target == null) swc.createSubject(subjectName.getText(), Short.parseShort(credits.getText()), required.isSelected(), prerequisites);
				else swc.updateSubject(target, subjectName.getText(), credits.getText(), required.isSelected(), prerequisites);

				submit.dispose();
				SubjectWindow.this.dispose();
				((ManagerPage) ManagerPage.getPage()).refreshSubject();
			});
		});

        this.setVisible(true);
    }
    
    private JTextField createStyledTextField(String text, int width) {
        var textField = new JTextField(text);
        textField.setForeground(config.getConfiguration().getFieldColor());
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
