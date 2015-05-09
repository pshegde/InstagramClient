package com.pshegde.instagramclient;

/**
 * Created by Prajakta on 5/9/2015.
 */
public class InstagramComment {
    private String text;
    private String username;
    private String createdTime;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }
}
