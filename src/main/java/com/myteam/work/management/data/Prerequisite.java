package com.myteam.work.management.data;

public class Prerequisite {
    private Subject subject;
    private Subject require;
    public Prerequisite() {
    }
    public Prerequisite(Subject subject, Subject require) {
        this.subject = subject;
        this.require = require;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Subject getRequire() {
        return require;
    }

    public void setRequire(Subject require) {
        this.require = require;
    }

    
}
