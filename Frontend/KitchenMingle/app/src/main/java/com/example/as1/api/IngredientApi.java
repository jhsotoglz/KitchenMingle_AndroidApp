package com.example.as1.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.DELETE;
import com.example.as1.model.Ingredient;

/**
 * This interface defines the contract for interacting with the Ingredient API.
 *
 */
public interface IngredientApi {

    /**
     * Retrieves a list of all ingredients.
     * @return A Call object with a List of Ingredient.
     */
    @GET("ingredient/all")
    Call<List<Ingredient>> GetAllIngredients();

    /**
     * Posts a new ingredient with the specified name using a path parameter
     * @param name The name of the ingredient to be posted.
     * @return A Call object with the posted Ingredient.
     */
    @POST("ingredient/post/{name}")
    Call<Ingredient> PostIngredientByPath(@Path("name") String name);

    /**
     * Posts a new ingredient using the request body.
     * @param newIngredient The new Ingredient to be posted.
     * @return A Call object with the posted Ingredient.
     */
    @POST("ingredient/post")
    Call<Ingredient> PostIngredientByBody(@Body Ingredient newIngredient);

//    @DELETE("ingredient/delete/{id}")
//    Call<Void> deleteIngredientById(@Path("id") Long id);
//
//    @DELETE("pantryIng/delete/{userId}/{pantryIngId}")
//    Call<Void> deleteIngredientById(@Path("id") Long id);

//    @GET("/recipe/{recipeId}/ingredients")
//    Call<List<Ingredient>> getIngredientsForRecipe();

    /**
     * Retrieves a list of ingredients for a specific recipe.
     * @param recipeId The ID of the recipe for which ingredients are to be retrieved
     * @return A Call object with a List of Ingredient for the specified recipe.
     */
    @GET("/recipe/{recipeId}/ingredients")
    Call<List<Ingredient>> getIngredientsForRecipe(@Path("recipeId") int recipeId);

    /**
     * Sets the quantity of a pantry ingredient for a user.
     * @param userId The ID of the user.
     * @param pantryIngId The ID of the pantry ingredient.
     * @param quantity The quantity to be set.
     * @return A Call object with the updated Ingredient.
     */
    @PUT("pantryIng/quantity/{userId}/{pantryIngId}/{quantity}")
    Call<Ingredient> setQuantity(
            @Path("userId") Long userId,
            @Path("pantryIngId") Long pantryIngId,
            @Path("quantity") int quantity
    );

    /**
     * Retrieves a list of all ingredients.
     * @return A Call object with a List of Ingredient.
     */
    @GET("ingredients")
    Call<List<Ingredient>> getAllIngredients();

    /**
     * Retrieves a specific ingredient by ID.
     * @param id The ID of the ingredient to be retrieved.
     * @return A Call object with the retrieved Ingredient.
     */
    @GET("ingredient/{id}")
    Call<Ingredient> getIngredient(@Path("id") Long id);

    /**
     * Creates a new ingredient using the request body.
     * @param newIngredient The new Ingredient to be created.
     * @return A Call object with the created Ingredient.
     */
    @POST("ingredients/create")
    Call<Ingredient> createIngredient(@Body Ingredient newIngredient);

    /**
     * Updates an existing ingredient with the specified ID using the request body.
     * @param id The ID of the ingredient to be updated.
     * @param updatedIngredient The updated Ingredient object.
     * @return A Call object with the updated Ingredient.
     */
    @PUT("ingredient/update/{id}")
    Call<Ingredient> updateIngredient(@Path("id") Long id, @Body Ingredient updatedIngredient);

    /**
     * Deletes an ingredient with the specified ID.
     * @param id The ID of the ingredient to be deleted.
     * @return A Call object representing the deletion operation.
     */
    @DELETE("ingredient/delete/{id}")
    Call<Void> deleteIngredient(@Path("id") Long id);






}
