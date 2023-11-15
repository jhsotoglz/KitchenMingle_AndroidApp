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

/**
 * This interface defines the contract for interacting with the Pantry Ingredient API.
 */
public interface PantryIngredientApi {
    /**
     * Retrieves a list of all pantry ingredients.
     * @return A Call object with a List of PantryIngredient.
     */
    @GET("ingredient/all")
    Call<List<PantryIngredient>> GetAllIngredients();

    /**
     * Posts a new pantry ingredient with the specified name using a path parameter.
     * @param name The name of the pantry ingredient to be posted.
     * @return A Call object with the posted PantryIngredient.
     */
    @POST("ingredient/post/{name}")
    Call<PantryIngredient> PostIngredientByPath(@Path("name") String name);

    /**
     * Posts a new pantry ingredient using the request body.
     * @param newIngredient The new PantryIngredient to be posted.
     * @return A Call object with the posted PantryIngredient.
     */
    @POST("ingredient/post")
    Call<PantryIngredient> PostIngredientByBody(@Body PantryIngredient newIngredient);

//    @DELETE("ingredient/delete/{id}")
//    Call<Void> deleteIngredientById(@Path("id") Long id);

//    @DELETE("pantryIng/delete/{userId}/{pantryIngId}")
//    Call<Void> deleteIngredientById(@Path("id") Long id);

    /**
     * Retrieves a list of pantry ingredients for a specific recipe.
     * @return A Call object with a List of PantryIngredient for the specified recipe.
     */
    @GET("/recipe/{recipeId}/ingredients")
    Call<List<PantryIngredient>> getIngredientsForRecipe();

    /**
     * Sets the quantity of a pantry ingredient for a user.
     * @param userId The ID of the user.
     * @param pantryIngId The ID of the pantry ingredient.
     * @param quantity The quantity to be set.
     * @return
     */
    @PUT("pantryIng/quantity/{userId}/{pantryIngId}/{quantity}")
    Call<PantryIngredient> setQuantity(
            @Path("userId") Long userId,
            @Path("pantryIngId") Long pantryIngId,
            @Path("quantity") int quantity
    );

    /**
     * Adds a new pantry ingredient to the user's pantry.
     * @param userId The ID of the user.
     * @param ingredientId The ID of the ingredient to be added.
     * @return A Call object with a Set of PantryIngredient reflecting the updated pantry.
     */
    @POST("pantryIng/add/{userId}/{ingredientId}")
    Call<Set<com.example.as1.model.PantryIngredient>> addPantryIngredient(
            @Path("userId") Long userId,
            @Path("ingredientId") Long ingredientId
    );

    /**
     * Deletes a pantry ingredient from the user's pantry.
     * @param userId The ID of the user.
     * @param pantryIngId The ID of the pantry ingredient to be deleted.
     * @return A Call object representing the deletion operation.
     */
    @DELETE("pantryIng/delete/{userId}/{pantryIngId}")
    Call<Void> deleteIngredient(
            @Path("userId") Long userId,
            @Path("pantryIngId") Long pantryIngId
    );

}
