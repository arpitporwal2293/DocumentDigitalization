package com.m2l2.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.m2l2.beans.Response;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class PersistResult {

    public void save(List<Response> responseList) throws JsonProcessingException, FileNotFoundException, UnsupportedEncodingException {

        ObjectMapper objectMapper = new ObjectMapper();
        String result = objectMapper.writeValueAsString(responseList);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        result = dtf.format(now)+ ": " + result;

        //write to new file
        PrintWriter writer = new PrintWriter(new FileOutputStream("C:\\application logs\\result.txt",false));
        writer.println(result);
        writer.close();

        //write to old file
        writer = new PrintWriter(new FileOutputStream("C:\\application logs\\logs.txt",true));
        writer.println(result);
        writer.println();
        writer.close();

    }

}
