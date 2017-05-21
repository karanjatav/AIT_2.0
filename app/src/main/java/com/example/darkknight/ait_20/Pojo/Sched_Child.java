package com.example.darkknight.ait_20.Pojo;

/**
 * Created by DARKKNIGHT on 5/21/2017.
 */

public class Sched_Child {

    private String Date,Day,Subject_Code,Subject;
    private int Image;

    public Sched_Child(String date, String day, String subject_Code, String subject, int image) {
        Date = date;
        Day = day;
        Subject_Code = subject_Code;
        Subject = subject;
        Image = image;
    }

    public Sched_Child() {
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getDay() {
        return Day;
    }

    public void setDay(String day) {
        Day = day;
    }

    public String getSubject_Code() {
        return Subject_Code;
    }

    public void setSubject_Code(String subject_Code) {
        Subject_Code = subject_Code;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        Image = image;
    }
}
