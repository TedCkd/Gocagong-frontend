package com.cookandroid.gocafestudy.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cookandroid.gocafestudy.datas.MockData;
import com.cookandroid.gocafestudy.models.MyReviewItem;
import com.cookandroid.gocafestudy.adapters.MyReviewsAdapter;
import com.cookandroid.gocafestudy.R;
import com.cookandroid.gocafestudy.models.Review;

import java.util.ArrayList;
import java.util.List;

public class ActivityMyReviews extends AppCompatActivity {

    private RecyclerView rvMyReviews;
    private List<MyReviewItem> myReviewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reviews);

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        rvMyReviews = findViewById(R.id.rv_my_reviews);
        rvMyReviews.setLayoutManager(new LinearLayoutManager(this));

        List<Review> allReviews = MockData.getReviews();
        myReviewList = new ArrayList<>();
        for (Review r : allReviews) {
            if (r.getUserId() == 1) { // 내 리뷰만
                myReviewList.add(new MyReviewItem(
                        r.getCafeName(),
                        r.getContent(),
                        r.getCafeImageUrl(),
                        r.getRating()
                ));
            }
        }
        // 어댑터 연결
        MyReviewsAdapter adapter = new MyReviewsAdapter(this, myReviewList);
        rvMyReviews.setAdapter(adapter);

        // 상단 카운트 업데이트
        findViewById(R.id.tv_review_count);
        ((android.widget.TextView)findViewById(R.id.tv_review_count))
                .setText(myReviewList.size() + "개");
    }
}
