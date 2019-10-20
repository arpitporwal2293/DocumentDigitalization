package com.m2l2.Parsers;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.context.annotation.Scope;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("ALL")
@Scope("singleton")
public class DocumentParser {

    private static final String REGEX_DOB = "[0-9][0-9]/[0-9][0-9]/[0-9][0-9][0-9][0-9]";
    private static final String REGEX_AADHAR_DETAIL = "[0-9][0-9][0-9][0-9]\\s+[0-9][0-9][0-9][0-9]\\s+[0-9][0-9][0-9][0-9]";
    private static final String REGEX_PAN_DETAIL = "[A-Z][A-Z][A-Z][A-Z][A-Z][0-9][0-9][0-9][0-9][A-Z]";
    private static final String REGEX_VOTER_ID_DETAIL = "[A-Z][A-Z][A-Z][0-9][0-9][0-9][0-9][0-9][0-9][0-9]";
    private static final String REGEX_VOTER_ID_YEAR = "[0-9][0-9][0-9][0-9]";

    private static final String[] FILTER_WORDS_AADHAR = new String[]{"govt", "government", "govern", "nment", "india", "soverne", "sovernen"};
    private static final String[] FILTER_WORDS_PAN = new String[]{"govt", "government", "govern", "nment", "india"
            , "income", "ncome", "incom", "tax", "dept", "depart", "tment", "permanent", "anent", "account", "ccount", "accou"
            , "number", "umber", "numbe"};
    private static final String[] FILTER_WORDS_VOTER_ID = new String[]{"election", "electi", "lectio", "commisio"
            , "ommission", "india", "identity", "dentit", "card"};
    private static final String[] WORDS_GENDER = new String[]{"male", "female", "emale", "femal"};
    private static final String REGEX_AADHAR_UUID = ".*[0-9].*[0-9].*[0-9].*[0-9].*[0-9].*[0-9].*[0-9].*[0-9].*[0-9].*";
    private static final String REGEX_PAN_NUMBER = ".*[a-zA-z][a-zA-z][a-zA-z][a-zA-z].*[0-9].*[0-9].*[0-9].*";
    private static final String REGEX_VOTER_ID = ".*[a-zA-z][a-zA-z].*[0-9].*[0-9][0-9][0-9].*";
    private static final String[] STOP_WORDS_VOTER_ID = new String[]{"name", "elector's", "elector", "sex"
            , "age", "as", "on"};

    private static final String[] TEST_CASE_1 = new String[]{"Searerrear", "SOVERNENTIORNO—", "raita eat", "Rajeev Sharma", "‘SEH aHY0B:1982", "PT Male", "ie", "ae", "ee", "aS", "ie", "6903 2611 1844", "Gqearmrireter niec", "Fes", "UNIQUE IDENTIFICATION AUTHORITY OF INDIA"};
    private static final String[] TEST_CASE_2 = new String[]{"ART SHIT", "S", "© GOVERNMENTIOF INDIA =", "7", "faerer SAR ToT", "Vikash Kumar Rana", "wer TuY0B:1992", "eT Male", "a", "ey", "ee", "8236 7944 9784", "STE - AT saat BT arene"};
    private static final String[] TEST_CASE_3 = new String[]{"La", "oe", "ART Aa", "GOVERNME NT", "NMA", "aTT", "Ayush", "st fa/ DOB: 14/02/1994", "Ter / MALE", "3", "8941 3127 7368", "eee", "POCO", "SHOT ON POCO F1", "i"};
    private static final String[] TEST_CASE_4 = new String[]{"Po", "ae", "ee", "a", "= 2", "_—", "ANd BHT", "Government of India", "*,", "My", "Sie", "aaa Sart", "NETAN PORWAL", "tar", "garg SAN GATT", "Father : Hamendra Kumar Porwa!", "aca fafa / DOB", "12/06/1991", "ges / Male", "t", "is", "3375 6019 5937 &", "4 aX = HA Haat oT", "ah", "i", "\\"};
    private static final String[] TEST_CASE_5 = new String[]{"Re", "Bef", "‘in", ".", "ferees.", "waescowe", "HT AHN", "vt", "—", "eeGévernment of India", "Aaa Garet", "=", "NETAN PORWAL", "foar : seg SAN Iaret", ") Father : Hamendra Kumar Porwal", "aiea fate / DOB : 12/06/1991", "te", "Ges / Male", "ese", "Pre", "3375 6019 5937 _", "Wk", "Bina aaah aI", "Mi:", ":", "y"};
    private static final String[] TEST_CASE_6 = new String[]{"siraax faapt", "anil?", "ANT", "INCOME TAX DEPARTMENT", "FESER", "GOVT vd INDIA", "oo", "CPPPA3743G", "AYUSH", "ANIL", "On", "14/02/1 994", "ak A»", "-", "a", "Why"};
    private static final String[] TEST_CASE_7 = new String[]{"STAHL", "ART UG", "INCOME TAX DEPARTMENT is", "GOVT. OF INDIA", "MANAN GAKHAR", "ARUN KUMAR GAKHAR", "Oy nwa", "16/08/1994", "Permanent Account Number", "oe:", "cs", "BMUPG1121R", "€).", "Mewes", "wet", "Signature", "a &", "1", "o", "oa", ";"};

