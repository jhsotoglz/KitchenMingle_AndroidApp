package com.example.as1;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    // TODO: fix
    @POST("login")
    Call<something> sendLoginRequest(@Body LoginActivity newUser);

    @POST("signup")
    Call<something> sendSignUpRequest(@Body SignUpActivity newUser);
}


