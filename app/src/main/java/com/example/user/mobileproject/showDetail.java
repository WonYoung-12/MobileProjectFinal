package com.example.user.mobileproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by kwy2868 on 2016-06-19.
 */
public class showDetail extends AppCompatActivity {
    TextView title;
    TextView text;
    TextView nickname;
    ListView listview;
    GlobalApplication app;
    //Normalboard item;
    Button button; // 댓글 작성 버튼
    AnroidToServer ad;
    String num;

    TextView comment;
    ArrayList<com.example.user.mobileproject.Comment> commentlist;
    CommentAdapter commentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_detail);
        init();
    }

    void init(){
        app = (GlobalApplication)getApplicationContext();
        ad = new AnroidToServer();
        title = (TextView)findViewById(R.id.d_title); // 글 제목
        text = (TextView)findViewById(R.id.d_text); // 글 내용
        nickname = (TextView)findViewById(R.id.nickname);

        listview = (ListView)findViewById(R.id.commentlist);
        commentlist = new ArrayList<com.example.user.mobileproject.Comment>();
        commentAdapter = new CommentAdapter(this, commentlist);
        listview.setAdapter(commentAdapter);
        comment = (TextView)findViewById(R.id.comment);

        Intent intent = getIntent();
        //item=new Normalboard(intent.getStringExtra("id"), intent.getStringExtra("title"), intent.getStringExtra("nickname"), intent.getStringExtra("text"), intent.getStringExtra("num"));
        num = intent.getStringExtra("num"); // 게시글 번호
        title.setText(intent.getStringExtra("title"));
        text.setText(intent.getStringExtra("text"));
        nickname.setText(intent.getStringExtra("nickname"));

        button = (Button)findViewById(R.id.writeComment);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String m_nickname = nickname.getText().toString();
                String m_text = comment.getText().toString();
                com.example.user.mobileproject.Comment comment = new com.example.user.mobileproject.Comment(m_nickname, m_text, num);
                ad.comment_write((int)app.user.main_user.getId(), m_nickname, m_text,  num, new Callback<JsonObject>() {
                    @Override
                    public void success(JsonObject jsonObject, Response response) {
                        Log.d("test", response.toString());
                        Toast.makeText(getApplicationContext(), "댓글이 성공적으로 작성되었습니다.", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d("test", error.toString());
                    }
                });
                commentlist.add(comment);
                commentAdapter.notifyDataSetChanged();
                //Toast.makeText(getApplicationContext(), "댓글이 성공적으로 작성되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        ad.comment_show(num, new Callback<JsonObject>() {
            @Override
            public void success(JsonObject jsonObject, Response response) {
                Log.d("test",jsonObject.toString());
                if (jsonObject.get("status").getAsString().equals("OK")) {
                    JsonArray result = jsonObject.getAsJsonArray("result");
                    for (int i = 0; i < result.size(); i++) {

                        JsonObject n1 = (JsonObject) result.get(i);
                        int id = n1.get("id").getAsInt();
                        String nickname = n1.get("nickname").getAsString();
                        String text = n1.get("text").getAsString();
                        String num = n1.get("num").getAsString();

                        Comment g1 = new Comment(String.valueOf(id), nickname, text, num);
                        commentlist.add(g1);
                    }
                    commentAdapter.notifyDataSetChanged();

                } else {
                    return;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("test",error.toString());
            }
        });
    }
}