    public static void main(String[] args) {
        List<DocumentInput> inputList = new ArrayList<DocumentInput>();
        inputList.add(new AadharCardInput(Arrays.asList(TEST_CASE_1)));
        inputList.add(new AadharCardInput((Arrays.asList(TEST_CASE_2))));
        inputList.add(new AadharCardInput((Arrays.asList(TEST_CASE_3))));
        inputList.add(new AadharCardInput((Arrays.asList(TEST_CASE_4))));
        inputList.add(new AadharCardInput((Arrays.asList(TEST_CASE_5))));
        inputList.add(new AadharCardInput((Arrays.asList(TEST_CASE_6))));
        inputList.add(new AadharCardInput((Arrays.asList(TEST_CASE_7))));
        parse(inputList).forEach(document -> System.out.println(document));
    }

    static List<Document> parse(List<DocumentInput> inputList) {
        List<Document> documents = new ArrayList<Document>();
        for (DocumentInput input : inputList) {
            if (input instanceof AadharCardInput) {
                documents.add(parseDocument((AadharCardInput) input));
            } else if (input instanceof PanCardInput) {
                documents.add(parseDocument((PanCardInput) input));
            } else if (input instanceof VoterIdCardInput) {
                documents.add(parseDocument((VoterIdCardInput) input));
            }
        }
        return documents;
    }

    static abstract class Document {

    }

    public static AadharCard parseDocument(AadharCardInput input) {
        AadharCard document = new AadharCard();
        String[] filterWords = FILTER_WORDS_AADHAR;
        boolean nameFound = false;
        boolean fathersNameFound = false;
        boolean dobFound = false;
        boolean genderFound = false;
        boolean uuidFound = false;

        String uuid = extractViaRegex(input.getLines(), REGEX_AADHAR_DETAIL);
        if (null != uuid) {
            document.setUuid(uuid);
            uuidFound = true;
        }

        String dob = extractViaRegex(input.getLines(), REGEX_DOB);
        if (null != dob) {
            document.setDateOfBirth(dob);
            dobFound = true;
        }

        try {
            Pair<String, String> pair = specialSurnameCase(input.getLines());
            if (pair != null) {
                document.setName(pair.getLeft());
                nameFound = true;
                document.setFathersName(pair.getRight());
                fathersNameFound = true;
            }
        } catch (Exception e) {

        }

        for (String eachLine : input.getLines()) {

            String line = eachLine.toLowerCase();
            boolean foundFilterWord = false;

            // filter govt
            for (String word : filterWords) {
                if (line.contains(word.toLowerCase())) {
                    foundFilterWord = true;
                    break;
                }
            }
            if (foundFilterWord)
                continue;

            // extract gender
            if (!genderFound) {
                for (String each : WORDS_GENDER) {
                    if (line.contains(each.toLowerCase())) {
                        genderFound = true;
                        if (line.contains("emale") || line.contains("female")) {
                            document.setGender("female");
                        } else
                            document.setGender("male");
                        break;
                    }
                }
                if (genderFound) {
                    continue;
                }
            }

            // extract name
            if (!nameFound && line.matches(".*[A-Za-z0-9]+.*") && !line.matches(".*[0-9][0-9].*")) {
                if (line.matches(".*[A-Za-z][A-Za-z][A-Za-z][A-Za-z].*") && !foundRLEGreatherThanEqualTo(line, 3)) {
                    nameFound = true;
                    document.setName(line);
                }
                if (nameFound) {
                    continue;
                }
            }

            // extract fathers name
            if (nameFound && !fathersNameFound && line.matches(".*[A-Za-z0-9]+.*") && !line.matches(".*[0-9][0-9].*")) {
                if (line.matches(".*[A-Za-z][A-Za-z][A-Za-z][A-Za-z].*") && !foundRLEGreatherThanEqualTo(line, 3)) {
                    fathersNameFound = true;
                    document.setFathersName(line);
                }
                if (fathersNameFound) {
                    continue;
                }
            }

            // extract DOB
            if (!dobFound && line.matches(".*[0-9][0-9].*") && !line.matches(REGEX_AADHAR_UUID)) {
                dobFound = true;
                document.setDateOfBirth(line);
                continue;
            }

            // extract uuid
            if (!uuidFound && line.matches(REGEX_AADHAR_UUID)) {
                uuidFound = true;
                document.setUuid(line);
            }

        }


        return document;
    }

