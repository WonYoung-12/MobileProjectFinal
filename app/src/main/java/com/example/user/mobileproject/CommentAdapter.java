package com.example.user.mobileproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by kwy2868 on 2016-06-19.
 */
public class CommentAdapter extends BaseAdapter {
    ArrayList<Comment> items;
    Context context;

    TextView c_nickname;
    TextView c_text;

    public CommentAdapter(){

    }

    public CommentAdapter(Context context, ArrayList<Comment> items){
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return this.items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.commentrow, null);
            c_nickname = (TextView)convertView.findViewById(R.id.c_nickname);
            c_text = (TextView)convertView.findViewById(R.id.c_text);
        }
        c_nickname.setText(items.get(position).getNickname());
        c_text.setText(items.get(position).getText());

        return convertView;
    }
}
