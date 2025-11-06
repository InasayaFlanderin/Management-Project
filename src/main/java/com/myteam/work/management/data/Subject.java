package com.myteam.work.management.data;

public class Subject {
    private int id;
    private int credits;
    private boolean required;
    private String subjectName;
    public Subject() {
    }
    public Subject(int id, int credits, boolean required, String subjectName) {
        this.id = id;
        this.credits = credits;
        this.required = required;
        this.subjectName = subjectName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    
}
