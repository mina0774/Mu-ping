package com.google.sample.cloudvision;

public class User {
    private String email;
    private String name;
    private String genre;

    public User(String email, String name, String genre) {
        this.email = email;
        this.name = name;
        this.genre = genre;
    }

    public String getGenre() {
        return genre;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
}
