package com.example.as1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DecrementActivity extends AppCompatActivity {

    Button decreaseBtn;
    Button toCounterBtn;
    TextView numberTxt;

    Intent intent = getIntent();
    int counter = intent.getIntExtra("CounterValue", 0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decrement);

        decreaseBtn = findViewById(R.id.decreaseBtn);
        toCounterBtn = findViewById(R.id.toCounter);
        numberTxt = findViewById(R.id.number);

        decreaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                numberTxt.setText(String.valueOf(--counter));
            }
        });


        toCounterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(DecrementActivity.this, CounterActivity.class);
                startActivity(intent);
            }
        });


    }
}