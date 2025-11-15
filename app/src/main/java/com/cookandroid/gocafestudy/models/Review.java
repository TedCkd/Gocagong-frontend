package com.cookandroid.gocafestudy.models;

import java.util.Date;

public class Review {
    private int reviewId;
    private int userId;
    private int cafeId;
    private String cafeName;
    private String cafeImageUrl;
    private int rating;
    private String content;
    private Date createdAt;

    public Review(int reviewId, int userId, int cafeId, String cafeName,
                  String cafeImageUrl, int rating, String content, Date createdAt) {
        this.reviewId = reviewId;
        this.userId = userId;
        this.cafeId = cafeId;
        this.cafeName = cafeName;
        this.cafeImageUrl = cafeImageUrl;
        this.rating = rating;
        this.content = content;
        this.createdAt = createdAt;
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCafeId() {
        return cafeId;
    }

    public void setCafeId(int cafeId) {
        this.cafeId = cafeId;
    }

    public String getCafeName() {
        return cafeName;
    }

    public void setCafeName(String cafeName) {
        this.cafeName = cafeName;
    }

    public String getCafeImageUrl() {
        return cafeImageUrl;
    }

    public void setCafeImageUrl(String cafeImageUrl) {
        this.cafeImageUrl = cafeImageUrl;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
// Getter, Setter 생략
}
