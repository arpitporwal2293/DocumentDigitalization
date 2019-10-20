package com.m2l2.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.m2l2.beans.Document;
import com.m2l2.mapper.PythonDtoToDocumentMapper;
import org.springframework.stereotype.Component;

@Component
public class Reader {

    public Document read(String data) throws RuntimeException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            final PythonDto pythonDto = mapper.readValue(data, PythonDto.class);
            return new PythonDtoToDocumentMapper().apply(pythonDto);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
