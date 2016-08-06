package com.example.dmoraes.mathtablesgame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Main2Activity extends AppCompatActivity {

    Button btnVoz;
    Button btnEscrita;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        btnVoz = (Button)findViewById(R.id.btnVoz);
        btnEscrita = (Button)findViewById(R.id.btnEscrita);

        btnVoz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main2Activity.this, VoiceActivity.class);
                startActivity(intent);
            }

        });

        btnEscrita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main2Activity.this, MainActivity.class);
                        startActivity(intent);
            }

        });
    }
}
