package com.myteam.work.gui.pages.utilwin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class ClassWindow extends JFrame {
    
    private DefaultTableModel selectedTableModel;
    private DefaultTableModel availableTableModel;
    private JTable selectedClassTable;
    private JTable availableClassTable;
    private JComboBox<String> semesterComboBox;
    private JComboBox<String> subjectComboBox;
    
    public ClassWindow() {
        this.setTitle("Class Management");
        this.setSize(new Dimension(1100, 700));
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(new Color(236, 240, 241));
        
        var mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(new Color(236, 240, 241));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        var topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.setBackground(Color.WHITE);
        topPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            new EmptyBorder(20, 20, 20, 20)
        ));
        
        var titleLabel = new JLabel("Add New Class");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(new Color(41, 128, 185));

        var formPanel = new JPanel(new GridLayout(2, 4, 15, 15));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(new EmptyBorder(15, 0, 15, 0));

        var nameLabel = new JLabel("Class Name:");
        nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(nameLabel);
        
        var nameField = new JTextField();
        nameField.setPreferredSize(new Dimension(150, 35));
        nameField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            new EmptyBorder(5, 10, 5, 10)
        ));
        formPanel.add(nameField);

        var semesterLabel = new JLabel("Semester:");
        semesterLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(semesterLabel);
        
        String[] semesters = {};
        semesterComboBox = new JComboBox<>(semesters);
        semesterComboBox.setPreferredSize(new Dimension(150, 35));
        semesterComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        semesterComboBox.setBackground(Color.WHITE);
        formPanel.add(semesterComboBox);

        var subjectLabel = new JLabel("Subject:");
        subjectLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(subjectLabel);
        
        subjectComboBox = new JComboBox<>();
        subjectComboBox.addItem("Select Subject");
        subjectComboBox.setPreferredSize(new Dimension(150, 35));
        subjectComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subjectComboBox.setBackground(Color.WHITE);
        formPanel.add(subjectComboBox);

        var teacherLabel = new JLabel("Teacher:");
        teacherLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(teacherLabel);
        
        var teacherField = new JTextField();
        teacherField.setPreferredSize(new Dimension(150, 35));
        teacherField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            new EmptyBorder(5, 10, 5, 10)
        ));
        formPanel.add(teacherField);
        
        topPanel.add(titleLabel, BorderLayout.NORTH);
        topPanel.add(formPanel, BorderLayout.CENTER);

        var centerPanel = new JPanel(new GridLayout(1, 3, 15, 0));
        centerPanel.setBackground(new Color(236, 240, 241));

        var selectedPanel = createTablePanel("Selected Teachers", true);

        var middlePanel = new JPanel();
        middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.Y_AXIS));
        middlePanel.setBackground(new Color(236, 240, 241));
        middlePanel.add(Box.createVerticalGlue());
        
        var addBtn = createStyledButton("Add Teacher", new Color(52, 152, 219));
        addBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        addBtn.setMaximumSize(new Dimension(150, 40));
        middlePanel.add(addBtn);
        
        middlePanel.add(Box.createRigidArea(new Dimension(0, 15)));
        
        var removeBtn = createStyledButton("Remove Teacher", new Color(231, 76, 60));
        removeBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        removeBtn.setMaximumSize(new Dimension(150, 40));
        middlePanel.add(removeBtn);
        
        middlePanel.add(Box.createVerticalGlue());

        var availablePanel = createTablePanel("Available Teachers", false);
        
        centerPanel.add(selectedPanel);
        centerPanel.add(middlePanel);
        centerPanel.add(availablePanel);

        var bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        bottomPanel.setBackground(new Color(236, 240, 241));
        
        var submitBtn = createStyledButton("Submit", new Color(39, 174, 96));
        submitBtn.setPreferredSize(new Dimension(200, 45));
        bottomPanel.add(submitBtn);
        
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        this.add(mainPanel);

        addBtn.addActionListener(e -> {
            int selectedRow = availableClassTable.getSelectedRow();
            
            if(selectedRow == -1) {
                JOptionPane.showMessageDialog(
                    this,
                    "Please select a teacher from Available Teachers!",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE
                );
                return;
            }
            
            String id = availableTableModel.getValueAt(selectedRow, 0).toString();
            String teacher = availableTableModel.getValueAt(selectedRow, 1).toString();

            selectedTableModel.addRow(new Object[]{id, teacher});
            availableTableModel.removeRow(selectedRow);
        });

        removeBtn.addActionListener(e -> {
            int selectedRow = selectedClassTable.getSelectedRow();
            
            if(selectedRow == -1) {
                JOptionPane.showMessageDialog(
                    this,
                    "Please select a teacher from Selected Teachers!",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE
                );
                return;
            }
            
            String id = selectedTableModel.getValueAt(selectedRow, 0).toString();
            String teacher = selectedTableModel.getValueAt(selectedRow, 1).toString();

            availableTableModel.addRow(new Object[]{id, teacher});
            selectedTableModel.removeRow(selectedRow);
        });

        submitBtn.addActionListener(e -> {
            String className = nameField.getText().trim();
            String semester = (String) semesterComboBox.getSelectedItem();
            String subject = (String) subjectComboBox.getSelectedItem();
            String teacher = teacherField.getText().trim();
            
            if(className.isEmpty() || teacher.isEmpty()) {
                JOptionPane.showMessageDialog(
                    this,
                    "Please fill in Class Name and Teacher!",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE
                );
                return;
            }
            
            if(semester.equals("Select Semester")) {
                JOptionPane.showMessageDialog(
                    this,
                    "Please select a semester!",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE
                );
                return;
            }
            
            if(subject.equals("Select Subject")) {
                JOptionPane.showMessageDialog(
                    this,
                    "Please select a subject!",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE
                );
                return;
            }
            
            if(selectedTableModel.getRowCount() == 0) {
                JOptionPane.showMessageDialog(
                    this,
                    "Please select at least one teacher!",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            StringBuilder selectedTeachers = new StringBuilder();
            for(int i = 0; i < selectedTableModel.getRowCount(); i++) {
                if(i > 0) selectedTeachers.append(", ");
                selectedTeachers.append(selectedTableModel.getValueAt(i, 1));
            }

            nameField.setText("");
            teacherField.setText("");
            semesterComboBox.setSelectedIndex(0);
            subjectComboBox.setSelectedIndex(0);
            
            JOptionPane.showMessageDialog(
                this,
                "Class submitted successfully!\n" +
                "Class: " + className + "\n" +
                "Semester: " + semester + "\n" +
                "Subject: " + subject + "\n" +
                "Teachers: " + selectedTeachers.toString() + "\n" +
                "Main Teacher: " + teacher,
                "Success",
                JOptionPane.INFORMATION_MESSAGE
            );
        });
        
        this.setVisible(true);
    }
    
    private JPanel createTablePanel(String title, boolean isSelected) {
        var panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            new EmptyBorder(15, 15, 15, 15)
        ));
        
        var titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(new Color(41, 128, 185));

        String[] columnNames = {"ID", "Teacher Name"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        if(isSelected) {
            selectedTableModel = tableModel;
        } else {
            availableTableModel = tableModel;
        }
        
        JTable table = new JTable(tableModel);
        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.getTableHeader().setBackground(new Color(41, 128, 185));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setSelectionBackground(new Color(52, 152, 219, 100));

        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(200);
        
        if(isSelected) {
            selectedClassTable = table;
        } else {
            availableClassTable = table;
        }
        
        var scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199), 1));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JButton createStyledButton(String text, Color bgColor) {
        var button = new JButton(text);
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(150, 40));
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        
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
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ClassWindow());
    }
}