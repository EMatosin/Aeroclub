package com.example.aeroclubapp;

import androidx.appcompat.app.AppCompatActivity;

import android.nfc.TagLostException;
import android.os.Bundle;
import android.widget.TextView;

public class NavHeader extends AppCompatActivity {
    private TextView welcomeTextView;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_header);

        welcomeTextView = findViewById(R.id.welcomeTextView);
        username = getIntent().getStringExtra("username");

        welcomeTextView.setText(username);
    }
}