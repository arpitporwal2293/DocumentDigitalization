package com.m2l2.model;

import com.m2l2.beans.Document;
import com.m2l2.beans.Person;
import com.m2l2.beans.Profile;
import com.m2l2.beans.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DocumentComparator {

    @Autowired
    NameComparator nameComparator;

    @Autowired
    FatherNameComparator fatherNameComparator;

    @Autowired
    DateOfBirthComparator dateOfBirthComparator;

    public Response compare(List<Document> documents, Response response) {

        for(Document document : documents){
            nameComparator.compareName(documents.get(0), document, response);
            fatherNameComparator.compareName(documents.get(0), document, response);
            dateOfBirthComparator.compareDOB(documents.get(0), document, response);
        }
        return response;
    }
}
