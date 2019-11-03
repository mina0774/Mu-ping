package com.google.sample.cloudvision;

import com.google.firebase.database.DataSnapshot;

public class User {
    private String email;
    private String name;
    private String genre;

    public User(String email, String name, String genre) {
        this.email = email;
        this.name = name;
        this.genre = genre;
    }
    public User() {}

    public String getGenre() {
        return genre;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

}
