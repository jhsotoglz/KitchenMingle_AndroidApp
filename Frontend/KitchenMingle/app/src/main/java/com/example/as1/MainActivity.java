package com.example.as1;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Initializes the MainActivity when it's created.
 * Sets up UI and handles button clicks to navigate to different sections of the app upon startup.
 */
public class MainActivity extends AppCompatActivity {
    Button btnToLogin, btnToSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnToLogin = findViewById(R.id.btnToLogin);
        btnToSignUp = findViewById(R.id.btnToSignUp);

        // Set up a click listener for the "Login" button"
        btnToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        // Set up a click listener for the "Sign Up" button
        btnToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}