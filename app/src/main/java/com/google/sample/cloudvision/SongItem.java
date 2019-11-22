package com.google.sample.cloudvision;

public class SongItem {
    private String title;
    private String performer;
    private String genre;
    private String adj;

    public SongItem(){}

    public SongItem(String title, String performer, String genre, String adj) {
        this.title = title;
        this.performer = performer;
        this.genre = genre;
        this.adj = adj;
    }

    public SongItem(String title, String performer, String genre) {
        this.title = title;
        this.performer = performer;
        this.genre = genre;
    }

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

    public String getGenre() {
        return genre;
    }

    public String getAdj() {
        return adj;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPerformer(String performer) {
        this.performer = performer;
    }

    public void setGenre(String genre) {
        this.performer =genre;
    }

    public void setAdj(String adj) {
        this.adj = adj;
    }
}
