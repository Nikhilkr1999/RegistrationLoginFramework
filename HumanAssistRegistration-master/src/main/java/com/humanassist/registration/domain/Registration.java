package com.humanassist.registration.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Registration implements Serializable {

    private Login login;
    private LocalDateTime registeredOn;
    private Integer userType;
    private String jwtToken;
    private String schemaName;

    private String orgName;

    public Registration(Login login, LocalDateTime registeredOn, Integer userType, String schemaName, String orgName) {
        this.login = login;
        this.registeredOn = registeredOn;
        this.userType = userType;
        this.schemaName = schemaName;
        this.orgName = orgName;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public LocalDateTime getRegisteredOn() {
        return registeredOn;
    }

    public void setRegisteredOn(LocalDateTime registeredOn) {
        this.registeredOn = registeredOn;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
}