    public static PanCard parseDocument(PanCardInput input) {
        PanCard document = new PanCard();
        String[] filterWords = FILTER_WORDS_PAN;
        boolean nameFound = false;
        boolean fathersNameFound = false;
        boolean dobFound = false;
        boolean panNumberFound = false;

        String dob = extractViaRegex(input.getLines(), REGEX_DOB);
        if (null != dob) {
            document.setDateOfBirth(dob);
            dobFound = true;
        }

        String panId = extractViaRegex(input.getLines(), REGEX_PAN_DETAIL);
        if (null != panId) {
            document.setPanNumber(panId);
            panNumberFound = true;
        }

        try {
            Pair<String, String> pair = specialSurnameCase(input.getLines());
            if (pair != null) {
                document.setName(pair.getLeft());
                nameFound = true;
                document.setFathersName(pair.getRight());
                fathersNameFound = true;
            }
        } catch (Exception e) {

        }

        for (String eachLine : input.getLines()) {

            String line = eachLine.toLowerCase();
            boolean foundFilterWord = false;

            // filter govt
            for (String word : filterWords) {
                if (line.contains(word.toLowerCase()))
                    foundFilterWord = true;
                break;
            }
            if (foundFilterWord)
                continue;

            // extract name
            if (!nameFound && line.matches(".*[A-Za-z0-9]+.*") && !line.matches(".*[0-9][0-9].*")) {
                if (line.matches(".*[A-Za-z][A-Za-z][A-Za-z][A-Za-z].*") && !foundRLEGreatherThanEqualTo(line, 3)) {
                    nameFound = true;
                    document.setName(line);
                }
                if (nameFound) {
                    continue;
                }
            }

            // extract fathers name
            if (nameFound && !fathersNameFound && line.matches(".*[A-Za-z0-9]+.*") && !line.matches(".*[0-9][0-9].*")) {
                if (line.matches(".*[A-Za-z][A-Za-z][A-Za-z][A-Za-z].*") && !foundRLEGreatherThanEqualTo(line, 3)) {
                    fathersNameFound = true;
                    document.setFathersName(line);
                }
                if (fathersNameFound) {
                    continue;
                }
            }

            // extract DOB
            if (!dobFound && line.matches(".*[0-9][0-9].*") && !line.matches(REGEX_PAN_NUMBER)) {
                dobFound = true;
                document.setDateOfBirth(line);
                continue;
            }

            // extract pan
            if (!panNumberFound && line.matches(REGEX_PAN_NUMBER)) {
                panNumberFound = true;
                document.setPanNumber(line);
            }
        }
        return document;
    }

