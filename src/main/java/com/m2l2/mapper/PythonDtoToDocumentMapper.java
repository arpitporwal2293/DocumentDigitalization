package com.m2l2.mapper;

import com.m2l2.Parsers.DocumentParser;
import com.m2l2.beans.Document;
import com.m2l2.model.PythonDto;

import java.util.List;
import java.util.function.Function;

public class PythonDtoToDocumentMapper implements Function<PythonDto, Document> {
    @Override
    public Document apply(PythonDto pythonDto) {
        String type = pythonDto.getType();
        List<String> lines = pythonDto.getLines();
        Document document = new Document();
        switch (type) {
            case "pan":
                final DocumentParser.PanCard panCard = DocumentParser.parseDocument(new DocumentParser.PanCardInput(lines));
                document.setId(panCard.getPanNumber());
                document.setName(panCard.getName());
                document.setFathersName(panCard.getFathersName());
                document.setDob(panCard.getDateOfBirth());
                break;
            case "aadhar":
                final DocumentParser.AadharCard aadharCard = DocumentParser.parseDocument(new DocumentParser.AadharCardInput(lines));
                document.setId(aadharCard.getUuid());
                document.setName(aadharCard.getName());
                document.setFathersName(aadharCard.getFathersName());
                document.setDob(aadharCard.getDateOfBirth());
                document.setGender(aadharCard.getGender());
                break;
            case "voter":
                final DocumentParser.VoterIdCard voterIdCard = DocumentParser.parseDocument(new DocumentParser.VoterIdCardInput(lines));
                document.setId(voterIdCard.getVoterId());
                document.setName(voterIdCard.getName());
                document.setFathersName(voterIdCard.getFathersName());
                document.setDob(voterIdCard.getDateOfBirth());
                document.setGender(voterIdCard.getGender());
                break;
            default:
                return null;
        }
        document.setType(type);
        return document;
    }
}
