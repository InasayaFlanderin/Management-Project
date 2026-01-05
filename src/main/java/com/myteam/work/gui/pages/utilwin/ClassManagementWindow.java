package com.myteam.work.gui.pages.utilwin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
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

import com.myteam.work.management.data.TeachClass;

public class ClassManagementWindow extends JFrame {
    
    private final DefaultTableModel tableModel;
    private JTable studentTable;
    private JTextField idField;
    private final JButton addBtn, submitBtn, deleteBtn;
    private int selectedRow = -1;
    private static TeachClass tc;
    
    public ClassManagementWindow(TeachClass tc) {
        this.setTitle("Student Management");
        this.setSize(new Dimension(900, 500));
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
        
        var titleLabel = new JLabel("Student Information");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(new Color(41, 128, 185));
        
        var formPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(new EmptyBorder(15, 0, 15, 0));
        
        var idLabel = new JLabel("Student ID:");
        idLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        idField = new JTextField();
        idField.setPreferredSize(new Dimension(250, 35));
        idField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            new EmptyBorder(5, 10, 5, 10)
        ));
        
        formPanel.add(idLabel);
        formPanel.add(idField);
        
        topPanel.add(titleLabel, BorderLayout.NORTH);
        topPanel.add(formPanel, BorderLayout.CENTER);

        var centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            new EmptyBorder(15, 15, 15, 15)
        ));

        var titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(Color.WHITE);
        
        var tableTitle = new JLabel("Student List");
        tableTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        tableTitle.setForeground(new Color(41, 128, 185));

        var titleButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        titleButtonPanel.setBackground(Color.WHITE);
        
        addBtn = createStyledButton("Add", new Color(39, 174, 96));
        deleteBtn = createStyledButton("Delete", new Color(231, 76, 60));
        
        addBtn.setPreferredSize(new Dimension(100, 35));
        deleteBtn.setPreferredSize(new Dimension(100, 35));
        
        titleButtonPanel.add(addBtn);
        titleButtonPanel.add(deleteBtn);
        
        titlePanel.add(tableTitle, BorderLayout.WEST);
        titlePanel.add(titleButtonPanel, BorderLayout.EAST);
        

        String[] columnNames = {"ID", "Name", "Sex", "Generation", "Test 1", "Test 2", "End Test", "Total Score", "Normalized Score", "Rate"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        studentTable = new JTable(tableModel);
        studentTable.setRowHeight(35);
        studentTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        studentTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        studentTable.getTableHeader().setBackground(new Color(41, 128, 185));
        studentTable.getTableHeader().setForeground(Color.WHITE);
        studentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        studentTable.setSelectionBackground(new Color(52, 152, 219, 100));

        studentTable.getColumnModel().getColumn(0).setPreferredWidth(80);   // ID
        studentTable.getColumnModel().getColumn(1).setPreferredWidth(150);  // Name
        studentTable.getColumnModel().getColumn(2).setPreferredWidth(60);   // Sex
        studentTable.getColumnModel().getColumn(3).setPreferredWidth(100);  // Generation
        studentTable.getColumnModel().getColumn(4).setPreferredWidth(80);   // Test 1
        studentTable.getColumnModel().getColumn(5).setPreferredWidth(80);   // Test 2
        studentTable.getColumnModel().getColumn(6).setPreferredWidth(90);   // End Test
        studentTable.getColumnModel().getColumn(7).setPreferredWidth(100);  // Total Score
        studentTable.getColumnModel().getColumn(8).setPreferredWidth(130);  // Normalized Score
        studentTable.getColumnModel().getColumn(9).setPreferredWidth(80);   // Rate
        
        var scrollPane = new JScrollPane(studentTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199), 1));
        
        centerPanel.add(titlePanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        var bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        bottomPanel.setBackground(new Color(236, 240, 241));
        
        submitBtn = createStyledButton("Submit", new Color(243, 156, 18));
        submitBtn.setPreferredSize(new Dimension(200, 45));
        
        bottomPanel.add(submitBtn);
        
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        this.add(mainPanel);
        
        studentTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                selectedRow = studentTable.getSelectedRow();
                if (selectedRow != -1) {
                    loadStudentToForm(selectedRow);
                }
            }
        });

        addBtn.addActionListener(e -> addStudent());

        submitBtn.addActionListener(e -> editStudent());
        
        deleteBtn.addActionListener(e -> deleteStudent());
        
        this.setVisible(true);
    }
    
    private void addStudent() {
        String id = idField.getText().trim();
        
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(
                this,
                "Please enter Student ID!",
                "Warning",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        for (int i = 0; i < tableModel.getRowCount(); i++) {
            if (tableModel.getValueAt(i, 0).toString().equals(id)) {
                JOptionPane.showMessageDialog(
                    this,
                    "Student ID already exists!",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE
                );
                return;
            }
        }

        tableModel.addRow(new Object[]{id, "", "", "", "", "", "", "", "", ""});
        
        JOptionPane.showMessageDialog(
            this,
            "Student added successfully!",
            "Success",
            JOptionPane.INFORMATION_MESSAGE
        );
        
        clearForm();
    }
    
    private void editStudent() {
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(
                this,
                "Please select a student to edit!",
                "Warning",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }
        
        String id = idField.getText().trim();
        
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(
                this,
                "Please enter Student ID!",
                "Warning",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        String currentId = tableModel.getValueAt(selectedRow, 0).toString();
        if (!id.equals(currentId)) {
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                if (i != selectedRow && tableModel.getValueAt(i, 0).toString().equals(id)) {
                    JOptionPane.showMessageDialog(
                        this,
                        "Student ID already exists!",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE
                    );
                    return;
                }
            }
        }
        
        tableModel.setValueAt(id, selectedRow, 0);
        
        JOptionPane.showMessageDialog(
            this,
            "Student updated successfully!",
            "Success",
            JOptionPane.INFORMATION_MESSAGE
        );
        
        clearForm();
    }
    
    private void deleteStudent() {
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(
                this,
                "Please select a student to delete!",
                "Warning",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to delete this student?",
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION
        );
        
        if (confirm == JOptionPane.YES_OPTION) {
            tableModel.removeRow(selectedRow);
            
            JOptionPane.showMessageDialog(
                this,
                "Student deleted successfully!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE
            );
            
            clearForm();
        }
    }
    
    private void loadStudentToForm(int row) {
        idField.setText(tableModel.getValueAt(row, 0).toString());
    }
    
    private void clearForm() {
        idField.setText("");
        studentTable.clearSelection();
        selectedRow = -1;
    }
    
    private JButton createStyledButton(String text, Color bgColor) {
        var button = new JButton(text);
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
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
        SwingUtilities.invokeLater(() -> new ClassManagementWindow(tc));
    }
}