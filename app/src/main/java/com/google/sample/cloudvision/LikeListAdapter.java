package com.google.sample.cloudvision;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class LikeListAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private ArrayList<String> list;
    private int layout;

    public LikeListAdapter(Context context, int layout, ArrayList<String> list) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.list = list;
        this.layout = layout;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public String getItem(int i) {
        return list.get(i).toString();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = inflater.inflate(layout, viewGroup, false);
        }
        String likeItem = list.get(i);

        TextView songinfo = (TextView) view.findViewById(R.id.songinfo);
        songinfo.setText(likeItem.toString());

        return view;
    }
}
