package com.example.aeroclubapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.aeroclubapp.databinding.ActivityDetailedBasicServiceBinding;
import com.example.aeroclubapp.databinding.ActivityMainBinding;

public class DetailedBasicServiceActivity extends AppCompatActivity {

    private Button continueButton;
    ActivityDetailedBasicServiceBinding binding2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding2 = ActivityDetailedBasicServiceBinding.inflate(getLayoutInflater());
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

            Log.d("DetailedBasicService", "Value of name: " + name); // Ligne de log placée ici

            continueButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent interfaceActivityIntent = null;
                    // Supprimez la ligne suivante, car vous avez déjà déclaré la variable 'name' plus haut
                    // String name = getIntent().getStringExtra("name");

                    if ("Ravitaillement".equals(name)) {
                        interfaceActivityIntent = new Intent(DetailedBasicServiceActivity.this, RavitaillementActivity.class);
                    } else if ("Stationnement".equals(name)) {
                        interfaceActivityIntent = new Intent(DetailedBasicServiceActivity.this, StationnementActivity.class);
                    } else if ("Nettoyage intérieur".equals(name)) {
                        interfaceActivityIntent = new Intent(DetailedBasicServiceActivity.this, NettoyageActivity.class);
                    } else if ("Informations météorologiques".equals(name)) {
                        interfaceActivityIntent = new Intent(DetailedBasicServiceActivity.this, MeteoActivity.class);
                    }

                    startActivity(interfaceActivityIntent);
                    finish();
                }
            });
        }
    }
}