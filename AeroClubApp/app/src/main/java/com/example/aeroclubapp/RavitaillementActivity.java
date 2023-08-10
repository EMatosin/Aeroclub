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

public class RavitaillementActivity extends AppCompatActivity {

    EditText quantityEditText;
    Button saveFuelButton;
    String fuelType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ravitaillement);

        quantityEditText = findViewById(R.id.fuelQuantity);
        saveFuelButton = findViewById(R.id.saveButtonFuel);

        Button buttonJETA1 = findViewById(R.id.buttonJETA1);
        Button buttonAVGAS = findViewById(R.id.buttonAVGAS);
        Button buttonJETA1noTIC = findViewById(R.id.buttonJETA1noTIC);
        Button buttonAVGASnoTIC = findViewById(R.id.buttonAVGASnoTIC);

        buttonJETA1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fuelType = "JETA1";
            }
        });

        buttonAVGAS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fuelType = "AVGAS";
            }
        });

        buttonJETA1noTIC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fuelType = "JETA1 Sans TIC";
            }
        });

        buttonAVGASnoTIC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fuelType = "AVGAS Sans TIC";
            }
        });

        saveFuelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveFuelData();
            }
        });
    }

    public void saveFuelData() {
        String quantity = quantityEditText.getText().toString();
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String title = sharedPreferences.getString("title", "");
        if (!quantity.isEmpty()) {

            DataClassUser fuelData = new DataClassUser(fuelType, quantity);

            DatabaseReference titleRef = FirebaseDatabase.getInstance().getReference("AeroClubUser").child(title);

            Map<String, Object> updateData = new HashMap<>();
            updateData.put("fuelType", fuelData.getFuelType());
            updateData.put("quantity", fuelData.getQuantity());

            titleRef.updateChildren(updateData).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(RavitaillementActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(RavitaillementActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


}