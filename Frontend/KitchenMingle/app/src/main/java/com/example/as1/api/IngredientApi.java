package com.example.as1.api;

import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import com.example.as1.model.Ingredient;
import com.example.as1.model.PantryIngredient;

public interface IngredientApi {

    @GET("ingredient/all")
    Call<List<Ingredient>> GetAllIngredients();

    @POST("ingredient/post/{name}")
    Call<Ingredient> PostIngredientByPath(@Path("name") String name);

    @POST("ingredient/post")
    Call<Ingredient> PostIngredientByBody(@Body Ingredient newIngredient);

//    @DELETE("ingredient/delete/{id}")
//    Call<Void> deleteIngredientById(@Path("id") Long id);

    @DELETE("pantryIng/delete/{userId}/{pantryIngId}")
    Call<Void> deleteIngredientById(@Path("id") Long id);

//    @GET("/recipe/{recipeId}/ingredients")
//    Call<List<Ingredient>> getIngredientsForRecipe();

    @GET("/recipe/{recipeId}/ingredients")
    Call<List<Ingredient>> getIngredientsForRecipe(@Path("recipeId") int recipeId);

    @PUT("pantryIng/quantity/{userId}/{pantryIngId}/{quantity}")
    Call<Ingredient> setQuantity(
            @Path("userId") Long userId,
            @Path("pantryIngId") Long pantryIngId,
            @Path("quantity") int quantity
    );




}
