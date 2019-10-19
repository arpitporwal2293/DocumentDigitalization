package com.m2l2.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.m2l2.beans.Document;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Reader {

    public Document read(String data) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(data, Document.class);
    }

}
