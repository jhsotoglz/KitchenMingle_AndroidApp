package com.example.as1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CounterActivity extends AppCompatActivity {

    Button increaseBtn;
    Button backBtn;
    Button nextBtn;
    TextView numberTxt;

    int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);

        increaseBtn = findViewById(R.id.increaseBtn);
        backBtn = findViewById(R.id.backBtn);               // to main page
        nextBtn = findViewById(R.id.nextBtn);               // to decrement page
        numberTxt = findViewById(R.id.number);

        increaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                numberTxt.setText(String.valueOf(++counter));
            }
        });


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent return_intent = new Intent(CounterActivity.this, MainActivity.class);
                startActivity(return_intent);
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(CounterActivity.this, DecrementActivity.class);
                intent.putExtra("CounterValue", counter);
                startActivity(intent);
            }
        });


    }
}