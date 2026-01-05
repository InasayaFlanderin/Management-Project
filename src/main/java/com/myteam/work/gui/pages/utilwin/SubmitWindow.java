package com.myteam.work.gui.pages.utilwin;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class SubmitWindow extends JFrame {
    private final JButton revoke;
    private final JButton cancel;
    private final JButton submit;

    public SubmitWindow(boolean showRevoke) {
        this.setTitle("Submit ?");
        this.setSize(new Dimension(450, 180));
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        var mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.WHITE);
        
        GridBagConstraints gbc = new GridBagConstraints();

        var question = new JLabel("Are you sure you want to proceed?");
        question.setFont(new Font("Arial", Font.PLAIN, 16));
        question.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = showRevoke ? 3 : 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(0, 0, 20, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(question, gbc);

        var buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false);
        GridBagConstraints btnGbc = new GridBagConstraints();
        btnGbc.gridy = 0;
        btnGbc.insets = new Insets(0, 5, 0, 5);
        btnGbc.fill = GridBagConstraints.HORIZONTAL;
        btnGbc.weightx = 1.0;

        this.cancel = new JButton("Cancel");
        this.revoke = new JButton("Revoke");
        this.submit = new JButton("Submit");

        styleButton(cancel, new Color(108, 117, 125), Color.WHITE);
        styleButton(revoke, new Color(220, 53, 69), Color.WHITE);
        styleButton(submit, new Color(40, 167, 69), Color.WHITE);

        if (showRevoke) {
            btnGbc.gridx = 0;
            buttonPanel.add(cancel, btnGbc);
            btnGbc.gridx = 1;
            buttonPanel.add(revoke, btnGbc);
            btnGbc.gridx = 2;
            buttonPanel.add(submit, btnGbc);
        } else {
            btnGbc.gridx = 0;
            buttonPanel.add(cancel, btnGbc);
            btnGbc.gridx = 1;
            buttonPanel.add(submit, btnGbc);
        }

        gbc.gridy = 1;
        gbc.gridwidth = showRevoke ? 3 : 2;
        gbc.weighty = 0;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(buttonPanel, gbc);

        this.add(mainPanel);
        this.setVisible(true);
    }

    private void styleButton(JButton button, Color bgColor, Color fgColor) {
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setPreferredSize(new Dimension(100, 35));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.darker());
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });
    }

    public void setRevokeAction(ActionListener al) {
        this.revoke.addActionListener(al);
    }

    public void setCancelAction(ActionListener al) {
        this.cancel.addActionListener(al);
    }

    public void setSubmitAction(ActionListener al) {
        this.submit.addActionListener(al);
    }
}