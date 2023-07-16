package com.example.task_login_signup_screen.network;

import static com.example.task_login_signup_screen.utils.Constants.BASE_URL;

import android.util.Log;

import com.example.task_login_signup_screen.AuthService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static RetrofitClient retrofitClient;
    private static Retrofit retrofit;

    private RetrofitClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Log.e("TAG", BASE_URL);
    }

    public static synchronized RetrofitClient getInstance() {
        if (retrofitClient == null) {
            retrofitClient = new RetrofitClient();
        }
        return retrofitClient;
    }

    public AuthService getApi() {
        return retrofit.create(AuthService.class);
    }
}
