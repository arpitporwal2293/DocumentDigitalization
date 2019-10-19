package com.m2l2.model;

import com.m2l2.beans.Document;
import com.m2l2.beans.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class FatherNameComparator {

    @Autowired
    NameValidator nameValidator;

    public Response compareName(Document document1, Document document2, Response response){

        if(StringUtils.isEmpty(document1.getName()) || StringUtils.isEmpty(document2.getName())){
            response.setFault(true);
            response.getErrorValidation().add("Fault Found: Father's Name not found in document");
            return response;
        }

        if(!nameValidator.checkName(document1.getName(), document2.getName())) {
            response.setFault(true);
            response.getErrorValidation().add("Fault Found: Father's Name mismatch");
        }

        return response;
    }

}
