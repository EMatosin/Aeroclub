package com.example.aeroclubapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.aeroclubapp.databinding.ActivityDetailedAeroclubBinding;

public class DetailedAeroclubActivity extends AppCompatActivity {

    private Button continueButton;
    ActivityDetailedAeroclubBinding binding2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding2 = ActivityDetailedAeroclubBinding.inflate(getLayoutInflater());
        setContentView(binding2.getRoot());
        continueButton = findViewById(R.id.continueButton);
        Intent intent = this.getIntent();
        if (intent != null){
            String name = intent.getStringExtra("name");
            int desc = intent.getIntExtra("desc", R.string.ravitaillement);
            int image = intent.getIntExtra("image", R.drawable.ravitaillement);
            binding2.detailName.setText(name);
            binding2.detailDesc.setText(desc);
            binding2.detailImage.setImageResource(image);

            Log.d("DetailedAeroclubActivity", "Value of name: " + name);


            continueButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent interfaceActivityIntent = null;

                    if ("ULM".equals(name)){
                        interfaceActivityIntent = new Intent(DetailedAeroclubActivity.this, ULMActivity.class);
                    }

                    startActivity(interfaceActivityIntent);
                    finish();

                }
            });
        }
    }
}