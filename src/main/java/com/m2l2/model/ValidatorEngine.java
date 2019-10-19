package com.m2l2.model;

import com.m2l2.beans.Document;
import com.m2l2.beans.File;
import com.m2l2.beans.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
public class ValidatorEngine {

    public final String PYTHON = "python";
    public final String SCRIPT = "C:/Demo/myscript.py";

    @Autowired
    Reader reader;

    public boolean validate(Profile profile) throws IOException {

        //add documents to profile
        for(File file : profile.getFiles()) {
            profile.getDocuments().add(getDetails(file));
        }

        //compare document information
        return compareDetails(profile.getDocuments().get(0),profile.getDocuments().get(1));
    }

    public Document getDetails(File file) throws IOException {
        String[] cmd = {PYTHON, SCRIPT, file.getPath()};
        Process p = Runtime.getRuntime().exec(cmd);
        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line = "";
        while ((line = br.readLine()) != null) {
            line = line + "\n";
        }
        return reader.read(line);
    }

    public Boolean compareDetails(Document d1, Document d2){
        if(d1.getName().toLowerCase().equals(d2.getName().toLowerCase()))
            return true;
        return false;
    }

}
