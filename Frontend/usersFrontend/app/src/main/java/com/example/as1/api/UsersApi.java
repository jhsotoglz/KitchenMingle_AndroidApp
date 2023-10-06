package com.example.as1.api;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.PUT;
import retrofit2.http.DELETE;

public interface UsersApi {
    @GET("users")
    Call<List<Users>> getAllUsers();

    @GET("user/{id}")
    Call<Users> getUser(@Path("id") Long id);

    @PUT("users/put/{id}")
    Call<Users> updateUser(@Path("id") Long id, @Body Users newInfo);

    @DELETE("users/delete/{id}")
    Call<Void> deleteUser(@Path("id") Long id);

    @GET("users/login")
    Call<String> login(@Body Users loginUser);

    @POST("users/register")
    Call<String> RegisterUsers(@Body Users newUser);

    @GET("users/getEmail/{email}")
    Call<Users> GetUsersByEmail(@Path("email") String email);
}
