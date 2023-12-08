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
import androidx.appcompat.app.AppCompatActivity;


import com.example.as1.api.LoginApi;
import com.example.as1.api.ApiClientFactory;
import com.example.as1.model.LoginRequest;
import com.example.as1.model.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.google.gson.Gson;

/**
 * LoginActivity provides a user interface for users to log in with their email and password.
 */
public class LoginActivity extends AppCompatActivity {

    Button btnLogin, btnToMain;
    EditText emailEditText, passwordEditText;
    ProgressBar progressBar;

    /**
     * Initializes LoginActivity when it's created.
     * Sets up UI features such as button clicks, edit text, and a progress bar.
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btnLogin);
        btnToMain = findViewById(R.id.btnToMain);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        progressBar = findViewById(R.id.progressBar);

        // Set up a click listener to go to main
        btnToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // Set up a click listener for the "Login" button
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get user input from EditText
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                // Create new user object
                LoginRequest currentUser = new LoginRequest();
                currentUser.setEmail(email);
                currentUser.setPassword(password);

                try {
                    progressBar.setVisibility(View.VISIBLE);
                    Toast.makeText(LoginActivity.this, "Trying to log in...", Toast.LENGTH_SHORT).show();
                    progressBar.setProgress(25);
                    loginUser(currentUser);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //   todo: it returns id and stuff, grab it and save intent for later
    /**
     * Sends a login request to the server and handles the response.
     * @param currentUser is the login request object containing necessary user credentials.
     */
    private void loginUser(LoginRequest currentUser) {
        // Initializing retrofit service
        LoginApi loginApi = ApiClientFactory.GetLoginApi();

        Call<LoginResponse> call = loginApi.unifiedLogin(currentUser);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
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

                    // get user ID from response
                    LoginResponse loginResponse = response.body();
                    Long userId = loginResponse.getUserId();

                    // Take the user to their Pantry
                    Intent intent = new Intent(LoginActivity.this, MyPantryActivity.class);
                    intent.putExtra("USER_ID", userId);
                    startActivity(intent);
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
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                // handle network error on request failure
            }
        });
    }
}