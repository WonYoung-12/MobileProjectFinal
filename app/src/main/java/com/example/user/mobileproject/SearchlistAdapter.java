package com.example.user.mobileproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by kwy2868 on 2016-06-21.
 */
public class SearchlistAdapter extends BaseAdapter {
    ArrayList<Searchlist> items;
    Context context;

    TextView title;
    TextView text;
    TextView name;

    public SearchlistAdapter(Context context, ArrayList<Searchlist> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
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
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.searchrow, null);
            title = (TextView)convertView.findViewById(R.id.s_title);
            text = (TextView)convertView.findViewById(R.id.s_text);
            name = (TextView)convertView.findViewById(R.id.s_nickname);

        }
        title.setText(items.get(position).getTitle());
        text.setText(items.get(position).getText());
        name.setText(items.get(position).getName());

        return convertView;
    }
}
