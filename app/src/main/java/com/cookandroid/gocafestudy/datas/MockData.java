package com.cookandroid.gocafestudy.datas;

import com.cookandroid.gocafestudy.models.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MockData {

    public static List<User> getUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User(1, "홍길동", "hong@test.com", "google", "USER", new Date(), new Date()));
        users.add(new User(2, "김철수", "kim@test.com", "kakao", "USER", new Date(), new Date()));
        return users;
    }

    public static List<Cafe> getCafes() {
        List<Cafe> cafes = new ArrayList<>();

        List<CafePhoto> photos1 = new ArrayList<>();
        photos1.add(new CafePhoto(1, 201, "https://s3-main-image-url.com", true, 1));

        cafes.add(new Cafe(
                201, "센트럴파크 카페", "인천 연수구 센트럴로 263", "032-123-4567",
                "넓은 공간과 편안한 좌석", 37.456, 126.705, photos1, getReviews(), new ArrayList<>()
        ));
        cafes.add(new Cafe(
                202, "바다쏭", "인천광역시 연수구 능허대로 16", "032-833-4223",
                "한옥과 바다 전망", 37.382, 126.657, photos1, getReviews(), new ArrayList<>()
        ));

        // 새로 추가된 '포레스트 아웃팅스 송도점' 카페 데이터
        cafes.add(new Cafe(
                203, "포레스트 아웃팅스 송도점", "인천광역시 연수구 청량로 145", "032-831-3750",
                "실내에 식물이 많음", 37.404, 126.657, photos1, getReviews(), new ArrayList<>()
        ));

        return cafes;
    }
    public static List<Review> getReviews() {
        List<Review> reviews = new ArrayList<>();

        // 날짜 생성용
        Date date1 = new Date(125, 0, 10); // 2025-01-10
        Date date2 = new Date(125, 0, 12); // 2025-01-12
        Date date3 = new Date(125, 0, 15); // 2025-01-15
        Date date4 = new Date(125, 1, 2);  // 2025-02-02
        Date date5 = new Date(125, 1, 10); // 2025-02-10
        Date date6 = new Date(125, 2, 1);  // 2025-03-01

        // 센트럴파크 카페 (cafeId = 201)
        reviews.add(new Review(101, 1, 201, "센트럴파크 카페",
                "https://s3-main-image-url.com", 5,
                "넓고 쾌적한 공간, 편안한 좌석이 인상적이에요.", date1));

        reviews.add(new Review(102, 2, 201, "센트럴파크 카페",
                "https://s3-main-image-url.com", 4,
                "커피 맛도 괜찮고 분위기가 조용해서 공부하기 좋아요.", date2));

        // 바다쏭 (cafeId = 202)
        reviews.add(new Review(103, 1, 202, "바다쏭",
                "https://s3-main-image-url.com", 4,
                "한옥과 바다 전망이 정말 멋져요.", date3));

        reviews.add(new Review(104, 2, 202, "바다쏭",
                "https://s3-main-image-url.com", 3,
                "조금 시끄러운 편이지만 경치 때문에 괜찮아요.", date4));

        // 포레스트 아웃팅스 송도점 (cafeId = 203)
        reviews.add(new Review(105, 1, 203, "포레스트 아웃팅스 송도점",
                "https://s3-main-image-url.com", 5,
                "식물이 많아서 힐링되는 카페예요.", date5));

        reviews.add(new Review(106, 2, 203, "포레스트 아웃팅스 송도점",
                "https://s3-main-image-url.com", 4,
                "커피 맛이 좋고 분위기가 조용해서 좋아요.", date6));

        return reviews;
    }



    public static List<Bookmark> getBookmarks() {
        List<Bookmark> bookmarks = new ArrayList<>();
        bookmarks.add(new Bookmark(50, 1, 201, "센트럴파크 카페", "인천 연수구 센트럴로 263",
                4.8, "https://s3-main-image-url.com", new Date()));
        bookmarks.add(new Bookmark(51, 1, 305, "송도 스터디룸", "인천 연수구 송도동 123",
                4.5, "https://s3-main-image-url.com", new Date()));
        return bookmarks;
    }

    public static List<Category> getCategories() {
        List<Category> categories = new ArrayList<>();
        categories.add(new Category(1, "스터디", "조용한 공부 가능 카페", new Date()));
        categories.add(new Category(2, "감성", "사진 찍기 좋은 카페", new Date()));
        return categories;
    }

    public static List<Feature> getFeatures() {
        List<Feature> features = new ArrayList<>();
        features.add(new Feature(1, "편의", "콘센트", "YES/NO", "콘센트 사용 가능 여부", new Date()));
        features.add(new Feature(2, "편의", "와이파이", "YES/NO", "와이파이 사용 가능 여부", new Date()));
        return features;
    }
}
