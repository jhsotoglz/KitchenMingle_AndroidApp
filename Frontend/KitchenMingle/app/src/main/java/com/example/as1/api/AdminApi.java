package com.example.as1.api;

import com.example.as1.model.Users;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AdminApi {

    @GET("users")
    Call<List<Users>> getAllUsers();

    @DELETE("users/{id}")
    Call<Void> deleteUser(@Path("id") Long userId);

    @POST("users")
    Call<Users> createUser(@Body Users user);
}


