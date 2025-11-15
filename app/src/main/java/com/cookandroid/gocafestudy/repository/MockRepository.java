package com.cookandroid.gocafestudy.repository;

import com.cookandroid.gocafestudy.datas.MockData;
import com.cookandroid.gocafestudy.models.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 앱에서 서버 대신 목 데이터를 제공하는 Repository 클래스
 * 실제 서버 연동 시 Retrofit, Volley, OkHttp 등으로 대체
 */
public class MockRepository {

    // ---------------------------
    // User 관련
    // ---------------------------
    public List<User> getUsers() {
        // TODO: 서버 연동 시 GET /users API 호출
        return MockData.getUsers();
    }

    public User getUserById(int userId) {
        // TODO: 서버 연동 시 GET /users/{id}
        for (User u : MockData.getUsers()) {
            if (u.getUserId() == userId) return u;
        }
        return null;
    }

    // ---------------------------
    // Cafe 관련
    // ---------------------------
    public List<Cafe> getCafes() {
        // TODO: 서버 연동 시 GET /cafes
        return MockData.getCafes();
    }

    public Cafe getCafeById(int cafeId) {
        // TODO: 서버 연동 시 GET /cafes/{id}
        for (Cafe c : MockData.getCafes()) {
            if (c.getCafeId() == cafeId) return c;
        }
        return null;
    }

    // ---------------------------
    // Review 관련
    // ---------------------------
    public List<Review> getReviews() {
        // TODO: 서버 연동 시 GET /reviews
        return MockData.getReviews();
    }

    public List<Review> getReviewsByCafeId(int cafeId) {
        // TODO: 서버 연동 시 GET /cafes/{id}/reviews
        List<Review> result = new java.util.ArrayList<>();
        for (Review r : MockData.getReviews()) {
            if (r.getCafeId() == cafeId) result.add(r);
        }
        return result;
    }

    // ---------------------------
    // Bookmark 관련
    // ---------------------------
    public List<Bookmark> getBookmarksByUserId(int userId) {
        // TODO: 서버 연동 시 GET /users/{id}/bookmarks
        List<Bookmark> result = new java.util.ArrayList<>();
        for (Bookmark b : MockData.getBookmarks()) {
            if (b.getUserId() == userId) result.add(b);
        }
        return result;
    }







    // ---------------------------
    // Category / Feature 관련
    // ---------------------------
    public List<Category> getCategories() {
        // TODO: 서버 연동 시 GET /categories
        return MockData.getCategories();
    }

    public List<Feature> getFeatures() {
        // TODO: 서버 연동 시 GET /features
        return MockData.getFeatures();
    }

    // ---------------------------
    // POST, PUT, DELETE 예시 (주석)
    // ---------------------------
    // public void addReview(Review review) {
    //     // TODO: 서버 연동 시 POST /reviews
    // }
    //
    // public void addBookmark(Bookmark bookmark) {
    //     // TODO: 서버 연동 시 POST /bookmarks
    // }
    //
    // public void updateUser(User user) {
    //     // TODO: 서버 연동 시 PUT /users/{id}
    // }
    //
    // public void deleteBookmark(int bookmarkId) {
    //     // TODO: 서버 연동 시 DELETE /bookmarks/{id}
    // }
}
