package com.m2l2.beans;

import lombok.Data;

public class File {

    private String path;
    private String type;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "File{" +
                "path='" + path + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
