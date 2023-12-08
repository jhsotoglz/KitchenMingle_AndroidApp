package com.example.as1.api;

import android.content.SharedPreferences;

import com.example.as1.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface EditorApi {

    @POST("recipe/post/{name}/{instructions}")
    Call<Recipe> postRecipeByPath(
            @Path("name") String name,
            @Path("instructions") String instructions
    );

    @POST("recipe/post/{editorId}")
    Call<Recipe> postRecipeByBody(
            @Path("editorId") Long editorId,
            @Body Recipe newRecipe
    );

    @DELETE("recipe/delete/{id}")
    Call<Void> deleteRecipeById(
            @Path("id") Long id,
            @Query("editorId") Long editorId
    );

    @POST("recipe/post/with-ingredients/{editorId}")
    Call<Recipe> createRecipeWithIngredients(
            @Path("editorId") Long editorId,
            @Body Recipe newRecipe,
            @Query("ingredientIds") List<Long> ingredientIds
    );

    @POST("recipe/{recipeId}/associateIngredient/{editorId}/{ingredientId}")
    Call<Recipe> associateIngredientWithRecipe(
            @Path("recipeId") Long recipeId,
            @Path("editorId") Long editorId,
            @Path("ingredientId") Long ingredientId
    );

    // Other methods for ingredients, editor, etc.

    @POST("editor/getEmail/{email}")
    Call<SharedPreferences.Editor> getEditorByEmail(@Path("email") String email);

    @PUT("editor/put/{id}")
    Call<SharedPreferences.Editor> updateEditor(@Path("id") Long id, @Body SharedPreferences.Editor newInfo);

}
