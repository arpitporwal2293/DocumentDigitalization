package com.m2l2.beans;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Data
@Component
public class Profile{

    Person person;
    List<File> files;
    List<Document> documents = new ArrayList<>();

}
