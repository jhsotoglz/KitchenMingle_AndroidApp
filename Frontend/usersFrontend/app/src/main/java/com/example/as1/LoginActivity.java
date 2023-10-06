package com.example.as1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin, btnToMain;
    EditText emailEditText,passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btnLogin);
        btnToMain = findViewById(R.id.btnToMain);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);

        btnToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
               String email = emailEditText.getText().toString();
               String password = passwordEditText.getText().toString();
               Log.d("LoginActivity", "Log In button clicked");   // TODO: remove, for debugging only
                try {
                    String response = StringRequestActivity.sendLoginRequest(email, password);
                    // Process the response as needed
                    if (response.startsWith("Login successful")){
                        Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();
                        // TODO: Navigate to home page, temp: success message
                    } else {
                        // Show error message
                        Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();
                        // Change color of input fields to red
                        ShapeDrawable shapeDrawable = new ShapeDrawable(new RectShape());
                        shapeDrawable.getPaint().setColor(getResources().getColor(android.R.color.holo_red_light));
                        shapeDrawable.getPaint().setStrokeWidth(5f); // border width
                        emailEditText.setBackground(shapeDrawable);
                        passwordEditText.setBackground(shapeDrawable);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
