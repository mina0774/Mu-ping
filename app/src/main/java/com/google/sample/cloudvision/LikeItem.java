package com.google.sample.cloudvision;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LikeItem {
    private String songinfo;
    private String info;

    public String getSonginfo() {
        return songinfo;
    }

    public String getInfo() {
        return info;
    }

    public LikeItem(String songinfo, String info) {
        this.songinfo = songinfo;
        this.info = info;
    }

}
