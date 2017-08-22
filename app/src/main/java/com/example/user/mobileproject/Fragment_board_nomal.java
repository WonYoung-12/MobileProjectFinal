package com.example.user.mobileproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by user on 2016-05-29.
 */
public class Fragment_board_nomal extends Fragment {

    AnroidToServer ad;
    boolean flag = true;
    String data;
    ListView listView;
    NormalAdapter normalAdapter;
    ArrayList<Normalboard> normallist;

    ImageButton add_btn;
    ImageButton show_btn;
    ImageButton search_btn;
    RelativeLayout showLayout;
    RelativeLayout writeLayout;
    GlobalApplication app;

    //게시글 작성할 때 필요한 것들
    Button write_btn;
    EditText title;
    EditText text;

    public Fragment_board_nomal() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_board_nomal, container, false);
        app = (GlobalApplication)getActivity().getApplicationContext();

        ad = new AnroidToServer();
        showLayout = (RelativeLayout)view.findViewById(R.id.show);
        writeLayout = (RelativeLayout)view.findViewById(R.id.write);

        writeLayout.setVisibility(View.INVISIBLE);
        showLayout.setVisibility(View.VISIBLE);

        listView = (ListView) view.findViewById(R.id.listview);
        add_btn = (ImageButton)view.findViewById(R.id.addButton);
        show_btn = (ImageButton)view.findViewById(R.id.showButton);
        write_btn = (Button)view.findViewById(R.id.writeButton);
        search_btn = (ImageButton)view.findViewById(R.id.searchButton);
        title = (EditText)view.findViewById(R.id.title);
        text = (EditText)view.findViewById(R.id.text);

        normallist = new ArrayList<Normalboard>();
        normalAdapter = new NormalAdapter(getContext(), normallist);
        listView.setAdapter(normalAdapter);

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SearchIntent.class);
                startActivity(intent);
            }
        });

        ad.normal_board_show(1, new Callback<JsonObject>() {
            @Override
            public void success(JsonObject jsonObject, Response response) {
                Log.d("test", response.toString());

                if (jsonObject.get("status").getAsString().equals("OK")) {

                    JsonArray result = jsonObject.getAsJsonArray("result");
                    for (int i = 0; i < result.size(); i++) {

                        JsonObject n1 = (JsonObject) result.get(i);
                        int id = n1.get("id").getAsInt();
                        String nickname = n1.get("nickname").getAsString();
                        String title = n1.get("title").getAsString();
                        String text = n1.get("text").getAsString();
                        String date = n1.get("data").getAsString();
                        String num = n1.get("num").getAsString();

                        Normalboard g1 = new Normalboard(String.valueOf(id), nickname, title, text, date, num);
                        normallist.add(g1);
                    }

                    normalAdapter.notifyDataSetChanged();

                } else {
                    return;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("test", error.toString());

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Normalboard item = (Normalboard) normalAdapter.getItem(position);

                Intent intent = new Intent(getContext(), showDetail.class);
                intent.putExtra("num", item.getNum()); // 게시글 번호를 넘겨준다.
                intent.putExtra("title", item.getTitle());
                intent.putExtra("text", item.getText());
                intent.putExtra("nickname", app.user.main_user.getNickname());

                startActivity(intent);
            }


        });

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 게시판 보여주는 화면일 때
                if (flag == false) {
                    flag = true;
                    writeLayout.setVisibility(View.INVISIBLE);
                    showLayout.setVisibility(View.VISIBLE);
                } else {
                    flag = false;
                    writeLayout.setVisibility(View.VISIBLE);
                    showLayout.setVisibility(View.INVISIBLE);
                }
            }
        });

        show_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag == false) {
                    flag = true;
                    writeLayout.setVisibility(View.INVISIBLE);
                    showLayout.setVisibility(View.VISIBLE);
                } else {
                    flag = false;
                    writeLayout.setVisibility(View.VISIBLE);
                    showLayout.setVisibility(View.INVISIBLE);
                }
            }
        });

        write_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String m_title = title.getText().toString();
                String m_text = text.getText().toString();
                data = new String("yyyy.MM.dd");
                SimpleDateFormat sdf = new SimpleDateFormat(data, Locale.KOREA);
                Normalboard normalboard = new Normalboard(String.valueOf(app.user.main_user.getId()),m_title, app.user.main_user.getNickname(), m_text, sdf.format(new Date()));

                ad.normal_board_write((int)app.user.main_user.getId(), app.user.main_user.getNickname(), m_title, m_text, sdf.format(new Date()), new Callback<JsonObject>() {
                    @Override
                    public void success(JsonObject jsonObject, Response response) {
                        Log.d("test", response.toString());
                        Toast.makeText(getContext(), "게시글이 성공적으로 작성되었습니다.", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d("test", error.toString());
                    }
                });

                normallist.add(normalboard);
                normalAdapter.notifyDataSetChanged();
                Toast.makeText(getContext(), "게시글이 성공적으로 작성되었습니다", Toast.LENGTH_SHORT).show();
                flag = true;
                writeLayout.setVisibility(View.INVISIBLE);
                showLayout.setVisibility(View.VISIBLE);
            }
        });

        ///////////////////////////Back Button/////////////////////////////
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (flag == false) {

                        flag = true;
                        writeLayout.setVisibility(View.INVISIBLE);
                        showLayout.setVisibility(View.VISIBLE);

                    } else {
                        return false;
                    }
                    return true;
                } else {

                    return false;
                }
            }
        });
        return view;
    }
}
