package com.cookandroid.gocafestudy.activities;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.cookandroid.gocafestudy.R;
import com.cookandroid.gocafestudy.api.CafeApi; // 🌟 추가
import com.cookandroid.gocafestudy.repository.RetrofitClient; // 🌟 추가
import com.cookandroid.gocafestudy.utils.UserSessionManager; // 🌟 추가
import com.cookandroid.gocafestudy.models.GET.UserResponse; // 유저 정보 모델
import com.cookandroid.gocafestudy.models.POST.ReviewCreateResponse; // 응답 모델

import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.korean.KoreanTextRecognizerOptions;

// 🌟 OkHttp/Retrofit Multipart 관련 Import
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ActivityWriteReview extends AppCompatActivity {

    private static final String TAG = "WriteReviewActivity";

    private EditText etReview;
    private Button btnSubmit, btnCamera, btnGallery;

    // --- 기존 영수증 관련 ---
    private ImageView ivReceiptPreview, ivRemoveImage, ivReceiptStatusIcon;
    private LinearLayout layoutReceiptStatus, layoutImagePlaceholder;
    private TextView tvReceiptStatus, tvCharCount;
    private RatingBar ratingBar;
    private boolean receiptVerified = false; // 🚨 임시로 true로 처리될 예정

    // --- 기존 이미지 선택용 (사용 안 함) ---
    private List<String> selectedImages = new ArrayList<>();

    // --- 리뷰 사진 추가용 ---
    private LinearLayout layoutReviewImages;
    private Button btnReviewCamera, btnReviewGallery;
    private List<Bitmap> reviewBitmaps = new ArrayList<>();
    private static final int MAX_REVIEW_IMAGES = 5;

    // --- ActivityResultLauncher ---
    private ActivityResultLauncher<Void> cameraLauncher;
    private ActivityResultLauncher<String> galleryLauncher;
    private ActivityResultLauncher<Void> reviewCameraLauncher;
    private ActivityResultLauncher<String> reviewGalleryLauncher;

    private static final int PERMISSION_CAMERA_REQUEST = 2000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_review);

        // --- View 초기화 ---
        etReview = findViewById(R.id.etReview);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnCamera = findViewById(R.id.btnCamera);
        btnGallery = findViewById(R.id.btnGallery);
        ivReceiptPreview = findViewById(R.id.ivReceiptPreview);
        ivRemoveImage = findViewById(R.id.ivRemoveImage);
        layoutReceiptStatus = findViewById(R.id.layoutReceiptStatus);
        layoutImagePlaceholder = findViewById(R.id.layoutImagePlaceholder);
        tvReceiptStatus = findViewById(R.id.tvReceiptStatus);
        tvCharCount = findViewById(R.id.tvCharCount);
        ratingBar = findViewById(R.id.ratingBar);
        ivReceiptStatusIcon = findViewById(R.id.ivReceiptStatusIcon);

        // --- 리뷰 사진 관련 View ---
        layoutReviewImages = findViewById(R.id.layoutReviewImages);
        btnReviewCamera = findViewById(R.id.btnReviewCamera);
        btnReviewGallery = findViewById(R.id.btnReviewGallery);

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        // 🚨 영수증 상태 임시 허용
        layoutReceiptStatus.setVisibility(View.VISIBLE);
        tvReceiptStatus.setText("영수증 인증은 현재 개발 중입니다. (임시 허용)");
        ivReceiptStatusIcon.setImageResource(R.drawable.ic_check_circle);
        receiptVerified = true; // 🚨 무조건 true로 설정

        // --- 글자 수 감지 ---
        etReview.addTextChangedListener(new android.text.TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(android.text.Editable s) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tvCharCount.setText(s.length() + "자");
                checkSubmitEnable();
            }
        });

        // --- 별점 감지 ---
        ratingBar.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> checkSubmitEnable());

        // --- 기존 ActivityResultLauncher 등록 (영수증) ---
        cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.TakePicturePreview(),
                this::processReceiptBitmap
        );
        galleryLauncher = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                uri -> {
                    if (uri != null) {
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            processReceiptBitmap(bitmap);
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(this, "이미지 처리 실패", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        // --- 리뷰 사진 추가용 ActivityResultLauncher ---
        reviewCameraLauncher = registerForActivityResult(
                new ActivityResultContracts.TakePicturePreview(),
                this::addReviewImage
        );
        reviewGalleryLauncher = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                uri -> {
                    if (uri != null) {
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            addReviewImage(bitmap);
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(this, "이미지 처리 실패", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        // --- 기존 버튼 클릭 ---
        btnCamera.setOnClickListener(v -> checkPermissionAndLaunchCamera());
        btnGallery.setOnClickListener(v -> galleryLauncher.launch("image/*"));
        ivRemoveImage.setOnClickListener(v -> removeReceipt());
        btnSubmit.setOnClickListener(v -> submitReview()); // 🌟 Retrofit 호출

        // --- 리뷰 사진 버튼 클릭 ---
        btnReviewCamera.setOnClickListener(v -> {
            if (reviewBitmaps.size() >= MAX_REVIEW_IMAGES) {
                Toast.makeText(this, "최대 5장까지 등록 가능합니다.", Toast.LENGTH_SHORT).show();
                return;
            }
            // reviewCameraLauncher.launch(null); // 이전에 Manifest.permission.CAMERA 권한 체크 후 호출해야 함
            checkReviewImagePermissionAndLaunchCamera();
        });

        btnReviewGallery.setOnClickListener(v -> {
            if (reviewBitmaps.size() >= MAX_REVIEW_IMAGES) {
                Toast.makeText(this, "최대 5장까지 등록 가능합니다.", Toast.LENGTH_SHORT).show();
                return;
            }
            reviewGalleryLauncher.launch("image/*");
        });

        checkSubmitEnable();
    }

    // --- 권한 체크 및 카메라 실행 (영수증용) ---
    private void checkPermissionAndLaunchCamera() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, PERMISSION_CAMERA_REQUEST);
        } else {
            cameraLauncher.launch(null);
        }
    }

    // --- 권한 체크 및 카메라 실행 (리뷰 사진용) ---
    private void checkReviewImagePermissionAndLaunchCamera() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // 카메라 권한 요청은 하나로 통일
            requestPermissions(new String[]{Manifest.permission.CAMERA}, PERMISSION_CAMERA_REQUEST);
        } else {
            reviewCameraLauncher.launch(null);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_CAMERA_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 어떤 버튼을 눌렀는지 알 수 없으므로, 필요하다면 플래그를 사용하여 구분해야 함.
                // 여기서는 일단 영수증 카메라를 실행하도록 임시 처리
                // 또는 reviewCameraLauncher.launch(null); 를 추가할 수도 있음.
                cameraLauncher.launch(null);
            } else {
                Toast.makeText(this, "카메라 권한이 필요합니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // --- 영수증 제거 로직 (무조건 True) ---
    private void removeReceipt() {
        ivReceiptPreview.setVisibility(View.GONE);
        ivRemoveImage.setVisibility(View.GONE);
        layoutImagePlaceholder.setVisibility(View.VISIBLE);

        receiptVerified = true; // 🚨 무조건 true
        layoutReceiptStatus.setVisibility(View.VISIBLE);
        tvReceiptStatus.setText("영수증 인증은 현재 개발 중입니다. (임시 허용)");
        ivReceiptStatusIcon.setVisibility(View.VISIBLE);
        ivReceiptStatusIcon.setImageResource(R.drawable.ic_check_circle);

        checkSubmitEnable();
    }

    // --- 영수증 OCR 처리 로직 (무조건 True) ---
    private void processReceiptBitmap(Bitmap bitmap) {
        if (bitmap == null) return;

        ivReceiptPreview.setImageBitmap(bitmap);
        ivReceiptPreview.setVisibility(View.VISIBLE);
        ivRemoveImage.setVisibility(View.VISIBLE);
        layoutImagePlaceholder.setVisibility(View.GONE);

        layoutReceiptStatus.setVisibility(View.VISIBLE);
        tvReceiptStatus.setText("✅ 영수증으로 인식됨 (임시 허용)");
        ivReceiptStatusIcon.setVisibility(View.VISIBLE);
        ivReceiptStatusIcon.setImageResource(R.drawable.ic_check_circle);

        receiptVerified = true; // 🚨 무조건 true

        checkSubmitEnable();

        // 🚨 OCR 로직은 주석 처리하거나 제거
        // InputImage image = InputImage.fromBitmap(bitmap, 0);
        // TextRecognition.getClient(new KoreanTextRecognizerOptions.Builder().build())
        //         .process(image)
        //         .addOnSuccessListener(this::analyzeText)
        //         .addOnFailureListener(e -> { ... });
    }

    // --- OCR 콜백 메서드 (무조건 True) ---
    private void analyzeText(Text result) {
        // 🚨 OCR 결과를 무시하고 무조건 성공 처리
        tvReceiptStatus.setText("✅ 영수증으로 인식됨 (임시 허용)");
        ivReceiptStatusIcon.setVisibility(View.VISIBLE);
        ivReceiptStatusIcon.setImageResource(R.drawable.ic_check_circle);
        receiptVerified = true;
        checkSubmitEnable();
    }


    // --- 리뷰 사진 추가 ---
    private void addReviewImage(Bitmap bitmap) {
        if (bitmap == null) return;

        if (reviewBitmaps.size() >= MAX_REVIEW_IMAGES) {
            Toast.makeText(this, "최대 5장까지 등록 가능합니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        reviewBitmaps.add(bitmap);

        ImageView imageView = new ImageView(this);
        // dp 값을 픽셀로 변환하여 레이아웃 파라미터 설정 (160dp)
        int sizePx = (int) (160 * getResources().getDisplayMetrics().density);
        int marginPx = (int) (16 * getResources().getDisplayMetrics().density);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(sizePx, sizePx);
        params.setMargins(0, 0, marginPx, 0);

        imageView.setLayoutParams(params);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageBitmap(bitmap);

        // 클릭 시 제거 가능
        imageView.setOnClickListener(v -> {
            layoutReviewImages.removeView(imageView);
            reviewBitmaps.remove(bitmap);
        });

        layoutReviewImages.addView(imageView);

        // 이미지 추가 후 스크롤을 끝으로 이동
        final HorizontalScrollView scrollView = findViewById(R.id.scrollReviewImages);
        scrollView.post(() -> scrollView.fullScroll(HorizontalScrollView.FOCUS_RIGHT));
    }

    // --- 제출 버튼 활성화 체크 ---
    private void checkSubmitEnable() {
        boolean enable = etReview.getText().length() >= 10 && receiptVerified && ratingBar.getRating() > 0;
        btnSubmit.setEnabled(enable);
        btnSubmit.setBackgroundColor(enable ?
                ContextCompat.getColor(this, R.color.yellow_primary) : // R.color.yellow_primary가 정의되어 있어야 함
                ContextCompat.getColor(this, android.R.color.darker_gray));
    }

    // 🌟 Bitmap을 캐시 디렉토리에 저장하는 헬퍼 메서드 (Multipart 전송을 위해 필수)
    private File getTempFileFromBitmap(Context context, Bitmap bitmap, String fileName) throws IOException {
        File file = new File(context.getCacheDir(), fileName + ".jpg");
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        byte[] bitmapData = bos.toByteArray();

        FileOutputStream fos = new FileOutputStream(file);
        fos.write(bitmapData);
        fos.flush();
        fos.close();
        bos.close();
        return file;
    }

    // 🌟 Bitmap을 MultipartBody.Part로 변환하는 헬퍼 메서드
    private MultipartBody.Part createMultipartPart(Bitmap bitmap, String partName, int index) {
        try {
            File file = getTempFileFromBitmap(this, bitmap, "review_image_" + index);

            RequestBody requestFile = RequestBody.create(
                    MediaType.parse("image/jpeg"), // MIME Type
                    file
            );

            // 서버 필드 이름은 'images'입니다.
            return MultipartBody.Part.createFormData("images", file.getName(), requestFile);
        } catch (IOException e) {
            Log.e(TAG, "Failed to create multipart part: " + e.getMessage());
            return null;
        }
    }


    // --- 리뷰 제출 (Retrofit API 호출) ---
    private void submitReview() {
        // 1. 데이터 및 유저 정보 준비
        String reviewText = etReview.getText().toString();
        int rating = (int) ratingBar.getRating();
        int cafeId = getIntent().getIntExtra("cafeId", -1);

        UserResponse currentUser = UserSessionManager.getInstance().getCurrentUser();
        if (currentUser == null) {
            Toast.makeText(this, "로그인이 필요합니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (cafeId == -1) {
            Toast.makeText(this, "카페 ID가 유효하지 않습니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        // 2. Multipart RequestBody 준비 (텍스트 필드)
        // Media Type을 "text/plain"으로 설정하는 것이 일반적이지만,
        // Retrofit/OkHttp는 String을 전달할 때 자동으로 처리합니다.
        // 여기서는 명시적으로 RequestBody.create를 사용합니다.
        RequestBody ratingBody = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(rating));
        RequestBody contentBody = RequestBody.create(MediaType.parse("text/plain"), reviewText);

        // 3. 이미지 Part 리스트 준비
        List<MultipartBody.Part> imageParts = new ArrayList<>();
        for (int i = 0; i < reviewBitmaps.size(); i++) {
            MultipartBody.Part part = createMultipartPart(reviewBitmaps.get(i), "images", i);
            if (part != null) {
                imageParts.add(part);
            }
        }

        // 4. API 클라이언트 호출
        CafeApi api = RetrofitClient.getAuthCafeApi(this);
        Call<ReviewCreateResponse> call = api.createReview(
                cafeId,
                ratingBody,
                contentBody,
                imageParts
        );

        // 5. API 호출 및 응답 처리
        call.enqueue(new Callback<ReviewCreateResponse>() {
            @Override
            public void onResponse(Call<ReviewCreateResponse> call, Response<ReviewCreateResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // ✅ 성공
                    ReviewCreateResponse reviewResponse = response.body();
                    Toast.makeText(ActivityWriteReview.this, "리뷰 등록 성공: " + reviewResponse.getMessage(), Toast.LENGTH_LONG).show();

                    android.content.Intent intent = new android.content.Intent();
                    intent.putExtra("newReviewId", reviewResponse.getReview().getReviewId());
                    setResult(RESULT_OK, intent);

                } else {
                    // ❌ 실패
                    String errorMsg = "리뷰 등록 실패: HTTP " + response.code();
                    try {
                        // 에러 바디를 읽어 상세 오류를 출력 (읽은 후에는 닫아줘야 함)
                        if (response.errorBody() != null) {
                            String errorBody = response.errorBody().string();
                            Log.e(TAG, "Review API Error: " + errorBody);
                            // JSON 형식의 에러 메시지를 파싱하여 사용자에게 보여줄 수도 있습니다.
                            errorMsg += "\n" + errorBody;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(ActivityWriteReview.this, errorMsg, Toast.LENGTH_LONG).show();
                }
                finish();
            }

            @Override
            public void onFailure(Call<ReviewCreateResponse> call, Throwable t) {
                // ❌ 네트워크 오류
                Log.e(TAG, "Review API Failure", t);
                Toast.makeText(ActivityWriteReview.this, "네트워크 오류: " + t.getMessage(), Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }
}