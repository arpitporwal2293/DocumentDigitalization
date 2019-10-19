package com.m2l2.beans;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Response {

    private String id;
    private List<String> errorValidation = new ArrayList<>();
    private Boolean fault = false;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getErrorValidation() {
        return errorValidation;
    }

    public void setErrorValidation(List<String> errorValidation) {
        this.errorValidation = errorValidation;
    }

    public Boolean getFault() {
        return fault;
    }

    public void setFault(Boolean fault) {
        this.fault = fault;
    }

    @Override
    public String toString() {
        return "Response{" +
                "id='" + id + '\'' +
                ", errorValidation=" + errorValidation +
                ", fault=" + fault +
                '}';
    }
}
