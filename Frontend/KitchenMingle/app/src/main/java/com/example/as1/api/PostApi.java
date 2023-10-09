package com.example.as1.api;

import com.example.as1.model.Post;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PostApi{

    @GET("posts/1")
    Call<Post> getFirstPost();
}
