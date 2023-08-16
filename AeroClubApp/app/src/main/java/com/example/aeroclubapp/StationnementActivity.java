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

public class StationnementActivity extends AppCompatActivity {

    Button buttonTurbine, buttonReacteur, buttonWk, buttonSemaine,
            buttonBasé, buttonBaséMensuel, buttonAc1, buttonAc2, buttonAc3,
            buttonAc4, buttonAc5a, buttonAc5b, buttonJour, buttonNuit,
            buttonCat1, buttonCat2, buttonCat3, saveButton;

    String typeAvion, periode, groupeAcoustique, heureAtterrissage, categorieAvion;

    EditText lenghtEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stationnement);

        buttonTurbine = findViewById(R.id.buttonTurbine);
        buttonReacteur = findViewById(R.id.buttonReacteur);
        buttonWk = findViewById(R.id.buttonWk);
        buttonSemaine = findViewById(R.id.buttonSemaine);
        buttonBasé = findViewById(R.id.buttonBasé);
        buttonBaséMensuel = findViewById(R.id.buttonBaséMensuel);
        buttonAc1 = findViewById(R.id.buttonAc1);
        buttonAc2 = findViewById(R.id.buttonAc2);
        buttonAc3 = findViewById(R.id.buttonAc3);
        buttonAc4 = findViewById(R.id.buttonAc4);
        buttonAc5a = findViewById(R.id.buttonAc5a);
        buttonAc5b = findViewById(R.id.buttonAc5b);
        buttonJour = findViewById(R.id.buttonJour);
        buttonNuit = findViewById(R.id.buttonNuit);
        lenghtEditText = findViewById(R.id.lenght);
        buttonCat1 = findViewById(R.id.buttonCat1);
        buttonCat2 = findViewById(R.id.buttonCat2);
        buttonCat3 = findViewById(R.id.buttonCat3);
        saveButton = findViewById(R.id.saveButtonStationnement);


        buttonTurbine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                typeAvion = "Mono/Bi turbine";
                Toast.makeText(StationnementActivity.this, "Mono/Bi turbine choisi", Toast.LENGTH_SHORT).show();

            }
        });

        buttonReacteur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                typeAvion = "Réacteur mono/multi";
                Toast.makeText(StationnementActivity.this, "Réacteur mono/multi choisi", Toast.LENGTH_SHORT).show();

            }
        });

        buttonWk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                periode = "Week-end/JF(non basé)";
                Toast.makeText(StationnementActivity.this, "Week-end/JF(non basé) choisi", Toast.LENGTH_SHORT).show();

            }
        });

        buttonSemaine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                periode = "Semaine(non basé)";
                Toast.makeText(StationnementActivity.this, "Semaine(non basé) choisi", Toast.LENGTH_SHORT).show();

            }
        });

        buttonBasé.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                periode = "Avion basé(mensuel)";
                Toast.makeText(StationnementActivity.this, "Avion basé(mensuel) choisi", Toast.LENGTH_SHORT).show();

            }
        });

        buttonBaséMensuel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                periode = "Avion basé (unité)";
                Toast.makeText(StationnementActivity.this, "Avion basé (unité) choisi", Toast.LENGTH_SHORT).show();

            }
        });

        buttonAc1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                groupeAcoustique = "1";
                Toast.makeText(StationnementActivity.this, "1 choisi", Toast.LENGTH_SHORT).show();

            }
        });

        buttonAc2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                groupeAcoustique = "2";
                Toast.makeText(StationnementActivity.this, "2 choisi", Toast.LENGTH_SHORT).show();

            }
        });

        buttonAc3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                groupeAcoustique = "3";
                Toast.makeText(StationnementActivity.this, "3 choisi", Toast.LENGTH_SHORT).show();

            }
        });

        buttonAc4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                groupeAcoustique = "4";
                Toast.makeText(StationnementActivity.this, "4 choisi", Toast.LENGTH_SHORT).show();

            }
        });

        buttonAc5a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                groupeAcoustique = "5a";
                Toast.makeText(StationnementActivity.this, "5a choisi", Toast.LENGTH_SHORT).show();

            }
        });

        buttonAc5b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                groupeAcoustique = "5b";
                Toast.makeText(StationnementActivity.this, "5b choisi", Toast.LENGTH_SHORT).show();

            }
        });

        buttonJour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                heureAtterrissage = "Jour et soir (6h00-22h00)";
                Toast.makeText(StationnementActivity.this, "Jour et soir (6h00-22h00) choisi", Toast.LENGTH_SHORT).show();

            }
        });

        buttonNuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                heureAtterrissage = "Nuit (22h00-6h00)";
                Toast.makeText(StationnementActivity.this, "Nuit (22h00-6h00) choisi", Toast.LENGTH_SHORT).show();

            }
        });

        buttonCat1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categorieAvion = "Cat 1";
                Toast.makeText(StationnementActivity.this, "Cat 1 choisi", Toast.LENGTH_SHORT).show();

            }
        });

        buttonCat2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categorieAvion = "Cat 2";
                Toast.makeText(StationnementActivity.this, "Cat 2 choisi", Toast.LENGTH_SHORT).show();

            }
        });

        buttonCat3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categorieAvion = "Cat 3";
                Toast.makeText(StationnementActivity.this, "Cat 3 choisi", Toast.LENGTH_SHORT).show();

            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });

    }

    private void saveData() {
        String surfaceSol = lenghtEditText.getText().toString();

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String title = sharedPreferences.getString("title", "");

        DatabaseReference titleRef = FirebaseDatabase.getInstance().getReference("AeroClubUser").child(title);

        Map<String, Object> updateData = new HashMap<>();
        updateData.put("typeAvion", typeAvion);
        updateData.put("periode", periode);
        updateData.put("groupeAcoustique", groupeAcoustique);
        updateData.put("heureAtterrissage", heureAtterrissage);
        updateData.put("categorieAvion", categorieAvion);
        updateData.put("surfaceSol", surfaceSol);

        titleRef.updateChildren(updateData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(StationnementActivity.this, "Enregistré !", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(StationnementActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
