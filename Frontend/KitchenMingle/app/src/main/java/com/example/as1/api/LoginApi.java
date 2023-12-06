package com.example.as1.api;

import com.example.as1.model.RegistrationRequest;
import com.example.as1.model.LoginResponse;
import com.example.as1.model.LoginRequest;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Body;


public interface LoginApi {
    @POST("/register")
    Call<String> unifiedRegister(@Body RegistrationRequest registrationRequest);

    @POST("/login")
    Call<String> unifiedLogin(@Body LoginRequest loginRequest);
}
