package com.cookandroid.gocafestudy.models;


import com.cookandroid.gocafestudy.models.CafePhoto;
import com.cookandroid.gocafestudy.models.Feature;
import com.cookandroid.gocafestudy.models.Review;

import java.util.List;

public class Cafe {
    private int cafeId;
    private String name;
    private String address;
    private String tel;
    private String description;


    private double latitude;
    private double longitude;
    private List<CafePhoto> photos;
    private List<Review> reviews;
    private List<Feature> features;

    public Cafe(int cafeId, String name, String address, String tel, String description,
                double latitude, double longitude, List<CafePhoto> photos,
                List<Review> reviews, List<Feature> features) {
        this.cafeId = cafeId;
        this.name = name;
        this.address = address;
        this.tel = tel;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.photos = photos;
        this.reviews = reviews;
        this.features = features;
    }

    public int getCafeId() {
        return cafeId;
    }

    public void setCafeId(int cafeId) {
        this.cafeId = cafeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public List<CafePhoto> getPhotos() {
        return photos;
    }

    public void setPhotos(List<CafePhoto> photos) {
        this.photos = photos;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(List<Feature> features) {
        this.features = features;
    }
    // Getter, Setter 생략
}