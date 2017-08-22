package com.example.user.mobileproject;

/**
 * Created by kwy2868 on 2016-06-21.
 */
public class Searchlist {
    String title;
    String text;
    String name;

    public Searchlist(String title, String text, String name) {
        this.title = title;
        this.text = text;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
