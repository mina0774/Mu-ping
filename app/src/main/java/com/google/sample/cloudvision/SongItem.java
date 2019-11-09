package com.google.sample.cloudvision;

public class SongItem {
    private String title;
    private String performer;

    public SongItem(String title, String performer) {
        this.title = title;
        this.performer = performer;
    }

    public String getTitle() {
        return title;
    }

    public String getPerformer() {
        return performer;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPerformer(String performer) {
        this.performer = performer;
    }
}
