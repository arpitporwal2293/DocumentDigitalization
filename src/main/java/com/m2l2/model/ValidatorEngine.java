package com.m2l2.model;

import com.m2l2.beans.Document;
import com.m2l2.beans.File;
import com.m2l2.beans.Profile;
import com.m2l2.beans.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class ValidatorEngine {

    public final String PYTHON = "python";
    public final String SCRIPT = "C:\\Users\\arporwal\\Documents\\My Received Files\\Important\\abc.py";

    @Autowired
    Reader reader;

    @Autowired
    DocumentComparator documentComparator;

    public List<Response> validate(List<Profile> profiles) throws IOException {
        List<Response> responses = new ArrayList<>();

        for(Profile profile : profiles) {

            Response response = new Response();
            response.setId(profile.getId());

            if (profile.getFiles().isEmpty()) {
                response.setFault(true);
                response.getErrorValidation().add("Fault Found: No documents found in directory, " + profile.getFolderPath());
                responses.add(response);
                continue;
            }

            //add documents to profile
            for (File file : profile.getFiles()) {
                //profile.getDocuments().add(getDetails(file));
                profile.getDocuments().add(getDetailsTry1(file));
                profile.getDocuments().add(getDetailsTry2(file));
            }

            //compare document information
            compareDetails(profile.getDocuments(), response);
            responses.add(response);
        }
        return responses;
    }

    public Document getDetailsTry1(File file) throws IOException {

        Document document = new Document();
        document.setName("Porwal");
        document.setFathersName("Hamendra Kumar Porwal");
        document.setDob("12/01/1994");

        return document;
    }

    public Document getDetailsTry2(File file) throws IOException {

        Document document = new Document();
        document.setName("Arpit Porwal");
        document.setFathersName("Hamendra1 Porwal1");
        document.setDob("1994");

        return document;
    }

    /*public Document getDetails(File file) throws IOException {

        String[] cmd = {PYTHON, SCRIPT, file.getPath()};
        Process p = Runtime.getRuntime().exec(cmd);
        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line = "";
        while ((line = br.readLine()) != null) {
            line = line + "\n";
        }
        return reader.read(line);
    }*/

    public Response compareDetails(List<Document> documents, Response response){
        return documentComparator.compare(documents, response);
    }

}
