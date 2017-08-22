package com.example.user.mobileproject;

import android.telecom.Call;

import com.google.gson.JsonObject;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by user on 2016-05-29.
 */
public interface Server {
    static final String serverIP = "http://203.252.166.226:8080";
    ////////////////////////////////////지역 함수/////////////////////////////////////
    @GET("/all.php")
    public void serachAll(@Query("region") String u_id, Callback<JsonObject> result);

    @GET("/normalboardlist_write.php")
    public void normal_board_write(@Query("u_id") int u_id, @Query("u_name") String u_name, @Query("u_title") String u_title,@Query("u_text") String u_text,@Query("u_date") String u_date, Callback<JsonObject> result);

    @GET("/normalboardlist_show.php")
    public void normal_board_show(@Query("u_id") int u_id,Callback<JsonObject> callback);

    @GET("/comment_show.php")
    public void comment_show(@Query("u_num") String num, Callback<JsonObject> callback);

    @GET("/comment_write.php")
    public void comment_write(@Query("u_id") int u_id, @Query("u_name") String u_name, @Query("u_text") String u_text,@Query("u_num") String num, Callback<JsonObject> callback);

    @GET("/secretboard_show.php")
    public void secretboard_show(@Query("u_id") int u_id, Callback<JsonObject> callback);

    @GET("/secretboard_write.php")
    public void secretboard_write(@Query("s_id") int s_id, @Query("s_nickname") String s_nickname, @Query("r_nickname") String r_nickname, @Query("u_text") String text, Callback<JsonObject> callback);

    @GET("/board_search.php")
    public void board_search(@Query("key") String key, Callback<JsonObject> callback);
}
