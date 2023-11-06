package com.example.as1.api;
import com.example.as1.api.IngredientApi;
import com.example.as1.api.PostApi;
import com.example.as1.api.UsersApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClientFactory {

    static Retrofit apiClientSeed = null;

    static Retrofit GetApiClientSeed() {

        if (apiClientSeed == null) {
            apiClientSeed = new Retrofit.Builder()
                    .baseUrl("http://coms-309-033.class.las.iastate.edu:8080")
                  //  .baseUrl("http://10.0.2.2:8080/") // emulator
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return apiClientSeed;
    }


    public static PostApi GetPostApi(){

        return GetApiClientSeed().create(PostApi.class);
    }

    public static UsersApi GetUsersApi(){

        return GetApiClientSeed().create(UsersApi.class);
    }

    public static IngredientApi GetIngredientAPI(){
        return GetApiClientSeed().create(IngredientApi.class);
    }


    public static RecipeApi GetRecipeAPI(){
        return GetApiClientSeed().create(RecipeApi.class);
    }

    public static PantryIngredientApi GetPantryIngredientAPI() { return GetApiClientSeed().create(PantryIngredientApi.class);}

}
