package com.cookandroid.gocafestudy.models;

public class CafePhoto {
    private int photoId;
    private int cafeId;
    private String imageUrl;
    private boolean isMain;
    private int sortOrder;

    public CafePhoto(int photoId, int cafeId, String imageUrl, boolean isMain, int sortOrder) {
        this.photoId = photoId;
        this.cafeId = cafeId;
        this.imageUrl = imageUrl;
        this.isMain = isMain;
        this.sortOrder = sortOrder;
    }

    // Getter, Setter 생략

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

    public int getCafeId() {
        return cafeId;
    }

    public void setCafeId(int cafeId) {
        this.cafeId = cafeId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isMain() {
        return isMain;
    }

    public void setMain(boolean main) {
        isMain = main;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }
}
