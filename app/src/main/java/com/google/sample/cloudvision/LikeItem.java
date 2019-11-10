package com.google.sample.cloudvision;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LikeItem {
    private String title;
    private String performer;

    public LikeItem() {}

    public LikeItem(String title, String performer) {
        this.title = title;
        this.performer = performer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPerformer() {
        return performer;
    }

    public void setPerformer(String performer) {
        this.performer = performer;
    }





}
