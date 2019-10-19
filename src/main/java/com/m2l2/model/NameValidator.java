package com.m2l2.model;

import org.springframework.stereotype.Component;

@Component
public class NameValidator {



    public Boolean checkName(String name1, String name2){

     Boolean flag = false;
     String name1Arr[] = name1.split(" ");
     String name2Arr[] = name2.split(" ");

     for(int i=0;i<name1Arr.length;i++){
         for(int j=0;j<name2Arr.length;j++){
             if(name1Arr[i].toLowerCase().equals(name2Arr[j])){
                 flag = true;
             }
         }
     }

        for(int i=0;i<name2Arr.length;i++){
            for(int j=0;j<name1Arr.length;j++){
                if(name2Arr[i].toLowerCase().equals(name1Arr[j])){
                    flag = true;
                }
            }
        }

     return flag;
    }

}
