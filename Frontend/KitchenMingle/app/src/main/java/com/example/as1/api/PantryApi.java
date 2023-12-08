package com.example.as1.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import com.example.as1.model.Pantry;



public interface PantryApi {
    @GET("/pantry/{userId}")
    Call<Pantry> getPantryForUser(@Path("userId") Long userId);
}
