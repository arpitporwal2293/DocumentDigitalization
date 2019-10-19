package com.m2l2.beans;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@Component
public class Person {

    String name;
    Date dob;
    String address;

}
