package com.example.perabot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button btnShowOrderSummary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnShowOrderSummary = (Button) findViewById(R.id.btnShowOrderSummary);
        btnShowOrderSummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPayment1();
            }
        });
    }

    public void openPayment1(){
        Intent intent = new Intent(this,Payment1.class);
        startActivity(intent);
    }
}
