package com.myteam.work.management.data;

import java.time.LocalDate;

public class Information {
    private int id;;
    private String urName;
    private LocalDate birth;
    private String placeOfBirth;
    private boolean sex;
    public Information() {
    }
    public Information(int id, String urName, LocalDate birth, String placeOfBirth, boolean sex) {
        this.id = id;
        this.urName = urName;
        this.birth = birth;
        this.placeOfBirth = placeOfBirth;
        this.sex = sex;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrName() {
        return urName;
    }

    public void setUrName(String urName) {
        this.urName = urName;
    }

    public LocalDate getBirth() {
        return birth;
    }

    public void setBirth(LocalDate birth) {
        this.birth = birth;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    
}
