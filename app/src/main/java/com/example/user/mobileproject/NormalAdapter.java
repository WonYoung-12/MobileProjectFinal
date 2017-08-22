package com.example.user.mobileproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by kwy2868 on 2016-06-17.
 */
public class NormalAdapter extends BaseAdapter{
    ArrayList<Normalboard> items;
    Context context;

    TextView n_title;
    TextView n_name;
    TextView n_date;

    public NormalAdapter(){

    }

    public NormalAdapter(Context context, ArrayList<Normalboard> items) {
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
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.normalrow, null);
            n_title = (TextView)convertView.findViewById(R.id.n_title);
            n_name = (TextView)convertView.findViewById(R.id.n_name);
            n_date = (TextView)convertView.findViewById(R.id.n_date);
        }
        n_title.setText(items.get(position).getTitle());
        n_name.setText(items.get(position).getName());
        n_date.setText(items.get(position).getDate());

        return convertView;
    }
}
