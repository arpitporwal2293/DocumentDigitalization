package com.m2l2.beans;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Document{

    String type;
    String name;
    String dob;

}
