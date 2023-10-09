package com.example.as1.model;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.example.as1.api.LambdaInterface;

public class SlimCallback<T> implements Callback<T> {

    LambdaInterface<T> lambdaInterface;

    String logTag;



    public SlimCallback(LambdaInterface<T> lambdaInterface){
        this.lambdaInterface = lambdaInterface;
    }

    public SlimCallback(LambdaInterface<T> lambdaInterface, String customTag){
        this.lambdaInterface = lambdaInterface;
        this.logTag = customTag;
    }



    @Override
    public void onResponse(Call<T> call, Response<T> response) {

        if (response.isSuccessful()){
            lambdaInterface.doSomething(response.body());
        }
        else{
            Log.d(logTag, "Code:  "+response.code() + "    Msg:  "+response.message());
        }

    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {

        Log.d(logTag, "Thrown:  "+t.getMessage());

    }
}
