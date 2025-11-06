package com.myteam.work.management.data;

import java.time.LocalDate;

public class Users extends Information{
    private Authentication auth;
    private boolean ur;
    public Users(int id, String urName, LocalDate birth, String placeOfBirth, boolean sex, Authentication auth, boolean ur) {
        super(id, urName, birth, placeOfBirth, sex);
        this.auth = auth;
        this.ur = ur;
    }
    public Users() {
    }

    public Authentication getAuth() {
        return auth;
    }

    public void setAuth(Authentication auth) {
        this.auth = auth;
    }

    public boolean isUr() {
        return ur;
    }

    public void setUr(boolean ur) {
        this.ur = ur;
    }



    
}
