package com.humanassist.registration.domain;

import java.io.Serializable;

public class Login implements Serializable {


    private String emailId;
    private String password;

    public Login(String emailId, String password) {
        this.emailId = emailId;
        this.password = password;
    }
    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
