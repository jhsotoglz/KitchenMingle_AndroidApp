package com.example.as1.api;

import com.example.as1.model.PantryIngredient;

import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PantryIngredientApi {
    @GET("ingredient/all")
    Call<List<PantryIngredient>> GetAllIngredients();

    @POST("ingredient/post/{name}")
    Call<PantryIngredient> PostIngredientByPath(@Path("name") String name);

    @POST("ingredient/post")
    Call<PantryIngredient> PostIngredientByBody(@Body PantryIngredient newIngredient);

//    @DELETE("ingredient/delete/{id}")
//    Call<Void> deleteIngredientById(@Path("id") Long id);

//    @DELETE("pantryIng/delete/{userId}/{pantryIngId}")
//    Call<Void> deleteIngredientById(@Path("id") Long id);

    @GET("/recipe/{recipeId}/ingredients")
    Call<List<PantryIngredient>> getIngredientsForRecipe();

    @PUT("pantryIng/quantity/{userId}/{pantryIngId}/{quantity}")
    Call<PantryIngredient> setQuantity(
            @Path("userId") Long userId,
            @Path("pantryIngId") Long pantryIngId,
            @Path("quantity") int quantity
    );

    @POST("pantryIng/add/{userId}/{ingredientId}")
    Call<Set<com.example.as1.model.PantryIngredient>> addPantryIngredient(
            @Path("userId") Long userId,
            @Path("ingredientId") Long ingredientId
    );

    @DELETE("pantryIng/delete/{userId}/{pantryIngId}")
    Call<Void> deleteIngredient(
            @Path("userId") Long userId,
            @Path("pantryIngId") Long pantryIngId
    );

}
