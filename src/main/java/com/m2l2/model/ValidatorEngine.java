package com.m2l2.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.m2l2.beans.Document;
import com.m2l2.beans.File;
import com.m2l2.beans.Profile;
import com.m2l2.beans.Response;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class ValidatorEngine {

    public final String PYTHON = "python";
    public final String SCRIPT = "C:\\Users\\Netan\\Desktop\\Demo_Images\\main_file_2.py";

    @Autowired
    Reader reader;

    @Autowired
    DocumentComparator documentComparator;

    public List<Response> validate(List<Profile> profiles) throws IOException {
        List<Response> responses = new ArrayList<>();

        for (Profile profile : profiles) {

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
                getDetails(file);
            }

            //compare document information
            compareDetails(profile.getDocuments(), response);
            responses.add(response);
        }
        return responses;
    }

    public Document getDetails(File file) throws IOException {

        String output = "";

        try {
            output = getHTML("http://localhost:5000/" + file.getPath() + "?type=" + file.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }

        ObjectMapper m = new ObjectMapper();
        Output outputObject = m.readValue(output, Output.class);

        System.out.println(output);
        PythonDto pythonDto = new PythonDto();
        pythonDto.setLines(Arrays.asList(outputObject.getOutput().split("\\n\\n")));
        pythonDto.setType(file.getType().split("\\.")[0]);
        Document read = reader.read(pythonDto);
        return read;
    }


    public Response compareDetails(List<Document> documents, Response response) {
        return documentComparator.compare(documents, response);
    }

    public static String getHTML(String urlToRead) throws Exception {
        StringBuilder result = new StringBuilder();
        URL url = new URL(urlToRead);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();
        return result.toString();
    }

}
