package com.example.as1;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class SignUpActivity extends AppCompatActivity {

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