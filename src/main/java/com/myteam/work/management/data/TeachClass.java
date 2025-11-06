package com.myteam.work.management.data;

public class TeachClass {
    private int id;
    private Semester semester;
    private String className;
    private int subject;
    private float gpa;
    public TeachClass(int id, Semester semester, String className, int subject, float gpa) {
        this.id = id;
        this.semester = semester;
        this.className = className;
        this.subject = subject;
        this.gpa = gpa;
    }
    

    public TeachClass() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getSubject() {
        return subject;
    }

    public void setSubject(int subject) {
        this.subject = subject;
    }

    public float getGpa() {
        return gpa;
    }

    public void setGpa(float gpa) {
        this.gpa = gpa;
    }

    

}
