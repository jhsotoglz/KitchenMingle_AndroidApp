package com;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
<<<<<<<< HEAD:Frontend/KitchenMingle/app/src/main/java/com/example/as1/SignUpActivity.java
import android.widget.ProgressBar;
import android.widget.Toast;
import com.example.as1.api.*;
import com.example.as1.api.ApiClientFactory;
import com.example.as1.model.Users;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
========
import android.widget.Toast;


>>>>>>>> 19-demo-2-frontend-login:Frontend/KitchenMingle/app/src/main/java/com/SignUpActivity.java

public class SignUpActivity extends AppCompatActivity {

    Button btnToMain, btnSignUp;
    EditText passwordEditText, usernameEditText, emailEditText, confirmPasswordEditText;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        btnToMain = findViewById(R.id.btnToMain);
        btnSignUp = findViewById(R.id.btnSignUp);
        emailEditText = findViewById(R.id.emailEditText);
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        progressBar = findViewById(R.id.progressBar);

        btnToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent return_intent = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(return_intent);
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                // disable sign-up while validating
                btnSignUp.setEnabled(false);

                // Get user input from EditText
                String email = emailEditText.getText().toString();
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String passwordConfirm = confirmPasswordEditText.getText().toString();
                boolean valid = true;

                // Create a Users object with user input
                Users newUser = new Users();
                newUser.setUsername(username);
                newUser.setEmail(email);
                newUser.setPassword(password);

                // Email validating indicator
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                if (!email.matches(emailPattern) || email.length() > 50) {
                    emailEditText.setError("Invalid email address");
                    valid = false;
                }

                // PW matching & requirement indicators
                if(!password.equals(passwordConfirm)){
                    confirmPasswordEditText.setError("Passwords must be the same");
                    valid = false;
                }
                if(password.length() > 50 || password.length() < 7){
                    passwordEditText.setError("Passwords must be between 7-50 characters");
                    valid = false;
                }

                // Username validation indicator
                if (username.length() > 20 || username.length() < 3 || !username.matches("[a-zA-Z0-9]+")) {
                    usernameEditText.setError("Username must be between 3-20 characters and contain only numbers and letters");
                    valid = false;
                }

                if(valid) {
                    try {
<<<<<<<< HEAD:Frontend/KitchenMingle/app/src/main/java/com/example/as1/SignUpActivity.java
                        progressBar.setVisibility(View.VISIBLE);
                        Toast.makeText(SignUpActivity.this, "Trying to register...", Toast.LENGTH_SHORT).show();
                        registerUser(newUser);
                        progressBar.setProgress(25);
========
                        String response = StringRequestActivity.sendSignUpRequest(email, username, password);
                        if (response.startsWith("User registered successfully")){
                            Toast.makeText(SignUpActivity.this, response, Toast.LENGTH_SHORT).show();
                            // Todo: take user to home page
                        } else {
                            Toast.makeText(SignUpActivity.this, response, Toast.LENGTH_SHORT).show();
                            valid = false;
                            // Todo: make button to login saying "did you mean to sign in?"
                        }
>>>>>>>> 19-demo-2-frontend-login:Frontend/KitchenMingle/app/src/main/java/com/SignUpActivity.java
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    // re-enable sign-up button
                    btnSignUp.setEnabled(true);
                }
            }
        });
    }

    private void registerUser(Users newUser){
        UsersApi usersApi = ApiClientFactory.GetUsersApi(); // initializing retrofit service

        Call<String> call = usersApi.RegisterUsers(newUser);

        call.enqueue(new Callback<String>(){
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                progressBar.setProgress(50);
                Toast.makeText(SignUpActivity.this, "Received a response...", Toast.LENGTH_SHORT).show();
                progressBar.setProgress(75);
                if (response.isSuccessful()) { // response status code between 200-299
                    // Registration successful, handle success
                    progressBar.setProgress(100);
                    Toast.makeText(SignUpActivity.this, "Successfully Registered! Please log in.", Toast.LENGTH_SHORT).show();
                    // Navigate to LoginActivity
                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    // registration failed, handle failure
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(SignUpActivity.this, "Did you mean to sign in?", Toast.LENGTH_SHORT).show();
                    // TODO: make button to login "did you mean to sign in?"
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t){
                progressBar.setVisibility(View.INVISIBLE);
                // handle network error on request failure
            }
        });
    }
}