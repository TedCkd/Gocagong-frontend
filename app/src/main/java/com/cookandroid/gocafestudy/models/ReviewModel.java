package com.cookandroid.gocafestudy.models;

public class ReviewModel {
    private String content;
    private float rating;

    public ReviewModel(String content, float rating) {
        this.content = content;
        this.rating = rating;
    }

    public String getContent() { return content; }
    public float getRating() { return rating; }
}