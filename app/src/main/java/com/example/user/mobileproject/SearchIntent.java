package com.example.user.mobileproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by kwy2868 on 2016-06-21.
 */
public class SearchIntent extends AppCompatActivity {
    GlobalApplication app;
    AnroidToServer ad;
    EditText editText;
    Button button;
    ListView listView;
    ArrayList<Searchlist> list;
    SearchlistAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.board_search);
        init();
    }

    void init(){
        app = GlobalApplication.getGlobalApplicationContext();
        ad = new AnroidToServer();
        editText = (EditText)findViewById(R.id.search);
        button = (Button)findViewById(R.id.searchbtn);
        listView = (ListView)findViewById(R.id.searchlist);
        list = new ArrayList<Searchlist>();
        adapter = new SearchlistAdapter(this, list);
        listView.setAdapter(adapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.clear();
                adapter.notifyDataSetChanged();
                final String key = editText.getText().toString();
                final ArrayList<String> numlist = new ArrayList<String>();
                //String temp = key;
                ad.board_search(key, new Callback<JsonObject>() {
                    @Override
                    public void success(JsonObject jsonObject, Response response) {

                        if (jsonObject.get("status").getAsString().equals("OK")) {

                            JsonArray result = jsonObject.getAsJsonArray("result");
                            for (int i = 0; i < result.size(); i++) {

                                JsonObject n1 = (JsonObject) result.get(i);
                                String title = n1.get("title").getAsString();
                                String text = n1.get("text").getAsString();
                                String nickname = n1.get("nickname").getAsString();
                                String num = n1.get("num").getAsString();

                                Searchlist searchlist = new Searchlist(title, text, nickname);
                                numlist.add(num);
                                list.add(searchlist);
                            }
                            adapter.notifyDataSetChanged();
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Searchlist items = (Searchlist) adapter.getItem(position);
                                    Intent intent = new Intent(getApplicationContext(), showDetail.class);
                                    intent.putExtra("num", numlist.get(position));
                                    intent.putExtra("title", items.getTitle());
                                    intent.putExtra("text", items.getText());
                                    intent.putExtra("nickname", app.user.main_user.getNickname());
                                    startActivity(intent);
                                }
                            });
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });
            }

        });
    }
}