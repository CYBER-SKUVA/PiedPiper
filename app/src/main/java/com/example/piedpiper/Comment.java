package com.example.piedpiper;

public class Comment {
    private String username;
    private String text;
    private String time;
    private int userImage;

    public Comment(String username, String text, String time, int userImage) {
        this.username = username;
        this.text = text;
        this.time = time;
        this.userImage = userImage;
    }

    public String getUsername() {
        return username;
    }

    public String getText() {
        return text;
    }

    public String getTime() {
        return time;
    }

    public int getUserImage() {
        return userImage;
    }
}
