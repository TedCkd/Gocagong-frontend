package com.cookandroid.gocafestudy.repository;

import android.content.Context;
import com.cookandroid.gocafestudy.activities.AuthInterceptor;
import com.cookandroid.gocafestudy.api.BookmarkApi;
import com.cookandroid.gocafestudy.api.CafeApi;
import com.cookandroid.gocafestudy.models.GET.UserResponse;
import com.cookandroid.gocafestudy.api.TestAuthApi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class RetrofitClient {
    private static final String BASE_URL = "https://go-cagong.ddns.net/";
    private static Retrofit nonAuthRetrofit = null; // 인증 불필요
    private static Retrofit authRetrofit = null;    // 인증 필요 (인터셉터 적용)

    private RetrofitClient() {}

    // 🌟 커스텀 Gson Builder 생성
    private static Gson createCustomGson() {
        return new GsonBuilder()
                // 서버의 ISO 8601 형식(타임존 없음, 정밀한 밀리초)에 맞추기 위해
                // 초 단위까지만 파싱하도록 설정하여 JsonSyntaxException을 방지
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();
    }


    // 1. 일반 API 호출용 (인증 불필요)
    public static Retrofit getClient() {
        if (nonAuthRetrofit == null) {
            nonAuthRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    // 🌟 커스텀 Gson 적용
                    .addConverterFactory(GsonConverterFactory.create(createCustomGson()))
                    .build();
        }
        return nonAuthRetrofit;
    }

    // 2. 인증 필요한 API 호출용 (AuthInterceptor 적용)
    public static Retrofit getAuthClient(Context context) {
        if (authRetrofit == null) {
            OkHttpClient client = new OkHttpClient.Builder()
                    // AuthInterceptor를 OkHttpClient에 추가
                    .addInterceptor(new AuthInterceptor(context))
                    .build();

            authRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    // 🌟 커스텀 Gson 적용
                    .addConverterFactory(GsonConverterFactory.create(createCustomGson()))
                    .build();
        }
        return authRetrofit;
    }

    // 3. 서비스 인스턴스 제공 메서드

    // 유저 정보 API (인증 필요)
    public static TestAuthApi getAuthApi(Context context) {
        return getAuthClient(context).create(TestAuthApi.class);
    }

    // 카페 API (인증 불필요한 경우)
    public static CafeApi getCafeApi() {
        return getClient().create(CafeApi.class);
    }

    // 카페 API (인증 필요한 경우: 예, 리뷰 등록, 북마크)
    public static CafeApi getAuthCafeApi(Context context) {
        return getAuthClient(context).create(CafeApi.class);
    }

    public static BookmarkApi getBookmarkApi(Context context) {
        return getAuthClient(context).create(BookmarkApi.class);
    }
}