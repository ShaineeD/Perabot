package com.example.perabot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Payment3 extends AppCompatActivity {
    private Button confirmOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment3);

        confirmOrder = (Button) findViewById(R.id.confirmOrder);
        confirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPayment4();
            }
        });
    }

    public void openPayment4(){
        Intent intent = new Intent(this,Payment4.class);
        startActivity(intent);
    }
}
