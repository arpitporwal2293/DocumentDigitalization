package com.m2l2.beans;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
public class Document{

    private String name;
    private String dob;
    private String fathersName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getFathersName() {
        return fathersName;
    }

    public void setFathersName(String fathersName) {
        this.fathersName = fathersName;
    }

    @Override
    public String toString() {
        return "Document{" +
                "name='" + name + '\'' +
                ", dob='" + dob + '\'' +
                ", fathersName='" + fathersName + '\'' +
                '}';
    }
}
