package com.m2l2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.m2l2.beans.Response;
import com.m2l2.model.FileReader;
import com.m2l2.model.PersistResult;
import com.m2l2.model.ValidatorEngine;
import com.m2l2.beans.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class ValidateProfileController {

    private final String PATH = "C:\\Users\\arporwal\\Documents\\My Received Files\\investments\\IPSF kit FY 2018-19\\498068IPSF201819\\Rent Agreement\\";

    @Autowired
    ValidatorEngine validatorEngine;

    @Autowired
    FileReader fileReader;

    @Autowired
    PersistResult persistResult;

    @RequestMapping(
            value = "/validateProfile",
            method = RequestMethod.GET)
    public List<Response> validateProfile() throws IOException {
        List<Profile> profiles = fileReader.readFiles(PATH);
        List<Response> responses = validatorEngine.validate(profiles);
        persistResult.save(responses);
        return responses;
    }

}
