package com.myteam.work.management.data;

public class Authentication {
    private String authName;
    private String authPass;
    public Authentication() {
    }
    public Authentication(String authName, String authPass) {
        this.authName = authName;
        this.authPass = authPass;
    }

    public String getAuthName() {
        return authName;
    }

    public void setAuthName(String authName) {
        this.authName = authName;
    }

    public String getAuthPass() {
        return authPass;
    }

    public void setAuthPass(String authPass) {
        this.authPass = authPass;
    }

    
}
