package com.ftiuksw.tugasmanpro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class activity_resep extends AppCompatActivity {

    private ImageButton btnHome, btnHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resep);
        btnHistory = findViewById(R.id.btnHistory);
        btnHome = findViewById(R.id.btnHome);

        btnHistory.bringToFront();
        btnHome.bringToFront();

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(com.ftiuksw.tugasmanpro.activity_resep.this, com.ftiuksw.tugasmanpro.MainActivity.class);
                startActivity(i);
            }
        });

        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(com.ftiuksw.tugasmanpro.activity_resep.this, activity_history.class);
                startActivity(i);
            }
        });
    }
}
