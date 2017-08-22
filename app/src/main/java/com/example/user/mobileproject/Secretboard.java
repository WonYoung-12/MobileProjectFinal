package com.example.user.mobileproject;

/**
 * Created by kwy2868 on 2016-06-20.
 */
public class Secretboard {
    String nickname1;
    String nickname2;
    int id1;
    int id2;
    String text;

    public Secretboard(int id1, int id2, String nickname1, String nickname2, String text) {
        this.id1 = id1;
        this.id2 = id2;
        this.nickname1 = nickname1;
        this.nickname2 = nickname2;
        this.text = text;
    }
}
