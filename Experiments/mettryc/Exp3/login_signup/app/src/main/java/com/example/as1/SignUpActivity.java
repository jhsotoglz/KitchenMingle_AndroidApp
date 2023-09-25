package com.example.as1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

                //if PW's don't match, change color of password fields & disable signup button
                if(!password.equals(passwordConfirm)){
                    passwordEditText.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                    confirmPasswordEditText.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                    btnSignUp.setEnabled(false);
                } else {
                    // if PW's match, remove indicator (if any) and enable the sign-up button
                    passwordEditText.setBackgroundColor(getResources().getColor(android.R.color.white));
                    confirmPasswordEditText.setBackgroundColor(getResources().getColor(android.R.color.white));
                    btnSignUp.setEnabled(true);
                }
            }
        });


    }
}