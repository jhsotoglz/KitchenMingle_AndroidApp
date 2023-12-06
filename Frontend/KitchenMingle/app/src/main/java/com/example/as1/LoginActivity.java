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
import com.example.as1.model.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;

import androidx.appcompat.app.AppCompatActivity;

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


//    Call<LoginResponse> unifedLogin(@Body LoginRequest loginRequest);
//    LoginApi loginApi = ApiClientFactory.GetLoginApi();
//
//    Call<String> call = loginApi.unifedRegister(newUser);
//
//        call.enqueue(new Callback<String>(){
    //   todo: it returns id and stuff, grab it and save intent for later
    /**
     * Sends a login request to the server and handles the response.
     * @param loginRequest the login request object containing necessary user credentials.
     */
    private void loginUser(LoginRequest loginRequest) {
        // Initializing retrofit service
//
//        Call<String> call = usersApi.login(loginRequest);
        LoginApi loginApi = ApiClientFactory.GetLoginApi();
        Call<String> call = loginApi.unifedLogin(loginRequest);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                progressBar.setProgress(50);
                Toast.makeText(LoginActivity.this, "Received a response...", Toast.LENGTH_SHORT).show();
                progressBar.setProgress(75);
                if (response.isSuccessful()) {   // response status code is between 200-299
                    // login successful, handle success
                    // below could be used to send userID to details once a getter is made
//                    Intent intent = new Intent(LoginActivity, DetailsActivity.class);
//                    intent.putExtra("USER_ID", loggedInUserId);
//                    startActivcity(intent);
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