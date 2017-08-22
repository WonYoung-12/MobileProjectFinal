package com.example.user.mobileproject;

/**
 * Created by kwy2868 on 2016-06-17.
 */
public class Normalboard {
    String id;
    String num;
    String title;
    String name;
    String text;
    String date;

    public Normalboard(String id,String title, String name, String text, String date) {
        this.id = id;
        this.title = title;
        this.name = name;
        this.text = text;
        this.date = date;
    }

    public Normalboard(String id, String name, String title, String text, String date, String num) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.text = text;
        this.date = date;
        this.num = num;
    }

    public String getNum(){
        return num;
    }

    public void setNum(String num){
        this.num = num;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText(){
        return text;
    }

    public void setText(){
        this.text = text;
    }

    Normalboard(){}

}
