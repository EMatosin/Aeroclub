package com.example.aeroclubapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class PilotageActivity extends AppCompatActivity {

    Button saveLicenceButton;
    String licenceType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilotage);

        saveLicenceButton = findViewById(R.id.saveLicence);

        Button buttonBB = findViewById(R.id.buttonBB);
        Button buttonLAPL = findViewById(R.id.buttonLAPL);
        Button buttonPPL = findViewById(R.id.buttonPPL);

        buttonBB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                licenceType = "BB";
                Toast.makeText(PilotageActivity.this, "BB choisi", Toast.LENGTH_SHORT).show();

            }
        });

        buttonLAPL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                licenceType = "LAPL";
                Toast.makeText(PilotageActivity.this, "LAPL choisi", Toast.LENGTH_SHORT).show();

            }
        });

        buttonPPL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                licenceType = "PPL";
                Toast.makeText(PilotageActivity.this, "PPL choisi", Toast.LENGTH_SHORT).show();

            }
        });


        saveLicenceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveLicenceData();
            }

        });
    }

    public void saveLicenceData() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String title = sharedPreferences.getString("title", "");

        DatabaseReference titleRef = FirebaseDatabase.getInstance().getReference("AeroClubUser").child(title);

        Map<String, Object> updateData = new HashMap<>();
        updateData.put("licenceType", licenceType);

        titleRef.updateChildren(updateData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(PilotageActivity.this, "Enregistré !", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(PilotageActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }


}