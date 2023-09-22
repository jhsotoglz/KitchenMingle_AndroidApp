package com.example.as1;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.EditText;

import android.os.Bundle;

public class UsernamePasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_username_password);


    }
    // Inside UsernamePasswordActivity.java
    EditText usernameEditText = findViewById(R.id.usernameEditText);
    EditText passwordEditText = findViewById(R.id.passwordEditText);

    // Retrieve user input
    String username = usernameEditText.getText().toString();
    String password = passwordEditText.getText().toString();

// Implement your validation or authentication logic here

}