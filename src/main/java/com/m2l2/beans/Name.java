package com.m2l2.beans;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class Name {

    private String firstName;
    private String middleName;
    private String lastName;


    public Name(String name){
        if(name.contains(" ")) {
            if(name.split(" ").length==3) {
                firstName = name.split(" ")[0].toLowerCase();
                middleName = name.split(" ")[1].toLowerCase();
                lastName = name.split(" ")[2].toLowerCase();
            }else{
                firstName = name.split(" ")[0].toLowerCase();
                lastName = name.split(" ")[1].toLowerCase();
            }
        }else{
            firstName = name;
        }
    }
}
