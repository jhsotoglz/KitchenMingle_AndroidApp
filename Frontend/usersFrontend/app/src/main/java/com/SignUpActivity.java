package com;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



public class SignUpActivity extends AppCompatActivity {

    Button btnToMain, btnSignUp;
    EditText passwordEditText, usernameEditText, emailEditText, confirmPasswordEditText;

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
                String email = emailEditText.getText().toString();
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String passwordConfirm = confirmPasswordEditText.getText().toString();
                boolean valid = true;

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
                    btnSignUp.setEnabled(true);
                    try {
                        String response = StringRequestActivity.sendSignUpRequest(email, username, password);
                        if (response.startsWith("User registered successfully")){
                            Toast.makeText(SignUpActivity.this, response, Toast.LENGTH_SHORT).show();
                            // Todo: take user to home page
                        } else {
                            Toast.makeText(SignUpActivity.this, response, Toast.LENGTH_SHORT).show();
                            valid = false;
                            // Todo: make button to login saying "did you mean to sign in?"
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    btnSignUp.setEnabled(false);
                }
            }
        });
    }
}