package test.connect.myapplication.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import test.connect.myapplication.model.Recipe;

public interface RecipeApi {

    @GET("recipe/all")
    Call<List<Recipe>> GetAllRecipes();

    @POST("recipe/post/{name}/{instructions}")
    Call<Recipe> PostRecipeByPath(@Path("name") String name,@Path("instructions") String instructions);

    @POST("recipe/post")
    Call<Recipe> PostRecipeByBody(@Body Recipe newRecipe);

}
