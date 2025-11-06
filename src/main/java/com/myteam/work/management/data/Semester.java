package com.myteam.work.management.data;

public class Semester {
    private int id;
    private int semester;
    private int years;
    public Semester() {
    }
    public Semester(int id, int semester, int years) {
        this.id = id;
        this.semester = semester;
        this.years = years;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public int getYears() {
        return years;
    }

    public void setYears(int years) {
        this.years = years;
    }

    

}
