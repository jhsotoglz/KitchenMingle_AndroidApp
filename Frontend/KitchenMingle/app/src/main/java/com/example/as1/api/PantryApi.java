package com.example.as1.api;
import com.example.as1.model.Pantry;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface PantryApi {
    @GET("/pantry/{userId}")
    Call<Pantry> getPantryForUser(@Path("userId") Long userId);
}
