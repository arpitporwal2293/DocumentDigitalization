package com.m2l2.beans;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Data
@Component
public class Profile{

    private String id;
    private Person person;
    private String folderPath;
    private List<File> files = new ArrayList<>();
    private List<Document> documents = new ArrayList<>();

}
