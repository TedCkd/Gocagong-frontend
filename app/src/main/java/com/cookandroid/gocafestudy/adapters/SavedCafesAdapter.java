package com.cookandroid.gocafestudy.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide; // 서버 이미지 로딩
import com.cookandroid.gocafestudy.models.CafeItem;
import com.cookandroid.gocafestudy.R;

import java.util.List;

public class SavedCafesAdapter extends RecyclerView.Adapter<SavedCafesAdapter.CafeViewHolder> {

    private Context context;
    private List<CafeItem> cafeList;

    public SavedCafesAdapter(Context context, List<CafeItem> cafeList) {
        this.context = context;
        this.cafeList = cafeList;
    }

    @NonNull
    @Override
    public CafeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_saved_cafe, parent, false);
        return new CafeViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull CafeViewHolder holder, int position) {
        CafeItem cafe = cafeList.get(position);
        holder.tvCafeName.setText(cafe.getName());
        holder.tvCafeLocation.setText(cafe.getLocation());

        Glide.with(context)
                .load(cafe.getImageUrl())
                .placeholder(R.drawable.ic_cafe1_img)
                .into(holder.ivCafeImage);

        // 클릭 시 bottom sheet 열기
        holder.itemView.setOnClickListener(v -> {
            // bottom sheet layout inflate
            View bottomSheetView = LayoutInflater.from(context)
                    .inflate(R.layout.bottom_sheet_cafe_detail, null);

            // BottomSheetDialog 생성
            com.google.android.material.bottomsheet.BottomSheetDialog bottomSheetDialog =
                    new com.google.android.material.bottomsheet.BottomSheetDialog(context);
            bottomSheetDialog.setContentView(bottomSheetView);

            // bottom sheet 안에 있는 뷰들 세팅 (예: 이름, 주소, 이미지)
            TextView tvName = bottomSheetView.findViewById(R.id.tv_cafe_name);
            TextView tvAddress = bottomSheetView.findViewById(R.id.tv_address);
            ImageView ivImage = bottomSheetView.findViewById(R.id.iv_cafe_image);

            tvName.setText(cafe.getName());
            tvAddress.setText(cafe.getLocation());
            Glide.with(context)
                    .load(cafe.getImageUrl())
                    .placeholder(R.drawable.ic_cafe1_img)
                    .into(ivImage);

            bottomSheetDialog.show();
        });
    }


    @Override
    public int getItemCount() {
        return cafeList.size();
    }

    public static class CafeViewHolder extends RecyclerView.ViewHolder {
        ImageView ivCafeImage;
        TextView tvCafeName, tvCafeLocation;

        public CafeViewHolder(@NonNull View itemView) {
            super(itemView);
            ivCafeImage = itemView.findViewById(R.id.ivCafeImage);
            tvCafeName = itemView.findViewById(R.id.tvCafeName);
            tvCafeLocation = itemView.findViewById(R.id.tvCafeLocation);
        }
    }
}
