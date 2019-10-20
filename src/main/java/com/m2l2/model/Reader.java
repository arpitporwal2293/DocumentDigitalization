package com.m2l2.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.m2l2.beans.Document;
import com.m2l2.mapper.PythonDtoToDocumentMapper;
import org.springframework.stereotype.Component;

@Component
public class Reader {

    //TODO
    public Document read(PythonDto pythonDto) throws RuntimeException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return new PythonDtoToDocumentMapper().apply(pythonDto);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
