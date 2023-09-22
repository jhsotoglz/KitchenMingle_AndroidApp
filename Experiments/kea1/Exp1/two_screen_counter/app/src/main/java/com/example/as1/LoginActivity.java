package com.example.as1;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;

import android.os.Bundle;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    public void goBack(View view) {
        // Your code to handle the button click goes here
        // This method is called when the button is clicked
        onBackPressed(); // Example: Navigating back
    }


}