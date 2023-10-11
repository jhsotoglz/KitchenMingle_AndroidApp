package com.example.as1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import com.example.as1.api.*;
import com.example.as1.api.ApiClientFactory;
import com.example.as1.model.LoginRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin, btnToMain;
    EditText emailEditText, passwordEditText;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btnLogin);
        btnToMain = findViewById(R.id.btnToMain);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        progressBar = findViewById(R.id.progressBar);


        btnToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
// todo check for input of nulls
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get user input from EditText
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                // create new user object
                LoginRequest loginRequest = new LoginRequest();
                loginRequest.setEmail(email);
                loginRequest.setPassword(password);

                try {
                    progressBar.setVisibility(View.VISIBLE);
                    Toast.makeText(LoginActivity.this, "Trying to log in...", Toast.LENGTH_SHORT).show();
                    progressBar.setProgress(25);
                    loginUser(loginRequest);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private void loginUser(LoginRequest loginRequest) {
        UsersApi usersApi = ApiClientFactory.GetUsersApi(); // initializing retrofit service

        Call<String> call = usersApi.login(loginRequest);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                progressBar.setProgress(50);
                Toast.makeText(LoginActivity.this, "Received a response...", Toast.LENGTH_SHORT).show();
                progressBar.setProgress(75);
                if (response.isSuccessful()) {   // response status code is between 200-299
                    // login successful, handle success
                    progressBar.setProgress(100);
                    Toast.makeText(LoginActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                    ShapeDrawable shapeDrawable = new ShapeDrawable(new RectShape());
                    shapeDrawable.getPaint().setColor(getResources().getColor(android.R.color.holo_green_light));
                    shapeDrawable.getPaint().setStrokeWidth(5f); // border width
                    emailEditText.setBackground(shapeDrawable);
                    passwordEditText.setBackground(shapeDrawable);
                    // Todo: take user to home page
                } else {
                    // login failed, handle failure
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(LoginActivity.this, "Login Failed, check your email and password", Toast.LENGTH_SHORT).show();
                    ShapeDrawable shapeDrawable = new ShapeDrawable(new RectShape());
                    shapeDrawable.getPaint().setColor(getResources().getColor(android.R.color.holo_red_light));
                    shapeDrawable.getPaint().setStrokeWidth(5f); // border width
                    emailEditText.setBackground(shapeDrawable);
                    passwordEditText.setBackground(shapeDrawable);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                // handle network error on request failure
            }
        });
    }
}