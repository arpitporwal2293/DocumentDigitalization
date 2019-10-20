package com.m2l2.model;

import com.m2l2.beans.Person;
import com.m2l2.beans.Profile;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

@Component
public class FileReader {

    public List<Profile> readFiles(String path){

        List<Profile> profiles = new ArrayList<>();
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        if(listOfFiles!=null) {
            for (int i = 0; i < listOfFiles.length; i++) {

                Profile profile = new Profile();
                profile.setId(listOfFiles[i].getName());

                File f = new File(listOfFiles[i].getAbsolutePath());
                File[] lof = f.listFiles();
                if(lof!=null) {
                    for (int j = 0; j < lof.length; j++) {
                        if (lof[j].isFile()) {
                            com.m2l2.beans.File file = new com.m2l2.beans.File();
                            file.setPath(lof[j].getAbsolutePath());
                            file.setType(lof[j].getName());
                            profile.getFiles().add(file);
                        }
                    }
                }
                profiles.add(profile);
            }
        }

        return profiles;
    }

}
