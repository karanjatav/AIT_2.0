package com.example.darkknight.ait_20.Pojo;

/**
 * Created by DARKKNIGHT on 5/19/2017.
 */

public class Newsfeed_Object {
    String subject,description,type,link;

    public Newsfeed_Object(String subject, String description, String type, String link) {
        this.subject = subject;
        this.description = description;
        this.type = type;
        this.link = link;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
