package com.example.as1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HelloWorldActivity extends AppCompatActivity {

    Button backBtn;
    //TextView helloWorld;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helloworld);

        backBtn = findViewById(R.id.backBtn);




        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(HelloWorldActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }
}