package com.example.as1.api;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UsersApi {
    @GET("user/all")
    Call<List<Users>> GetAllUsers();

    @POST("user/post/{name}")
    Call<Users> PostUsersByPath(@Path("name") String name);

    @POST("user/post")
    Call<Users> PostUsersByBody(@Body Users newUser);
}
