package com.m2l2.beans;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Document{

    private String name;
    private String dob;
    private String fathersName;

}
