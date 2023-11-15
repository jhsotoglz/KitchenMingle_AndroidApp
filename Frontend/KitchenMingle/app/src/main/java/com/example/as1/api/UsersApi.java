package com.example.as1.api;

import com.example.as1.model.*;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.PUT;
import retrofit2.http.DELETE;

/**
 * The UsersApi interface defines a set of HTTP RESTful API endpoints for managing user-related operations.
 * It provides methods for retrieving, updating, and deleting user information, as well as handling user authentication,
 * managing favorite recipes, and more.
 *
 * The API endpoints include operations such as:
 * - Getting a list of all users
 * - Retrieving a user by their unique ID
 * - Updating user information
 * - Deleting a user account
 * - User login
 * - Adding and removing favorite recipes for a user
 * - Retrieving a list of a user's favorite recipes
 * - Registering new users
 * - Retrieving a user by their email address
 *
 * This interface is used to generate HTTP requests to the server and receive corresponding responses.
 *
 * @see Users
 * @see LoginRequest
 * @see Recipe
 * @since 1.0
 */
public interface UsersApi {
    /**
     * Retrieves a list of all users from the API.
     *
     * @return A {@link Call} that can be executed to get a list of all users.
     */
    @GET("users")
    Call<List<Users>> getAllUsers();

    /**
     * Retrieves a user by their unique ID from the API.
     *
     * @param id The unique ID of the user to retrieve.
     * @return A {@link Call} that can be executed to get a user by ID.
     */
    @GET("user/{id}")
    Call<Users> getUser(@Path("id") Long id);

    /**
     * Updates user information using their unique ID and new user information.
     *
     * @param id      The unique ID of the user to update.
     * @param newInfo The new user information to replace the existing data.
     * @return A {@link Call} that can be executed to update user information.
     */
    @PUT("users/put/{id}")
    Call<Users> updateUser(@Path("id") Long id, @Body Users newInfo);

    /**
     * Deletes a user account using their unique ID.
     *
     * @param id The unique ID of the user to delete.
     * @return A {@link Call} that can be executed to delete a user account.
     */
    @DELETE("users/delete/{id}")
    Call<Void> deleteUser(@Path("id") Long id);

    /**
     * Performs user authentication by sending a login request with user credentials.
     *
     * @param loginRequest The login request containing user email and password.
     * @return A {@link Call} that can be executed to perform user login.
     */
    @POST("users/login")
    Call<String> login(@Body LoginRequest loginRequest);

    /**
     * Adds a recipe to a user's list of favorite recipes using user and recipe IDs.
     *
     * @param userId   The user's unique ID.
     * @param recipeId The recipe's unique ID to add as a favorite.
     * @return A {@link Call} that can be executed to add a favorite recipe.
     */
    @POST("users/{userId}/addFavRecipe/{recipeId}")
    Call<Void> addFavoriteRecipe(@Path("userId") Long userId, @Path("recipeId") Long recipeId);

    /**
     * Removes a recipe from a user's list of favorite recipes using user and recipe IDs.
     *
     * @param userId   The user's unique ID.
     * @param recipeId The recipe's unique ID to remove from favorites.
     * @return A {@link Call} that can be executed to remove a favorite recipe.
     */
    @DELETE("users/{userId}/removeFavRecipe/{recipeId}")
    Call<String> deleteFavoriteRecipe(@Path("userId") Long userId, @Path("recipeId") Long recipeId);

    /**
     * Retrieves a list of favorite recipes for a specific user using their unique ID.
     *
     * @param userId The user's unique ID.
     * @return A {@link Call} that can be executed to get a list of favorite recipes for the user.
     */
    @GET("users/{userId}/favRecipe")
    Call<List<Recipe>> getFavoriteRecipes(@Path("userId") Long userId);

    /**
     * Registers a new user by sending user registration information as a request body.
     *
     * @param newUser The new user information to register.
     * @return A {@link Call} that can be executed to register a new user.
     */
    @POST("users/register")
    Call<String> RegisterUsers(@Body Users newUser);

    /**
     * Retrieves a user by their email address from the API.
     *
     * @param email The email address of the user to retrieve.
     * @return A {@link Call} that can be executed to get a user by email.
     */
    @GET("users/getEmail/{email}")
    Call<Users> GetUsersByEmail(@Path("email") String email);


}
