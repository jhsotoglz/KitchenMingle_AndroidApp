package com.example.as1;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.example.as1.api.*;
import com.example.as1.model.LoginRequest;
import com.example.as1.model.LoginResponse;
import com.example.as1.model.RegistrationRequest;
import com.example.as1.api.ApiClientFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;

import android.widget.RadioGroup;
import android.widget.RadioButton;

/**
 * SignUpActivity provides a UI for users to register for KitchenMingle.
 * User's enter in a username, password, and email and their input is validated.
 */
public class SignUpActivity extends AppCompatActivity {

    Button btnToMain, btnSignUp;
    EditText passwordEditText, usernameEditText, emailEditText, confirmPasswordEditText;
    ProgressBar progressBar;
    RadioGroup radioGroupUserType;
    RadioButton radioButtonUser, radioButtonAdmin, radioButtonContributor;
    private String userType;

    /**
     * Initializes the SignUpActivity when it is created.
     * Sets up UI elements such as buttons, edit text, and a progress bar.
     * @param savedInstanceState
     */
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
        radioGroupUserType = findViewById(R.id.radioGroupUserType);
        radioButtonUser = findViewById(R.id.radioButtonUser);
        radioButtonAdmin = findViewById(R.id.radioButtonAdmin);
        radioButtonContributor = findViewById(R.id.radioButtonContributor);

        radioGroupUserType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                switch (checkedId) {
                    case R.id.radioButtonUser:
                        userType = "User";
                        break;
                    case R.id.radioButtonAdmin:
                        userType = "Admin";
                        break;
                    case R.id.radioButtonContributor:
                        userType = "Editor";
                        break;
                }
            }
        });

        // Set up a click listener to go to main
        btnToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent return_intent = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(return_intent);
            }
        });

        // Set up a click listener for the register button
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                // Disable sign-up while validating
                btnSignUp.setEnabled(false);

                // Get user input from EditText
                String email = emailEditText.getText().toString();
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String passwordConfirm = confirmPasswordEditText.getText().toString();
                boolean valid = true;

                // Create new RegistrationRequest object with user input
                RegistrationRequest newUser = new RegistrationRequest(userType, email, password, username);
                newUser.setUsername(username);
                newUser.setUserType(userType);
                newUser.setEmail(email);
                newUser.setPassword(password);

                // Email validating indicator
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                if (!email.matches(emailPattern) || email.length() > 50) {
                    emailEditText.setError("Invalid email address");
                    valid = false;
                }

                // Password matching & requirement indicators
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

                // Checks if user type was selected
                int selectedUserTypeId = radioGroupUserType.getCheckedRadioButtonId();
                if (selectedUserTypeId == -1) {
                    radioGroupUserType.setBackgroundResource(R.drawable.ic_error);
                    valid = false;
                }

                if(valid) {
                    try {
                        progressBar.setVisibility(View.VISIBLE);
                        Toast.makeText(SignUpActivity.this, "Trying to register...", Toast.LENGTH_SHORT).show();
                        registerUser(newUser);
                        progressBar.setProgress(25);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    // Re-enable sign-up button
                    btnSignUp.setEnabled(true);
                }
            }
        });
    }

    /**
     * Sends a user registration request to the server and handles the response.
     * @param newUser The user registration request object containing user details.
     */

    private void registerUser(RegistrationRequest newUser){
        // Initializing retrofit service
        LoginApi loginApi = ApiClientFactory.GetLoginApi();

        Call<String> call = loginApi.unifiedRegister(newUser);

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
                    btnSignUp.setEnabled(true);
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