    public static VoterIdCard parseDocument(VoterIdCardInput input) {
        VoterIdCard document = new VoterIdCard();
        boolean nameFound = false;
        boolean fathersNameFound = false;
        boolean dobFound = false;
        boolean genderFound = false;
        boolean voterIdFound = false;
        String[] filterWords = FILTER_WORDS_VOTER_ID;

        String dob = getYear(input.getLines());
        if (null != dob) {
            document.setDateOfBirth(dob);
            dobFound = true;
        }

        String id = extractViaRegex(input.getLines(), REGEX_VOTER_ID_DETAIL);
        if (null != id) {
            document.setVoterId(id);
            voterIdFound = true;
        }

        try {
            Pair<String, String> pair = specialSurnameCase(input.getLines());
            if (pair != null) {
                document.setName(pair.getLeft());
                nameFound = true;
                document.setFathersName(pair.getRight());
                fathersNameFound = true;
            }
        } catch (Exception e) {

        }

        for (String eachLine : input.getLines()) {

            String line = eachLine.toLowerCase();
            boolean foundFilterWord = false;

            // filter govt
            for (String word : filterWords) {
                if (line.contains(word.toLowerCase()))
                    foundFilterWord = true;
                break;
            }
            if (foundFilterWord)
                continue;

            // extract gender
            if (!genderFound) {
                for (String each : WORDS_GENDER) {
                    if (line.contains(each.toLowerCase())) {
                        if (line.contains("emale") || line.contains("female")) {
                            document.setGender("female");
                        } else {
                            document.setGender("male");
                        }
                        genderFound = true;
                        break;
                    }
                }
                if (genderFound) {
                    continue;
                }
            }

            // extract name
            if (!nameFound && line.matches(".*[A-Za-z0-9]+.*") && !line.matches(".*[0-9][0-9].*")) {
                List<String> stopWordRemovedList = new ArrayList<String>();
                String[] words = line.split("\\W");
                for (String word : STOP_WORDS_VOTER_ID) {
                    for (String eachWord : words) {
                        if (!eachWord.equalsIgnoreCase(word)) {
                            stopWordRemovedList.add(eachLine);
                        }
                    }
                }
                String cleanLine = StringUtils.join(stopWordRemovedList, " ");
                if (cleanLine.matches(".*[A-Za-z][A-Za-z][A-Za-z][A-Za-z].*") && !foundRLEGreatherThanEqualTo(cleanLine, 3)) {
                    nameFound = true;
                    document.setName(cleanLine);
                }
                if (nameFound) {
                    continue;
                }
            }

            // extract fathers name
            if (nameFound && !fathersNameFound && line.matches(".*[A-Za-z0-9]+.*") && !line.matches(".*[0-9][0-9].*")) {
                List<String> stopWordRemovedList = new ArrayList<String>();
                String[] words = line.split("\\W");
                for (String word : STOP_WORDS_VOTER_ID) {
                    for (String eachWord : words) {
                        if (!eachWord.equalsIgnoreCase(word)) {
                            stopWordRemovedList.add(eachLine);
                        }
                    }
                }
                String cleanLine = StringUtils.join(stopWordRemovedList, " ");
                if (cleanLine.matches(".*[A-Za-z][A-Za-z][A-Za-z][A-Za-z].*") && !foundRLEGreatherThanEqualTo(cleanLine, 3)) {
                    fathersNameFound = true;
                    document.setFathersName(cleanLine);
                }
                if (fathersNameFound) {
                    continue;
                }
            }

            // extract DOB
            if (!dobFound && line.matches(".*[0-9][0-9].*") && !line.matches(REGEX_AADHAR_UUID)) {
                dobFound = true;
                document.setDateOfBirth(line);
                continue;
            }

            // extract uuid
            if (!voterIdFound && line.matches(REGEX_VOTER_ID)) {
                voterIdFound = true;
                document.setVoterId(line);
            }
        }


        return document;
    }

    private static String getYear(List<String> lines) {
        return null;
    }

    public static class AadharCard extends Document {
        String name;
        String fathersName;
        String dateOfBirth;
        String uuid;
        String gender;

        public String getName() {
            return name;
        }

        public String getFathersName() {
            return fathersName;
        }

        public String getDateOfBirth() {
            return dateOfBirth;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public void setDateOfBirth(String dateOfBirth) {
            this.dateOfBirth = convertDateOfBirth(dateOfBirth);
        }

        public void setName(String name) {
            this.name = nameSetting(name);
        }

        public void setFathersName(String fathersName) {
            this.fathersName = fathersNameSetting(fathersName);
        }
    }

    public static class PanCard extends Document {
        String name;
        String fathersName;
        String dateOfBirth;
        String panNumber;

        public String getName() {
            return name;
        }

        public String getFathersName() {
            return fathersName;
        }

        public String getDateOfBirth() {
            return dateOfBirth;
        }

        public String getPanNumber() {
            return panNumber;
        }

        public void setPanNumber(String panNumber) {
            this.panNumber = panNumber;
        }

        public void setDateOfBirth(String dateOfBirth) {
            this.dateOfBirth = convertDateOfBirth(dateOfBirth);
        }

        public void setName(String name) {
            this.name = nameSetting(name);
        }

        public void setFathersName(String fathersName) {
            this.fathersName = fathersNameSetting(fathersName);
        }
    }

    public static class VoterIdCard extends Document {
        String voterId;
        String name;
        String fathersName;
        String dateOfBirth;
        String gender;

        public String getVoterId() {
            return voterId;
        }

        public void setVoterId(String voterId) {
            this.voterId = voterId;
        }

        public String getName() {
            return name;
        }

        public String getFathersName() {
            return fathersName;
        }

        public String getDateOfBirth() {
            return dateOfBirth;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public void setDateOfBirth(String dateOfBirth) {
            this.dateOfBirth = convertDateOfBirth(dateOfBirth);
        }

        public void setName(String name) {
            this.name = nameSetting(name);
        }

        public void setFathersName(String fathersName) {
            this.fathersName = fathersNameSetting(fathersName);
        }

    }

