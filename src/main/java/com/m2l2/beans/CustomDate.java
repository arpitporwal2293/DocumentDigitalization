package com.m2l2.beans;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Objects;

public class CustomDate {

    private Integer date;
    private Integer month;
    private Integer year;

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomDate that = (CustomDate) o;
        return Objects.equals(date, that.date) &&
                Objects.equals(month, that.month) &&
                Objects.equals(year, that.year);
    }

    @Override
    public int hashCode() {

        return Objects.hash(date, month, year);
    }

    @Override
    public String toString() {
        return "CustomDate{" +
                "date=" + date +
                ", month=" + month +
                ", year=" + year +
                '}';
    }
}
