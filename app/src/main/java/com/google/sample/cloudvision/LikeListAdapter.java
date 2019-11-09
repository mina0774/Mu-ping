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
    private ArrayList<LikeItem> data;
    private int layout;

    public LikeListAdapter(Context context, int layout, ArrayList<LikeItem> data) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data = data;
        this.layout = layout;
    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public String getItem(int i) {
        return data.get(i).getSonginfo();
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
        LikeItem likeItem = data.get(i);

        TextView songinfo = (TextView) view.findViewById(R.id.songinfo);
        songinfo.setText(likeItem.getSonginfo());

        TextView info = (TextView) view.findViewById(R.id.info);
        info.setText(likeItem.getInfo());

        return view;
    }
}
