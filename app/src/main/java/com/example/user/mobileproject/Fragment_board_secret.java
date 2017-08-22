package com.example.user.mobileproject;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by user on 2016-05-29.
 */
public class Fragment_board_secret extends Fragment{
    private ChatArrayAdapter chatArrayAdapter;
    private ListView listView;
    public List<ChatMessage> chatMessageList = new ArrayList<ChatMessage>();

    private EditText chatText;

    private Button buttonSend;
    AnroidToServer ad;
    GlobalApplication app;
    ArrayList<Secretboard> sc_arraylist = new ArrayList<>();
    Spinner spi;
    ArrayAdapter<String> spi_adapter;
    ArrayList<String> sc_name = new ArrayList<>();
    int main_position = 0;

    private boolean side = true;

    public Fragment_board_secret(){};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_board_secret, container , false);

        ad = new AnroidToServer();
        app = (GlobalApplication)getActivity().getApplicationContext();

        buttonSend = (Button)view.findViewById(R.id.buttonSend);

        spi = (Spinner)view.findViewById(R.id.spi);

        spi_adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, sc_name);

        spi.setAdapter(spi_adapter);

        if(!app.user.main_user.getNickname().equals("박영수"))
        {
            spi.setVisibility(View.GONE);
        }

        listView = (ListView)view.findViewById(R.id.listView1);

        chatArrayAdapter = new ChatArrayAdapter(getActivity().getApplicationContext(), R.layout.activity_chat_singlemessage);
        listView.setAdapter(chatArrayAdapter);

        chatText = (EditText)view.findViewById(R.id.chatText);

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                sendChatMessage();
            }
        });

        listView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        listView.setAdapter(chatArrayAdapter);

        //to scroll the list view to bottom on data change
        chatArrayAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                listView.setSelection(chatArrayAdapter.getCount() - 1);
            }
        });

        ad.secretboard_show((int) app.user.main_user.getId(), new Callback<JsonObject>() {
            @Override
            public void success(JsonObject jsonObject, Response response) {
                Log.d("test", response.toString());
                if (jsonObject.get("status").getAsString().equals("OK")) {
                    JsonArray result = jsonObject.getAsJsonArray("result");
                    for (int i = 0; i < result.size(); i++) {

                        JsonObject n1 = (JsonObject) result.get(i);
                        int s_id = n1.get("s_id").getAsInt();
                        int r_id = n1.get("r_id").getAsInt();
                        String s_nickname = n1.get("s_nickname").getAsString();
                        String r_nickname = n1.get("r_nickname").getAsString();
                        String text = n1.get("text").getAsString();

                        Secretboard sc = new Secretboard(s_id, r_id, s_nickname, r_nickname, text);

                        sc_arraylist.add(sc);
                    }

                    if (app.user.main_user.getNickname().equals("박영수")) {
                        for (int i = 0; i < sc_arraylist.size(); i++) {
                            sc_name.add(sc_arraylist.get(i).nickname1);
                        }

                        Log.d("Test", sc_name.toString());

                        HashSet hs = new HashSet(sc_name); //중복제거

                        ArrayList<String> newArrayList = new ArrayList<String>();

                        Iterator it = hs.iterator();
                        while (it.hasNext()) {
                            newArrayList.add((String) it.next());
                        }

                        sc_name.clear();

                        for (int i = 0; i < newArrayList.size(); i++) {
                            if (!newArrayList.get(i).equals("박영수"))
                                sc_name.add(newArrayList.get(i));
                        }

                        spi_adapter.notifyDataSetChanged();

                    } else {
                        for (int i = 0; i < sc_arraylist.size(); i++) {
                            if (app.user.main_user.getNickname().equals(sc_arraylist.get(i).nickname1)) {
                                side = false;
                                chatText.setText(sc_arraylist.get(i).text);
                                sendChatMessage();
                            } else if (sc_arraylist.get(i).nickname1.equals("박영수") && sc_arraylist.get(i).nickname2.equals(app.user.main_user.getNickname())) {
                                side = true;
                                chatText.setText(sc_arraylist.get(i).text);
                                sendChatMessage();
                            }
                        }
                        chatArrayAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("test", error.toString());
            }
        });

        chatText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    return sendChatMessage();
                }
                return false;
            }
        });

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                final AnroidToServer ad = new AnroidToServer();
                if (app.user.main_user.getNickname().equals("박영수")) {
                    if(sc_name.size() == 0)
                    {
                        return;
                    }
                    ad.secretboard_write((int) app.user.main_user.getId(), app.user.main_user.getNickname(), sc_name.get(main_position), chatText.getText().toString(), new Callback<JsonObject>() {
                        @Override
                        public void success(JsonObject jsonObject, Response response) {
                            Log.d("test", response.toString()); //관리자
                            side = false;
                            sendChatMessage();


//                            ad.secretboard_show((int) app.user.main_user.getId(), new Callback<JsonObject>() {
//                                @Override
//                                public void success(JsonObject jsonObject, Response response) {
//                                    sc_arraylist.clear();
//                                    if (jsonObject.get("status").getAsString().equals("OK")) {
//                                        JsonArray result = jsonObject.getAsJsonArray("result");
//                                        for (int i = 0; i < result.size(); i++) {
//
//                                            JsonObject n1 = (JsonObject) result.get(i);
//                                            int s_id = n1.get("s_id").getAsInt();
//                                            int r_id = n1.get("r_id").getAsInt();
//                                            String s_nickname = n1.get("s_nickname").getAsString();
//                                            String r_nickname = n1.get("r_nickname").getAsString();
//                                            String text = n1.get("text").getAsString();
//
//                                            Secretboard sc = new Secretboard(s_id, r_id, s_nickname, r_nickname, text);
//
//                                            sc_arraylist.add(sc);
//                                        }
//                                        for (int i = 0; i < sc_arraylist.size(); i++) {
//                                            if (sc_name.get(main_position).equals(sc_arraylist.get(i).nickname1)) {
//                                                side = true;
//                                                chatText.setText(sc_arraylist.get(i).text);
//                                                sendChatMessage();
//                                            } else if (!sc_name.get(main_position).equals(sc_arraylist.get(i).nickname1) && sc_arraylist.get(i).nickname1.equals("박영수") && sc_name.get(main_position).equals(sc_arraylist.get(i).nickname2)) {
//                                                side = false;
//                                                chatText.setText(sc_arraylist.get(i).text);
//                                                sendChatMessage();
//                                            }
//                                        }
//                                        chatArrayAdapter.notifyDataSetChanged();
//                                    }
//                                }
//
//                                @Override
//                                public void failure(RetrofitError error) {
//                                    Log.d("error", error.toString());
//                                }
//                            });

                        }
                        @Override
                        public void failure(RetrofitError error) {
                            Log.d("test",error.toString());
                        }
                    });
                } else {
                    ad.secretboard_write((int) app.user.main_user.getId(),  app.user.main_user.getNickname(),"박영수", chatText.getText().toString(), new Callback<JsonObject>() {
                        @Override
                        public void success(JsonObject jsonObject, Response response) {
                            Log.d("test", response.toString()); //일반회원
                            side = false;
                           sendChatMessage();
//                            ad.secretboard_show((int) app.user.main_user.getId(), new Callback<JsonObject>() {
//                                @Override
//                                public void success(JsonObject jsonObject, Response response) {
//                                    sc_arraylist.clear();
//                                    if (jsonObject.get("status").getAsString().equals("OK")) {
//                                        JsonArray result = jsonObject.getAsJsonArray("result");
//                                        for (int i = 0; i < result.size(); i++) {
//
//                                            JsonObject n1 = (JsonObject) result.get(i);
//                                            int s_id = n1.get("s_id").getAsInt();
//                                            int r_id = n1.get("r_id").getAsInt();
//                                            String s_nickname = n1.get("s_nickname").getAsString();
//                                            String r_nickname = n1.get("r_nickname").getAsString();
//                                            String text = n1.get("text").getAsString();
//
//                                            Secretboard sc = new Secretboard(s_id, r_id, s_nickname, r_nickname, text);
//
//                                            sc_arraylist.add(sc);
//                                        }
//
//                                        for (int i = 0; i < sc_arraylist.size(); i++) {
//                                            if (app.user.main_user.getNickname().equals(sc_arraylist.get(i).nickname1)) {
//                                                side = false;
//                                                chatText.setText(sc_arraylist.get(i).text);
//                                                sendChatMessage();
//                                            } else if (sc_arraylist.get(i).nickname1.equals("박영수") && sc_arraylist.get(i).nickname2.equals(app.user.main_user.getNickname())) {
//                                                side = true;
//                                                chatText.setText(sc_arraylist.get(i).text);
//                                                sendChatMessage();
//                                            }
//                                        }
//                                        chatArrayAdapter.notifyDataSetChanged();
//                                    }
//                                }
//
//                                @Override
//                                public void failure(RetrofitError error) {
//
//                                }
//                            });
                        }
                        @Override
                        public void failure(RetrofitError error) {
                            Log.d("test", error.toString());
                        }
                    });
                }
                //구분


                /////전체 리스트 다시 불러오기////////
            }
        });

        TimerTask myTask = new TimerTask() {
            @Override
            public void run() {

                ad.secretboard_show((int) app.user.main_user.getId(), new Callback<JsonObject>() {
                    @Override
                    public void success(JsonObject jsonObject, Response response) {

                        if (jsonObject.get("status").getAsString().equals("OK")) {
                            sc_arraylist.clear();

                            JsonArray result = jsonObject.getAsJsonArray("result");
                            for (int i = 0; i < result.size(); i++) {

                                JsonObject n1 = (JsonObject) result.get(i);
                                int s_id = n1.get("s_id").getAsInt();
                                int r_id = n1.get("r_id").getAsInt();
                                String s_nickname = n1.get("s_nickname").getAsString();
                                String r_nickname = n1.get("r_nickname").getAsString();
                                String text = n1.get("text").getAsString();

                                Secretboard sc = new Secretboard(s_id, r_id, s_nickname, r_nickname, text);

                                sc_arraylist.add(sc);
                            }

                            if (app.user.main_user.getNickname().equals("박영수")) {
                                for (int i = 0; i < sc_arraylist.size(); i++) {
                                    sc_name.add(sc_arraylist.get(i).nickname1);
                                }

                                Log.d("Test", sc_name.toString());

                                HashSet hs = new HashSet(sc_name); //중복제거

                                ArrayList<String> newArrayList = new ArrayList<String>();

                                Iterator it = hs.iterator();
                                while (it.hasNext()) {
                                    newArrayList.add((String) it.next());
                                }

                                sc_name.clear();

                                for (int i = 0; i < newArrayList.size(); i++) {
                                    if (!newArrayList.get(i).equals("박영수"))
                                        sc_name.add(newArrayList.get(i));
                                }
                                spi_adapter.notifyDataSetChanged();
                                chatArrayAdapter.notifyDataSetChanged();

                                chatMessageList.clear();

                                for (int i = 0; i < sc_arraylist.size(); i++) {
                                    if (sc_name.get(main_position).equals(sc_arraylist.get(i).nickname1)) {
                                        side = true;
                                        //chatText.setText(sc_arraylist.get(i).text);
                                        chatArrayAdapter.add(new ChatMessage(side,sc_arraylist.get(i).text));
                                    } else if (!sc_name.get(main_position).equals(sc_arraylist.get(i).nickname1) && sc_arraylist.get(i).nickname1.equals("박영수") && sc_name.get(main_position).equals(sc_arraylist.get(i).nickname2)) {
                                        side = false;
                                        //chatText.setText(sc_arraylist.get(i).text);
                                        chatArrayAdapter.add(new ChatMessage(side,sc_arraylist.get(i).text));
                                    }
                                }

                                chatArrayAdapter.notifyDataSetChanged();

                                spi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                        chatMessageList.clear();

                                        main_position = position;

                                        for (int i = 0; i < sc_arraylist.size(); i++) {
                                            if (sc_name.get(position).equals(sc_arraylist.get(i).nickname1)) {
                                                side = true;
                                                chatText.setText(sc_arraylist.get(i).text);
                                                sendChatMessage();
                                            } else if (!sc_name.get(position).equals(sc_arraylist.get(i).nickname1) && sc_arraylist.get(i).nickname1.equals("박영수") && sc_name.get(position).equals(sc_arraylist.get(i).nickname2)) {
                                                side = false;
                                                chatText.setText(sc_arraylist.get(i).text);
                                                sendChatMessage();
                                            }
                                        }

                                        chatArrayAdapter.notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                            } else {

                                chatMessageList.clear();

                                for (int i = 0; i < sc_arraylist.size(); i++) {
                                    if (app.user.main_user.getNickname().equals(sc_arraylist.get(i).nickname1)) {
                                        side = false;
                                        //chatText.setText(sc_arraylist.get(i).text);
                                        chatArrayAdapter.add(new ChatMessage(side, sc_arraylist.get(i).text));
                                    } else if (sc_arraylist.get(i).nickname1.equals("박영수") && sc_arraylist.get(i).nickname2.equals(app.user.main_user.getNickname())) {
                                        side = true;
                                       // chatText.setText(sc_arraylist.get(i).text);
                                        chatArrayAdapter.add(new ChatMessage(side, sc_arraylist.get(i).text));
                                    }
                                }

                                chatArrayAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                    @Override
                    public void failure(RetrofitError error) {

                    }
                });
            }
        };
        Timer  timer = new Timer();
        timer.schedule(myTask,3000,3000);

        return view;
    }

    private boolean sendChatMessage(){

        chatArrayAdapter.add(new ChatMessage(side, chatText.getText().toString()));
        chatText.setText("");

        return true;
    }

    public class ChatArrayAdapter extends ArrayAdapter<ChatMessage> {

        private TextView chatText;

        private LinearLayout singleMessageContainer;

        @Override
        public void add(ChatMessage object) {
            chatMessageList.add(object);
            super.add(object);
        }

        public ChatArrayAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
        }

        public int getCount() {
            return chatMessageList.size();
        }

        public ChatMessage getItem(int index) {
            return chatMessageList.get(index);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            if (row == null) {
                LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(R.layout.activity_chat_singlemessage, parent, false);
            }
            singleMessageContainer = (LinearLayout) row.findViewById(R.id.singleMessageContainer);
            ChatMessage chatMessageObj = getItem(position);
            chatText = (TextView) row.findViewById(R.id.singleMessage);
            chatText.setText(chatMessageObj.message);
            chatText.setBackgroundResource(chatMessageObj.left ? R.drawable.bubble_b : R.drawable.bubble_a);
            singleMessageContainer.setGravity(chatMessageObj.left ? Gravity.LEFT : Gravity.RIGHT);

            return row;
        }

        public Bitmap decodeToBitmap(byte[] decodedByte) {
            return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
        }

    }
    public class ChatMessage {
        public boolean left;
        public String message;

        public ChatMessage(boolean left, String message) {
            super();
            this.left = left;
            this.message = message;
        }
    }
}
