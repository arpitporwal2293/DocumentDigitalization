package com.m2l2.model;

import com.m2l2.Parsers.DateParser;
import com.m2l2.beans.CustomDate;
import com.m2l2.beans.Document;
import com.m2l2.beans.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class DateOfBirthComparator {

    @Autowired
    DateParser dateParser;

    public Response compareDOB(Document document1, Document document2, Response response){

        if(StringUtils.isEmpty(document1.getDob()) || StringUtils.isEmpty(document2.getDob())){
            response.setFault(true);
            response.getErrorValidation().add("Fault Found: Date of birth not found in document");
            return response;
        }

        CustomDate d1 = dateParser.parse(document1.getDob());
        CustomDate d2 = dateParser.parse(document2.getDob());

        if(!document1.getDob().equals(document2.getDob()) && !dateMonthYearCheck(d1,d2) && !monthYearCheck(d1,d2) && !yearCheck(d1,d2)){
            response.setFault(true);
            response.getErrorValidation().add("Fault Found: Date of birth mismatch");
        }

        return response;
    }

    public Boolean dateMonthYearCheck(CustomDate d1, CustomDate d2){
        if((d1.getDate()==null || d1.getDate()==null) && (monthYearCheck(d1, d2)))
            return true;
        return d1.equals(d2);
    }

    public Boolean monthYearCheck(CustomDate d1, CustomDate d2){
        if((d1.getMonth()==null || d1.getMonth()==null) && (yearCheck(d1, d2)))
            return true;
        if(d1.getMonth().equals(d2.getMonth()) && d1.getYear().equals(d2.getYear()))
            return true;
        return false;
    }

    public Boolean yearCheck(CustomDate d1, CustomDate d2){
        if(d1.getYear().equals(d2.getYear()))
            return true;
        return false;
    }

}
