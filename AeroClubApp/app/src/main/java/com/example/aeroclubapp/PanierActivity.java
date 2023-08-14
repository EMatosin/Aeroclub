package com.example.aeroclubapp;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PanierActivity extends AppCompatActivity {

    private TextView displayTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panier);

        displayTextView = findViewById(R.id.displayTextView);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String title = sharedPreferences.getString("title", "");

        DatabaseReference aeroclubUserRef = FirebaseDatabase.getInstance().getReference("AeroClubUser").child(title);

        aeroclubUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String nom = dataSnapshot.child("name").getValue(String.class);
                String dateNaissance = dataSnapshot.child("birth").getValue(String.class);
                String email = dataSnapshot.child("dataEmail").getValue(String.class);
                String emailSecond = dataSnapshot.child("email_second").getValue(String.class);
                String typeAvionAtterissage = dataSnapshot.child("typeAvion").getValue(String.class);
                String periodeAtterissage = dataSnapshot.child("periode").getValue(String.class);
                String groupeAcoustique = dataSnapshot.child("groupeAcoustique").getValue(String.class);
                String heureAtterissage = dataSnapshot.child("heureAtterissage").getValue(String.class);
                String surfaceStationnement = dataSnapshot.child("surfaceSol").getValue(String.class);
                String tarifAbri = dataSnapshot.child("date_bapteme_air").getValue(String.class);
                String categorieAbri = dataSnapshot.child("categorieAvion").getValue(String.class);
                String typeRavitaillement = dataSnapshot.child("fuelType").getValue(String.class);
                String litre = dataSnapshot.child("quantity").getValue(String.class);
                String dateParachutisme = dataSnapshot.child("date_parachutisme").getValue(String.class);
                String forfaitParachutisme = dataSnapshot.child("forfait_parachutisme").getValue(String.class);
                String optionParachutisme = dataSnapshot.child("option_parachutisme").getValue(String.class);
                String datePiper = dataSnapshot.child("date_uml_piper").getValue(String.class);
                String dateRobin = dataSnapshot.child("date_uml_robin").getValue(String.class);
                String dateBaptemeAir = dataSnapshot.child("date_bapteme_air").getValue(String.class);
                String typeBrevet = dataSnapshot.child("licenceType").getValue(String.class);

                displayTextView.setText("Mail: " + email);

                if (nom != null) {
                    displayTextView.setText("Nom: " + nom);
                } else {
                    displayTextView.setText("Aucun nom renseigné.");
                }

                if (dateNaissance != null) {
                    displayTextView.setText(displayTextView.getText() + "\n\nDate de naissance: " + dateNaissance);
                } else {
                    displayTextView.setText(displayTextView.getText() + "\n\nAucune date de naissance renseignée.");
                }

                if (emailSecond != null) {
                    displayTextView.setText(displayTextView.getText() + "\n\nEmail secondaire: " + email);
                } else {
                    displayTextView.setText(displayTextView.getText() + "\n\nAucun email secondaire renseigné.");
                }

                if (surfaceStationnement != null) {
                    displayTextView.setText(displayTextView.getText() + "\n\nSurface de stationnement: " + surfaceStationnement);
                } else {
                    displayTextView.setText(displayTextView.getText() + "\n\nAucune surface de stationnement renseignée.");
                }

                if (tarifAbri != null) {
                    displayTextView.setText(displayTextView.getText() + "\n\nTarif d'abri: " + tarifAbri);
                } else {
                    displayTextView.setText(displayTextView.getText() + "\n\nAucun tarif d'abri renseigné.");
                }

                if (categorieAbri != null) {
                    displayTextView.setText(displayTextView.getText() + "\n\nCatégorie d'abri: " + categorieAbri);
                } else {
                    displayTextView.setText(displayTextView.getText() + "\n\nAucune catégorie d'abri renseignée.");
                }

                if (datePiper != null) {
                    displayTextView.setText(displayTextView.getText() + "\n\nDate UML Piper: " + datePiper);
                } else {
                    displayTextView.setText(displayTextView.getText() + "\n\nAucune date de location pour l'UML Piper renseignée.");
                }

                if (dateRobin != null) {
                    displayTextView.setText(displayTextView.getText() + "\n\nDate UML Robin: " + dateRobin);
                } else {
                    displayTextView.setText(displayTextView.getText() + "\n\nAucune date de location pour l'UML Robin renseignée.");
                }

                if (dateBaptemeAir != null) {
                    displayTextView.setText(displayTextView.getText() + "\n\nDate pour le baptême de l'air: " + dateBaptemeAir);
                } else {
                    displayTextView.setText(displayTextView.getText() + "\n\nAucune date pour un baptême de l'air renseignée.");
                }

                if (typeBrevet != null) {
                    displayTextView.setText(displayTextView.getText() + "\n\nType de brevet: " + typeBrevet);
                } else {
                    displayTextView.setText(displayTextView.getText() + "\n\nAucun type de brevet renseigné.");
                }

                if (typeAvionAtterissage != null) {
                    displayTextView.setText(displayTextView.getText() + "\n\nType d'avion d'atterrissage: " + typeAvionAtterissage);
                } else {
                    displayTextView.setText(displayTextView.getText() + "\n\nAucun type d'avion à l'atterrissage renseigné.");
                }

                if (periodeAtterissage != null) {
                    displayTextView.setText(displayTextView.getText() + "\n\nPériode d'atterrissage: " + periodeAtterissage);
                } else {
                    displayTextView.setText(displayTextView.getText() + "\n\nAucune période d'atterrissage renseignée.");
                }

                if (groupeAcoustique != null) {
                    displayTextView.setText(displayTextView.getText() + "\n\nGroupe acoustique: " + groupeAcoustique);
                } else {
                    displayTextView.setText(displayTextView.getText() + "\n\nAucun groupe acoustique renseigné.");
                }

                if (heureAtterissage != null) {
                    displayTextView.setText(displayTextView.getText() + "\n\nHeure d'atterrissage: " + heureAtterissage);
                } else {
                    displayTextView.setText(displayTextView.getText() + "\n\nAucune heure d'atterrissage renseignée.");
                }

                if (typeRavitaillement != null) {
                    displayTextView.setText(displayTextView.getText() + "\n\nType de ravitaillement: " + typeRavitaillement);
                } else {
                    displayTextView.setText(displayTextView.getText() + "\n\nAucun type de ravitaillement renseigné.");
                }

                if (litre != null) {
                    displayTextView.setText(displayTextView.getText() + "\n\nLitre: " + litre);
                } else {
                    displayTextView.setText(displayTextView.getText() + "\n\nAucun quantité de fuel renseigné.");
                }

                if (dateParachutisme != null) {
                    displayTextView.setText(displayTextView.getText() + "\n\nDate de parachutisme: " + dateParachutisme);
                } else {
                    displayTextView.setText(displayTextView.getText() + "\n\nAucune date de parachutisme renseignée.");
                }

                if (forfaitParachutisme != null) {
                    displayTextView.setText(displayTextView.getText() + "\n\nForfait de parachutisme: " + forfaitParachutisme);
                } else {
                    displayTextView.setText(displayTextView.getText() + "\n\nAucun forfait de parachutisme renseigné.");
                }

                if (optionParachutisme != null) {
                    displayTextView.setText(displayTextView.getText() + "\n\nOption de parachutisme: " + optionParachutisme);
                } else {
                    displayTextView.setText(displayTextView.getText() + "\n\nAucune option de parachutisme renseignée.");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
