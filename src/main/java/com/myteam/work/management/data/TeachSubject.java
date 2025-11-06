package com.myteam.work.management.data;

public class TeachSubject {
    private Users teacher;
    private Subject subject;
    public TeachSubject() {
    }
    public TeachSubject(Users teacher, Subject subject) {
        this.teacher = teacher;
        this.subject = subject;
    }

    public Users getTeacher() {
        return teacher;
    }

    public void setTeacher(Users teacher) {
        this.teacher = teacher;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    
}
