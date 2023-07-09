package com.example.task_login_signup_screen;

import com.example.task_login_signup_screen.network.requests.SignInRequest;
import com.example.task_login_signup_screen.network.requests.SignUpRequest;
import com.example.task_login_signup_screen.network.responses.RegisterResponse;
import com.example.task_login_signup_screen.network.responses.SignInResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthService { //

    @POST("api/authaccount/registration")
    Call<RegisterResponse> register(@Body SignUpRequest request);

    @POST("api/authaccount/login")
    Call<SignInResponse> loginUser(@Body SignInRequest request);
}
