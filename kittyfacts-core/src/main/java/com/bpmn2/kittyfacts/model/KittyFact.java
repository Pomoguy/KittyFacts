package com.bpmn2.kittyfacts.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class KittyFact implements Serializable {
    String email;
    String fact;
    byte[] picture;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFact() {
        return fact;
    }

    public void setFact(String fact) {
        this.fact = fact;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public KittyFact(String email) {
        this.email = email;
    }

    public KittyFact() {
    }
}
