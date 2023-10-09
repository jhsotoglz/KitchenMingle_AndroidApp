package com.example.as1.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import com.example.as1.model.Ingredient;

public interface IngredientApi {

    @GET("ingredient/all")
    Call<List<Ingredient>> GetAllIngredients();

    @POST("ingredient/post/{name}")
    Call<Ingredient> PostIngredientByPath(@Path("name") String name);

    @POST("ingredient/post")
    Call<Ingredient> PostIngredientByBody(@Body Ingredient newIngredient);

    @DELETE("ingredient/delete/{id}")
    Call<Void> deleteIngredientById(@Path("id") Long id);
}
