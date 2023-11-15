package com.example.as1.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * The ApiClientFactory class provides a factory for creating and accessing Retrofit API clients
 * for different API endpoints. It maintains a single Retrofit instance and provides methods to create
 * specific API interfaces for user management, ingredient handling, recipe operations, and more.
 *
 * The class also configures the base URL for the API endpoints and sets up Gson converter factory
 * for JSON serialization and deserialization.
 *
 * @since 1.0
 */
public class ApiClientFactory {

    /**
     * The Retrofit instance for making API requests.
     */
    static Retrofit apiClientSeed = null;

    /**
     * Retrieves the Retrofit instance used for making API requests. If the instance
     * does not exist, it creates one with the base URL and Gson converter factory.
     *
     * @return The Retrofit instance for making API requests.
     */
    static Retrofit GetApiClientSeed() {

        if (apiClientSeed == null) {
            apiClientSeed = new Retrofit.Builder()
                    .baseUrl("http://coms-309-033.class.las.iastate.edu:8080")
                  //  .baseUrl("http://10.0.2.2:8080/")  // emulator
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return apiClientSeed;
    }

    /**
     * Retrieves the API interface for managing user-related operations.
     *
     * @return The API interface for user-related operations.
     */
    public static UsersApi GetUsersApi(){

        return GetApiClientSeed().create(UsersApi.class);
    }

    /**
     * Retrieves the API interface for handling ingredient-related operations.
     *
     * @return The API interface for ingredient-related operations.
     */
    public static IngredientApi GetIngredientAPI(){
        return GetApiClientSeed().create(IngredientApi.class);
    }


    /**
     * Retrieves the API interface for managing recipe-related operations.
     *
     * @return The API interface for recipe-related operations.
     */
    public static RecipeApi GetRecipeAPI(){
        return GetApiClientSeed().create(RecipeApi.class);
    }

    /**
     * Retrieves the API interface for handling pantry ingredient-related operations.
     *
     * @return The API interface for pantry ingredient-related operations.
     */
    public static PantryIngredientApi GetPantryIngredientAPI() { return GetApiClientSeed().create(PantryIngredientApi.class);}

}
