package com.example.as1;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("login")
    Call<LoginActivity> sendLoginRequest(@Body LoginActivity newUser);

    @POST("signup")
    Call<SignUpActivity> sendSignUpRequest(@Body SignUpActivity newUser);
}


