package com.example.as1.api;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import com.example.as1.model.Recipe;

public interface RecipeApi {

    @GET("recipe/all")
    Call<List<Recipe>> GetAllRecipes();

    @POST("recipe/post/{name}/{instructions}")
    Call<Recipe> PostRecipeByPath(@Path("name") String name,@Path("instructions") String instructions);

    @POST("recipe/post")
    Call<Recipe> PostRecipeByBody(@Body Recipe newRecipe);

    @GET("recipe/get/{name}")
    Call<Recipe> getRecipeByName(@Path("name") String name);

    @GET("/recipe/searchByName")
    Call<List<Recipe>> searchRecipesByName(@Query("name") String name);

    @GET("/recipe/searchByIngredient")
    Call<List<Recipe>> searchRecipesByIngredient(@Query("ingredientName") String ingredientName);

}
