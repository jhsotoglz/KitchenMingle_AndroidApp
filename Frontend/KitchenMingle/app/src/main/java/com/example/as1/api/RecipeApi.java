package com.example.as1.api;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

import com.example.as1.model.Ingredient;
import com.example.as1.model.Recipe;

/**
 * The RecipeApi interface defines a set of HTTP methods for interacting with a recipe-related API.
 * It provides methods for retrieving, posting, and searching recipes using Retrofit and HTTP requests.
 *
 * @since 1.0
 */
public interface RecipeApi {

    /**
     * Retrieves a list of all recipes from the API.
     *
     * @return A {@link Call} that can be executed to get a list of all recipes.
     */
    @GET("recipe/all")
    Call<List<Recipe>> GetAllRecipes();

    /**
     * Posts a new recipe to the API by specifying the name and instructions in the URL path.
     *
     * @param name         The name of the recipe to post.
     * @param instructions The instructions for the recipe to post.
     * @return A {@link Call} that can be executed to post a new recipe.
     */
    @POST("recipe/post/{name}/{instructions}")
    Call<Recipe> PostRecipeByPath(@Path("name") String name,@Path("instructions") String instructions);

    /**
     * Posts a new recipe to the API by sending a JSON body with the recipe information.
     *
     * @param newRecipe The new recipe to post as a JSON body.
     * @return A {@link Call} that can be executed to post a new recipe.
     */
    @POST("recipe/post")
    Call<Recipe> PostRecipeByBody(@Body Recipe newRecipe);

    /**
     * Retrieves a recipe by its name from the API.
     *
     * @param name The name of the recipe to retrieve.
     * @return A {@link Call} that can be executed to get a recipe by name.
     */
    @GET("recipe/get/{name}")
    Call<Recipe> getRecipeByName(@Path("name") String name);

    /**
     * Searches for recipes by name using a query parameter in the request.
     *
     * @param name The name to search for in recipes.
     * @return A {@link Call} that can be executed to search for recipes by name.
     */
    @GET("/recipe/searchByName")
    Call<List<Recipe>> searchRecipesByName(@Query("name") String name);

    /**
     * Searches for recipes by ingredient name using a query parameter in the request.
     *
     * @param ingredientName The ingredient name to search for in recipes.
     * @return A {@link Call} that can be executed to search for recipes by ingredient name.
     */
    @GET("/recipe/searchByIngredient")
    Call<List<Recipe>> searchRecipesByIngredient(@Query("ingredientName") String ingredientName);

    @GET("/recipe/{recipeId}/ingredients")
    Call<List<Ingredient>> getIngredientsForRecipe(@Path("recipeId") Long recipeId);


}
