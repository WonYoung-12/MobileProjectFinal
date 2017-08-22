package com.example.user.mobileproject;

import com.google.gson.JsonObject;

import retrofit.Callback;
import retrofit.RestAdapter;

/**
 * Created by user on 2016-05-29.
 */

public class AnroidToServer {

    public AnroidToServer(){};

    public void serachAll(final String u_id, final Callback<JsonObject> callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(Server.serverIP) // call
                            // your
                            // base
                            // url
                            .build();
                    Server retrofit = restAdapter.create(Server.class);
                    retrofit.serachAll(u_id, callback);
                } catch (Throwable ex) {
                }
            }
        }).start();
    }

    public void normal_board_write(final int u_id, final String u_name, final String u_title,final String u_text, final String u_date,final Callback<JsonObject> callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(Server.serverIP) // call
                            // your
                            // base
                            // url
                            .build();
                    Server retrofit = restAdapter.create(Server.class);
                    retrofit.normal_board_write(u_id, u_name, u_title, u_text, u_date, callback);
                } catch (Throwable ex) {
                }
            }
        }).start();
    }
    public void normal_board_show(final int u_id,final Callback<JsonObject> callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(Server.serverIP) // call
                            // your
                            // base
                            // url
                            .build();
                    Server retrofit = restAdapter.create(Server.class);
                    retrofit.normal_board_show(u_id, callback);
                } catch (Throwable ex) {
                }
            }
        }).start();
    }

    public void comment_show(final String num, final Callback<JsonObject> callback){
        new Thread(new Runnable(){
            @Override
            public void run() {
                try{
                    RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(Server.serverIP) // call
                            // your
                            // base
                            // url
                            .build();
                    Server retrofit = restAdapter.create(Server.class);
                    retrofit.comment_show(num, callback);
                } catch (Throwable ex) {
                }
            }
        }).start();
    }

    public void comment_write(final int u_id, final String u_name, final String u_text, final String u_num, final Callback<JsonObject> callback){
        new Thread(new Runnable(){
            @Override
            public void run() {
                try{
                    RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(Server.serverIP) // call
                            // your
                            // base
                            // url
                            .build();
                    Server retrofit = restAdapter.create(Server.class);
                    retrofit.comment_write(u_id, u_name, u_text, u_num, callback);
                } catch (Throwable ex) {
                }
            }
        }).start();
    }

    public void secretboard_show(final int u_id, final Callback<JsonObject> callback){
        new Thread(new Runnable(){
            @Override
            public void run() {
                try{
                    RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(Server.serverIP) // call
                            // your
                            // base
                            // url
                            .build();
                    Server retrofit = restAdapter.create(Server.class);
                    retrofit.secretboard_show(u_id, callback);
                } catch (Throwable ex) {
                }
            }
        }).start();
    }

    public void board_search(final String key, final Callback<JsonObject> callback){
        new Thread(new Runnable(){
            @Override
            public void run() {
                try{
                    RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(Server.serverIP) // call
                            // your
                            // base
                            // url
                            .build();
                    Server retrofit = restAdapter.create(Server.class);
                    retrofit.board_search(key, callback);
                } catch (Throwable ex) {
                }
            }
        }).start();
    }

    public void secretboard_write(final int s_id,final String s_nickname,final String r_nickname,final String u_text, final Callback<JsonObject> callback){
        new Thread(new Runnable(){
            @Override
            public void run() {
                try{
                    RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(Server.serverIP) // call
                            // your
                            // base
                            // url
                            .build();
                    Server retrofit = restAdapter.create(Server.class);
                    retrofit.secretboard_write(s_id,s_nickname,r_nickname,u_text, callback);
                } catch (Throwable ex) {
                }
            }
        }).start();
    }
}
