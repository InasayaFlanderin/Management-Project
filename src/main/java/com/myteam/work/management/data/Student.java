package com.myteam.work.management.data;

import java.time.LocalDate;

public class Student extends Information{
    private int generation;
    private float gpa;
    public Student(int id, String urName, LocalDate birth, String placeOfBirth, boolean sex, int generation, float gpa) {
        super(id, urName, birth, placeOfBirth, sex);
        this.generation = generation;
        this.gpa = gpa;
    }
    public Student() {
    }

    public int getGeneration() {
        return generation;
    }

    public void setGeneration(int generation) {
        this.generation = generation;
    }

    public float getGpa() {
        return gpa;
    }

    public void setGpa(float gpa) {
        this.gpa = gpa;
    }


    
}
