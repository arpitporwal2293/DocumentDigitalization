package com.m2l2.controller;

import com.m2l2.Model.ValidatorEngine;
import com.m2l2.beans.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class ValidateProfileController {

    @Autowired
    ValidatorEngine validatorEngine;

    @RequestMapping(
            value = "/validateProfile",
            method = RequestMethod.POST)
    public Boolean validateProfile(@RequestBody Profile profile) throws IOException {

        if(validatorEngine.validate(profile)) {
            return true;
        }else{
            return false;
        }
    }

}
