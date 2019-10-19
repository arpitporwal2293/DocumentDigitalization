package com.m2l2.beans;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@Component
public class Person {

    private String name;
    private Date dob;
    private String address;

}
