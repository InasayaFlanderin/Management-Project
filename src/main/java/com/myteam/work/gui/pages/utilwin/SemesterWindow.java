package com.myteam.work.gui.pages.utilwin;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.myteam.work.controller.ManagerPageEventController;
import com.myteam.work.management.data.Semester;

public class SemesterWindow extends JFrame {

    private JTextField semesterField;
    private JTextField yearField;
    private JButton submitBtn;

    public SemesterWindow(Semester target) {
        setTitle("Create New Semester");
        setSize(420, 220);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        add(buildForm(target), BorderLayout.CENTER);
        add(buildFooter(target), BorderLayout.SOUTH);

        setVisible(true);
    }

    private JPanel buildForm(Semester target) {
        JPanel panel = new JPanel(new GridLayout(2, 2, 12, 12));
        panel.setBorder(new EmptyBorder(20, 25, 10, 25));

        panel.add(new JLabel("Semester"));
        semesterField = new JTextField();
        semesterField.setPreferredSize(new Dimension(200, 28));
        panel.add(semesterField);

  
        panel.add(new JLabel("Year"));
        yearField = new JTextField();
        yearField.setPreferredSize(new Dimension(200, 28));
        panel.add(yearField);

		if(target != null) {
			semesterField.setText(String.valueOf(target.getSemester()));
			yearField.setText(String.valueOf(target.getYears()));
		}

        return panel;
    }

    private JPanel buildFooter(Semester target) {
        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(0, 0, 15, 0));

        submitBtn = new JButton("Submit");
        submitBtn.addActionListener(e -> submit(target));

        panel.add(submitBtn);
        return panel;
    }

    private void submit(Semester target) {
        String semester = semesterField.getText().trim();
        String year = yearField.getText().trim();

       
        if (semester.isEmpty()) {
            JOptionPane.showMessageDialog(
                this,
                "Semester must not be empty",
                "Validation Error",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        if (year.isEmpty()) {
            JOptionPane.showMessageDialog(
                this,
                "Academic year must not be empty",
                "Validation Error",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        if(target == null) ManagerPageEventController
            .getController()
            .createSemester(semester, year);
		else ManagerPageEventController.getController().updateSemester(target, semester, year);

        dispose();
    }
}
