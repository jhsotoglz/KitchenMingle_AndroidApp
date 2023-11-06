package com.example.as1.api;

import com.example.as1.model.*;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.PUT;
import retrofit2.http.DELETE;

public interface UsersApi {
    @GET("users")
    Call<List<Users>> getAllUsers();

    @GET("user/{id}")
    Call<Users> getUser(@Path("id") Long id);

    @PUT("users/put/{id}")
    Call<Users> updateUser(@Path("id") Long id, @Body Users newInfo);

    @DELETE("users/delete/{id}")
    Call<Void> deleteUser(@Path("id") Long id);

    @POST("users/login")
    Call<String> login(@Body LoginRequest loginRequest);

    @POST("users/{userId}/addFavRecipe/{recipeId}")
    Call<Void> addFavoriteRecipe(@Path("userId") Long userId, @Path("recipeId") Long recipeId);

    @DELETE("users/{userId}/removeFavRecipe/{recipeId}")
    Call<String> deleteFavoriteRecipe(@Path("userId") Long userId, @Path("recipeId") Long recipeId);

    @GET("users/{userId}/favRecipe")
    Call<List<Recipe>> getFavoriteRecipes(@Path("userId") Long userId);
    @POST("users/register")
    Call<String> RegisterUsers(@Body Users newUser);

    @GET("users/getEmail/{email}")
    Call<Users> GetUsersByEmail(@Path("email") String email);


}