    private static String fathersNameSetting(String fathersName) {
        return fathersName
                .replace("father", "")
                .replace("\\P{L}", " ")
                .replace(":", " ")
                .replace(")", " ")
                .replace("\\s+", "\\s")
                .trim();
    }

    static private String nameSetting(String name) {
        return name;
    }

    private static String convertDateOfBirth(String dateOfBirth) {
        String regexBig = ".*[0-9][0-9].*[0-9].*[0-9][0-9][0-9].*";
        String regexSmall = ".*[0-9][0-9][0-9][0-9].*";
        String pattern = "[0-9][0-9][0-9][0-9]";
        if (!dateOfBirth.matches(regexBig) && dateOfBirth.matches(regexSmall)) {
            Pattern p = Pattern.compile(pattern);
            Matcher matcher = p.matcher(dateOfBirth);
            if (matcher.find()) {
                return matcher.group(0);
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (Character character : dateOfBirth.toCharArray()) {
            if (character == 32)
                continue;
            stringBuilder.append(character);
        }
        return stringBuilder.toString();
    }

    static abstract class DocumentInput {

    }

    public static class AadharCardInput extends DocumentInput {
        List<String> lines;

        public AadharCardInput(List<String> lines) {
            this.lines = lines;
        }

        public void setLines(List<String> lines) {
            this.lines = lines;
        }

        public List<String> getLines() {
            List<String> filteredLines = filter(lines);
            return filteredLines;
        }
    }

    public static class PanCardInput extends DocumentInput {
        List<String> lines;

        public PanCardInput(List<String> lines) {
            this.lines = lines;
        }

        public void setLines(List<String> lines) {
            this.lines = lines;
        }

        public List<String> getLines() {
            List<String> filteredLines = filter(lines);
            return filteredLines;
        }
    }

    public static class VoterIdCardInput extends DocumentInput {
        List<String> lines;

        public VoterIdCardInput(List<String> lines) {
            this.lines = lines;
        }

        public void setLines(List<String> lines) {
            this.lines = lines;
        }

        public List<String> getLines() {
            List<String> filteredLines = filter(lines);
            return filteredLines;
        }
    }

    private static List<String> filter(List<String> strings) {
        List<String> filtered = new ArrayList<String>();
        int num = -1;
        for (int index = strings.size() - 1; index >= 0; index--) {
            for (String each : FILTER_WORDS_AADHAR) {
                if (strings.get(index).toLowerCase().contains(each.toLowerCase())) {
                    num = index;
                    break;
                }
            }
            if (num != -1) {
                break;
            }
        }
        if (num > strings.size() / 2) {
            return strings;
        }
        for (int index = num + 1; index < strings.size(); index++) {
            filtered.add(strings.get(index));
        }
        return filtered;
    }

    private static String extractViaRegex(List<String> lines, String regex) {
        Pattern p = Pattern.compile(regex);
        for (String line : lines) {
            Matcher matcher = p.matcher(line.toLowerCase());
            if (matcher.find()) {
                return matcher.group(0);
            }
        }
        return null;
    }

    static boolean foundRLEGreatherThanEqualTo(String str, int size) {
        int n = str.length();
        for (int i = 0; i < n; i++) {

            // Count occurrences of current character 
            int count = 1;
            while (i < n - 1 &&
                    str.charAt(i) == str.charAt(i + 1)) {
                count++;
                i++;
            }

            // Print character and its count 
//            System.out.print(str.charAt(i));
//            System.out.print(count);
            if (count >= size && String.valueOf(str.charAt(i)).matches("[A-Za-z]")) {
                return true;
            }
        }
        return false;
    }

    static Pair specialSurnameCase(List<String> lines) {
        String surname = null;
        List<String> potentialSurnames = new ArrayList<>();
        for (String eachLine : lines) {
            String line = eachLine.toLowerCase();
            if (line.length() < 4)
                continue;
            String[] words = line.split("\\P{L}");
            int count = 0;
            for (String word : words) {
                if (word.length() >= 3)
                    count++;
            }
            if (count < 2)
                continue;
            String word = words[words.length - 1];
            if (potentialSurnames.contains(word)) {
                surname = word;
                break;
            }
            potentialSurnames.add(word);
        }

        List<String> names = new ArrayList<>();
        for (String eachLine : lines) {
            String line = eachLine.toLowerCase();
            if (line.contains(surname)) {
                names.add(line);
            }
        }
        if (names.size() == 2) {
            return new ImmutablePair(names.get(0), names.get(1));
        }
        return null;
    }

}
