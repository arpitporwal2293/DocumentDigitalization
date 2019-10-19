package com.m2l2.model;

import com.m2l2.beans.Document;
import com.m2l2.beans.Name;
import com.m2l2.beans.Response;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class FatherNameComparator {

    public Response compareName(Document document1, Document document2, Response response){

        if(StringUtils.isEmpty(document1.getName()) || StringUtils.isEmpty(document2.getName())){
            response.setFault(true);
            response.getErrorValidation().add("Fault Found: Father's Name not found in document");
            return response;
        }

        Name name1 = new Name(document1.getName());
        Name name2 = new Name(document2.getName());

        if(!name1.equals(name2) && !checkFirstName(name1,name2) && !checkLastName(name1,name2)){
            response.setFault(true);
            response.getErrorValidation().add("Fault Found: Name mismatch");
        }

        return response;
    }

    public Boolean checkFirstName(Name name1, Name name2){
        if((name1.getFirstName()==null || name2.getFirstName()==null) && checkLastName(name1,name2)){
            return true;
        }
        if(name1.getFirstName().equals(name2.getFirstName()) && checkLastName(name1,name2)) {
            return true;
        }
        return false;
    }


    public Boolean checkLastName(Name name1, Name name2){
        if(((name1.getLastName()==null || name2.getLastName()==null)) && name1.getFirstName().equals(name1.getFirstName())){
            return true;
        }
        if(name1.getLastName().equals(name2.getLastName()))
            return true;
        return false;
    }

}
