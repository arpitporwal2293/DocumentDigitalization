package com.m2l2.Parsers;

import com.m2l2.beans.CustomDate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class DateParser {

    private final String MONTH_TEXT_PATTERN = ".*[a-zA-Z].*[a-zA-Z].*";
    private Pattern pattern;
    private Matcher matcher;

    public CustomDate parse(String dob){
        if(!StringUtils.isEmpty(dob)) {
            pattern = Pattern.compile(MONTH_TEXT_PATTERN);

            CustomDate customDate = new CustomDate();
            String[] dates = dob.split("\\W");

            if (dates.length == 3) {
                matcher = pattern.matcher(dates[1]);
                customDate.setDate(Integer.parseInt(dates[0]));
                if (matcher.matches()) {
                    customDate.setMonth(parseCharMonth(dates[1]));
                } else {
                    customDate.setMonth(Integer.parseInt(dates[1]));
                }
                customDate.setYear(Integer.parseInt(dates[2]));
            } else if (dates.length == 2) {
                matcher = pattern.matcher(dates[0]);
                if (matcher.matches()) {
                    customDate.setMonth(parseCharMonth(dates[0]));
                } else {
                    customDate.setMonth(Integer.parseInt(dates[0]));
                }
                customDate.setYear(Integer.parseInt(dates[1]));
            } else if (dates.length == 1) {
                customDate.setYear(Integer.parseInt(dates[0]));
            }

            return customDate;
        }
        return null;
    }

    public Integer parseCharMonth(String month){
        DateTimeFormatter parser = DateTimeFormatter.ofPattern("MMM").withLocale(Locale.ENGLISH);
        TemporalAccessor accessor = parser.parse(month);
        return accessor.get(ChronoField.MONTH_OF_YEAR);
    }

}
