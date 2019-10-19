package com.m2l2.beans;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Data
@Component
public class Response {

    private String id;
    private List<String> errorValidation = new ArrayList<>();
    private Boolean fault = false;

}
