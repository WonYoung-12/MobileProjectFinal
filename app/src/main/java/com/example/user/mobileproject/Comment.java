package com.example.user.mobileproject;

/**
 * Created by kwy2868 on 2016-06-19.
 */
public class Comment {
    String id;
    String num;
    String nickname;
    String text;

    Comment(){

    }

    Comment(String nickname, String text){
        this.nickname = nickname;
        this.text = text;
    }

    Comment(String nickname, String text, String num){
        this.num = num;
        this.nickname = nickname;
        this.text = text;
    }

    Comment(String id, String nickname, String text, String num){
        this.id = id;
        this.nickname = nickname;
        this.text = text;
        this.num = num;
    }

    public String getNum() {
        return num;
    }

    public void setId(String num) {
        this.num = num;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
