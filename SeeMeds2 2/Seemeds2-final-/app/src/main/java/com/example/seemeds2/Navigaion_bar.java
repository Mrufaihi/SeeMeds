package com.example.seemeds2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.seemeds2.Reminder;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class Navigaion_bar extends AppCompatActivity {

    private static final int REQUEST_CODE = 22;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigaion_bar); // Replace with your layout resource ID
        setupBottomNavigationView();
    }

    protected void setupBottomNavigationView() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);


        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                navigateToActivity(item.getItemId());
                return true;
            }
        });

        bottomNavigationView.setOnItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                navigateToActivity(item.getItemId());
            }
        });
    }

    protected void navigateToActivity(int itemId) {
        if (itemId == R.id.camera) {
              startActivity(new Intent(this, Camera.class));
        } else if (itemId == R.id.reminder) {
            startActivity(new Intent(this, Reminder.class));
        } else if (itemId == R.id.settings) {
            startActivity(new Intent(this, Settings.class));
        }
    }
}

//        شغل وسام
//
//        // Find references to the buttons in the navigation bar
//        Button btnCamera = findViewById(R.id.btnCamera);
//        Button btnReminder = findViewById(R.id.btnReminder);
//        Button btnSettings = findViewById(R.id.btnSettings);
//
//        // Set click listeners for the buttons
//        btnCamera.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(Navigaion_bar.this , Camera.class));
//                Toast.makeText(Navigaion_bar.this, "Camera button clicked", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        btnReminder.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(Navigaion_bar.this , Reminder.class));
//                Toast.makeText(Navigaion_bar.this, "Reminder button clicked", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        btnSettings.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(Navigaion_bar.this , Settings.class));
//                Toast.makeText(Navigaion_bar.this, "Settings button clicked", Toast.LENGTH_SHORT).show();
//            }
//        });
