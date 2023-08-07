package com.example.aeroclubapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.aeroclubapp.databinding.ActivityDetailedBasicServiceBinding;
import com.example.aeroclubapp.databinding.ActivityMainBinding;

public class DetailedBasicServiceActivity extends AppCompatActivity {

    ActivityDetailedBasicServiceBinding binding2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding2 = ActivityDetailedBasicServiceBinding.inflate(getLayoutInflater());
        setContentView(binding2.getRoot());
        Intent intent = this.getIntent();
        if (intent != null){
            String name = intent.getStringExtra("name");
            int desc = intent.getIntExtra("desc", R.string.ravitaillement);
            int image = intent.getIntExtra("image", R.drawable.ravitaillement);
            binding2.detailName.setText(name);
            binding2.detailDesc.setText(desc);
            binding2.detailImage.setImageResource(image);
        }
    }
}