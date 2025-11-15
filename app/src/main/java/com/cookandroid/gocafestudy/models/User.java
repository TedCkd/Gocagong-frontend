package com.cookandroid.gocafestudy.models;

import java.util.Date;

public class User {
    private int userId;
    private String name;
    private String email;
    private String provider;
    private String role;
    private Date createdAt;
    private Date updatedAt;

    public User(int userId, String name, String email, String provider, String role, Date createdAt, Date updatedAt) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.provider = provider;
        this.role = role;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
// Getter, Setter 생략
}
