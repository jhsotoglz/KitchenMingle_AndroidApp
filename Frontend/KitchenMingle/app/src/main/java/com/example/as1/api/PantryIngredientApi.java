package com.example.as1.api;
import com.example.as1.model.PantryIngredient;

import java.util.Set;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * This interface defines the contract for interacting with the Pantry Ingredient API.
 */
public interface PantryIngredientApi {

    @POST("/pantryIng/add/{userId}/{ingredientId}")
    Call<Set<PantryIngredient>> addPantryIngredient(@Path("userId") Long userId, @Path("ingredientId") Long ingredientId);

    @PUT("/pantryIng/quantity/{userId}/{pantryIngId}/{quantity}")
    Call<Void> setQuantity(@Path("userId") Long userId, @Path("pantryIngId") Long pantryIngId, @Path("quantity") int quantity);

    @PUT("/pantryIng/increment/{userId}/{pantryIngId}")
    Call<Void> incrementQuantity(@Path("userId") Long userId, @Path("pantryIngId") Long pantryIngId);

    @PUT("/pantryIng/decrement/{userId}/{pantryIngId}")
    Call<Void> decrementQuantity(@Path("userId") Long userId, @Path("pantryIngId") Long pantryIngId);

    @DELETE("/pantryIng/delete/{userId}/{pantryIngId}")
    Call<Void> deleteIngredient(@Path("userId") Long userId, @Path("pantryIngId") Long pantryIngId);
}

