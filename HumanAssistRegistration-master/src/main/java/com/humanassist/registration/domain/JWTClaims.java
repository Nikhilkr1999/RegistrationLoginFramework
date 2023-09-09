package com.humanassist.registration.domain;

import java.io.Serializable;

public class JWTClaims implements Serializable {

    private static final long serialVersionUID = 123L;
    private String schema;
    private String ownerEmail;

    public JWTClaims(String schema, String ownerEmail) {
        this.schema = schema;
        this.ownerEmail = ownerEmail;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }
}
