package com.example.as1.api;

import java.util.List;
<<<<<<< HEAD
=======
import java.util.Set;
>>>>>>> e4bd5efa0959dace6727f5d963664afec0538f65

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
<<<<<<< HEAD
import com.example.as1.model.*;
=======
import com.example.as1.model.Ingredient;
import com.example.as1.model.PantryIngredient;
>>>>>>> e4bd5efa0959dace6727f5d963664afec0538f65

public interface IngredientApi {

    @GET("ingredient/all")
    Call<List<Ingredient>> GetAllIngredients();

    @POST("ingredient/post/{name}")
    Call<Ingredient> PostIngredientByPath(@Path("name") String name);

    @POST("ingredient/post")
    Call<Ingredient> PostIngredientByBody(@Body Ingredient newIngredient);

//    @DELETE("ingredient/delete/{id}")
//    Call<Void> deleteIngredientById(@Path("id") Long id);
//
//    @DELETE("pantryIng/delete/{userId}/{pantryIngId}")
//    Call<Void> deleteIngredientById(@Path("id") Long id);

    @GET("/recipe/{recipeId}/ingredients")
    Call<List<Ingredient>> getIngredientsForRecipe();

    @PUT("pantryIng/quantity/{userId}/{pantryIngId}/{quantity}")
    Call<Ingredient> setQuantity(
            @Path("userId") Long userId,
            @Path("pantryIngId") Long pantryIngId,
            @Path("quantity") int quantity
    );




}
