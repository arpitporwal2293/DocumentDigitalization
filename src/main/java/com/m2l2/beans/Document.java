package com.m2l2.beans;

import org.springframework.stereotype.Component;

import java.util.StringJoiner;

@Component
public class Document {

    private String name;
    private String dob;
    private String fathersName;
    private String id;
    private String type;
    private String gender;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Document.class.getSimpleName() + "[", "]")
                .add("name='" + name + "'")
                .add("dob='" + dob + "'")
                .add("fathersName='" + fathersName + "'")
                .add("id='" + id + "'")
                .add("type='" + type + "'")
                .add("gender='" + gender + "'")
                .toString();
    }
}
