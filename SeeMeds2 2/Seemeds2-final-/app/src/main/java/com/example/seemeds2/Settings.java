package com.example.seemeds2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Settings extends Navigaion_bar {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setupBottomNavigationView();
    }

    }

