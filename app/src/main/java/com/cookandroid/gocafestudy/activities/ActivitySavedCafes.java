package com.cookandroid.gocafestudy.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.ImageButton;

import com.cookandroid.gocafestudy.adapters.SavedCafesAdapter;
import com.cookandroid.gocafestudy.datas.MockData;
import com.cookandroid.gocafestudy.models.Bookmark;
import com.cookandroid.gocafestudy.models.CafeItem;
import com.cookandroid.gocafestudy.R;

import java.util.ArrayList;
import java.util.List;

public class ActivitySavedCafes extends AppCompatActivity {

    private RecyclerView rvSavedCafes;
    private SavedCafesAdapter adapter;
    private List<CafeItem> cafeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_cafes);

        rvSavedCafes = findViewById(R.id.rv_saved_cafes);
        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        // MockData에서 북마크 가져와서 CafeItem으로 변환
        cafeList = new ArrayList<>();
        List<Bookmark> bookmarks = MockData.getBookmarks();
        for (Bookmark b : bookmarks) {
            cafeList.add(new CafeItem(
                    b.getCafeName(),
                    b.getAddress(),
                    b.getMainImageUrl()
            ));
        }

        adapter = new SavedCafesAdapter(this, cafeList);
        rvSavedCafes.setLayoutManager(new LinearLayoutManager(this));
        rvSavedCafes.setAdapter(adapter);
    }
}
