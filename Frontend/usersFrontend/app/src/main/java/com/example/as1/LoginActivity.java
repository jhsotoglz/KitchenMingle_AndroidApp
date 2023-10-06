package com.example.as1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import com.example.as1.api.Users;
import com.example.as1.api.UsersApi;
import com.example.as1.api.ApiClientFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin, btnToMain;
    EditText emailEditText, passwordEditText;

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
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get user input from EditText
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                // create new user object
                Users loginUser = new Users();
                loginUser.setEmail(email);
                loginUser.setPassword(password);

                try {
                    loginUser(loginUser);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private void loginUser(Users loginUser) {
        UsersApi usersApi = ApiClientFactory.GetUsersApi(); // initializing retrofit service

        Call<Users> call = usersApi.login(loginUser);

        call.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                if (response.isSuccessful()) {
                    // login successful, handle success
                    // Todo: take user to home page
                } else {
                    // login failed, handle failure
                    Toast.makeText(LoginActivity.this, "Login Failed, check your email and password", Toast.LENGTH_SHORT).show();
                    ShapeDrawable shapeDrawable = new ShapeDrawable(new RectShape());
                    shapeDrawable.getPaint().setColor(getResources().getColor(android.R.color.holo_red_light));
                    shapeDrawable.getPaint().setStrokeWidth(5f); // border width
                    emailEditText.setBackground(shapeDrawable);
                    passwordEditText.setBackground(shapeDrawable);
                }
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                // handle network error on request failure
            }
        });
    }
}