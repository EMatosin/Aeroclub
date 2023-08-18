package com.example.aeroclubapp;


import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

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
            @SuppressLint("SetTextI18n")
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

                String datesHeuresBapteme = "";

                if (dataSnapshot.hasChild("date_bapteme_air")) {
                    HashMap<String, HashMap<String, Object>> BaptemeData = dataSnapshot.child("date_bapteme_air").getValue(new GenericTypeIndicator<HashMap<String, HashMap<String, Object>>>() {});


                    for (Map.Entry<String, HashMap<String, Object>> entry : BaptemeData.entrySet()) {
                        String dateKey = entry.getKey();
                        HashMap<String, Object> dateData = entry.getValue();

                        if (dateData != null) {
                            int heureDebut = ((Long) dateData.get("heureDebut")).intValue();
                            int heureFin = ((Long) dateData.get("heureFin")).intValue();

                            datesHeuresBapteme += "Date: " + dateKey + "\n";
                            datesHeuresBapteme += "Heure de début: " + heureDebut + "h\n";
                            datesHeuresBapteme += "Heure de fin: " + heureFin + "h\n\n";
                        }
                    }

                    if (!datesHeuresBapteme.isEmpty()) {
                        datesHeuresBapteme = "\n\nDates et heures bapteme de l'air:\n" + datesHeuresBapteme;
                    } else {
                        datesHeuresBapteme = "\n\nAucune date de bapteme de l'air prévue.";
                    }
                } else {
                    datesHeuresBapteme = "\n\nAucune date de bapteme de l'air prévue.";
                }



                String datesHeuresBB = "";

                if (dataSnapshot.hasChild("date_BB")) {
                    HashMap<String, HashMap<String, Object>> BBData = dataSnapshot.child("date_BB").getValue(new GenericTypeIndicator<HashMap<String, HashMap<String, Object>>>() {});


                    for (Map.Entry<String, HashMap<String, Object>> entry : BBData.entrySet()) {
                        String dateKey = entry.getKey();
                        HashMap<String, Object> dateData = entry.getValue();

                        if (dateData != null) {
                            int heureDebut = ((Long) dateData.get("heureDebut")).intValue();
                            int heureFin = ((Long) dateData.get("heureFin")).intValue();

                            datesHeuresBB += "Date: " + dateKey + "\n";
                            datesHeuresBB += "Heure de début: " + heureDebut + "h\n";
                            datesHeuresBB += "Heure de fin: " + heureFin + "h\n\n";
                        }
                    }

                    if (!datesHeuresBB.isEmpty()) {
                        datesHeuresBB = "\n\nDates et heures BB:\n" + datesHeuresBB;
                    } else {
                        datesHeuresBB = "\n\nAucune date de BB prévue.";
                    }
                } else {
                    datesHeuresBB = "\n\nAucune date de BB prévue.";
                }




                String datesHeuresLAPL = "";

                if (dataSnapshot.hasChild("date_LAPL")) {
                    HashMap<String, HashMap<String, Object>> LAPLData = dataSnapshot.child("date_LAPL").getValue(new GenericTypeIndicator<HashMap<String, HashMap<String, Object>>>() {});


                    for (Map.Entry<String, HashMap<String, Object>> entry : LAPLData.entrySet()) {
                        String dateKey = entry.getKey();
                        HashMap<String, Object> dateData = entry.getValue();

                        if (dateData != null) {
                            int heureDebut = ((Long) dateData.get("heureDebut")).intValue();
                            int heureFin = ((Long) dateData.get("heureFin")).intValue();

                            datesHeuresLAPL += "Date: " + dateKey + "\n";
                            datesHeuresLAPL += "Heure de début: " + heureDebut + "h\n";
                            datesHeuresLAPL += "Heure de fin: " + heureFin + "h\n\n";
                        }
                    }

                    if (!datesHeuresLAPL.isEmpty()) {
                        datesHeuresLAPL = "\n\nDates et heures LAPL:\n" + datesHeuresLAPL;
                    } else {
                        datesHeuresLAPL = "\n\nAucune date de LAPL prévue.";
                    }
                } else {
                    datesHeuresLAPL = "\n\nAucune date de LAPL prévue.";
                }




                String datesHeuresPPL = "";

                if (dataSnapshot.hasChild("date_PPL")) {
                    HashMap<String, HashMap<String, Object>> PPLData = dataSnapshot.child("date_PPL").getValue(new GenericTypeIndicator<HashMap<String, HashMap<String, Object>>>() {});


                    for (Map.Entry<String, HashMap<String, Object>> entry : PPLData.entrySet()) {
                        String dateKey = entry.getKey();
                        HashMap<String, Object> dateData = entry.getValue();

                        if (dateData != null) {
                            int heureDebut = ((Long) dateData.get("heureDebut")).intValue();
                            int heureFin = ((Long) dateData.get("heureFin")).intValue();

                            datesHeuresPPL += "Date: " + dateKey + "\n";
                            datesHeuresPPL += "Heure de début: " + heureDebut + "h\n";
                            datesHeuresPPL += "Heure de fin: " + heureFin + "h\n\n";
                        }
                    }

                    if (!datesHeuresPPL.isEmpty()) {
                        datesHeuresPPL = "\n\nDates et heures LAPL:\n" + datesHeuresPPL;
                    } else {
                        datesHeuresPPL= "\n\nAucune date de PPL prévue.";
                    }
                } else {
                    datesHeuresPPL = "\n\nAucune date de PPL prévue.";
                }




                String categorieAbri = dataSnapshot.child("categorieAvion").getValue(String.class);
                String typeRavitaillement = dataSnapshot.child("fuelType").getValue(String.class);
                String litre = dataSnapshot.child("quantity").getValue(String.class);
                String dateParachutisme = dataSnapshot.child("date_parachutisme").getValue(String.class);
                String forfaitParachutisme = dataSnapshot.child("forfait_parachutisme").getValue(String.class);
                String optionParachutisme = dataSnapshot.child("option_parachutisme").getValue(String.class);

                String datesHeuresAutogire = "";

                if (dataSnapshot.hasChild("date_uml_autogire")) {
                    HashMap<String, HashMap<String, Object>> autogireData = dataSnapshot.child("date_uml_autogire").getValue(new GenericTypeIndicator<HashMap<String, HashMap<String, Object>>>() {});


                    for (Map.Entry<String, HashMap<String, Object>> entry : autogireData.entrySet()) {
                        String dateKey = entry.getKey();
                        HashMap<String, Object> dateData = entry.getValue();

                        if (dateData != null) {
                            int heureDebut = ((Long) dateData.get("heureDebut")).intValue();
                            int heureFin = ((Long) dateData.get("heureFin")).intValue();

                            datesHeuresAutogire += "Date: " + dateKey + "\n";
                            datesHeuresAutogire += "Heure de début: " + heureDebut + "h\n";
                            datesHeuresAutogire += "Heure de fin: " + heureFin + "h\n\n";
                        }
                    }

                    if (!datesHeuresAutogire.isEmpty()) {
                        datesHeuresAutogire = "\n\nDates et heures location autogire:\n" + datesHeuresAutogire;
                    } else {
                        datesHeuresAutogire = "\n\nAucune date de location autogire prévue.";
                    }
                } else {
                    datesHeuresAutogire = "\n\nAucune date de location autogire prévue.";
                }


                String datesHeuresUlm = "";

                if (dataSnapshot.hasChild("date_uml_ulm")) {
                    HashMap<String, HashMap<String, Object>> ulmData = dataSnapshot.child("date_uml_ulm").getValue(new GenericTypeIndicator<HashMap<String, HashMap<String, Object>>>() {});

                    for (Map.Entry<String, HashMap<String, Object>> entry : ulmData.entrySet()) {
                        String dateKey = entry.getKey();
                        HashMap<String, Object> dateData = entry.getValue();

                        if (dateData != null) {
                            int heureDebut = ((Long) dateData.get("heureDebut")).intValue();
                            int heureFin = ((Long) dateData.get("heureFin")).intValue();

                            datesHeuresUlm += "Date: " + dateKey + "\n";
                            datesHeuresUlm += "Heure de début: " + heureDebut + "h\n";
                            datesHeuresUlm += "Heure de fin: " + heureFin + "h\n\n";
                        }
                    }

                    if (!datesHeuresUlm.isEmpty()) {
                        datesHeuresUlm = "\n\nDates et heures location ulm:\n" + datesHeuresUlm;
                    } else {
                        datesHeuresUlm = "\n\nAucune date de location ulm prévue.";
                    }
                } else {
                    datesHeuresUlm = "\n\nAucune date de location ulm prévue.";
                }

                String typeBrevet = dataSnapshot.child("licenceType").getValue(String.class);


                if (email != null) {
                    displayTextView.setText("Mail: " + email);
                } else {
                    displayTextView.setText("Aucun email renseigné.");
                }

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
                    displayTextView.setText(displayTextView.getText() + "\n\nEmail secondaire: " + emailSecond);
                } else {
                    displayTextView.setText(displayTextView.getText() + "\n\nAucun email secondaire renseigné.");
                }

                if (surfaceStationnement != null) {
                    displayTextView.setText(displayTextView.getText() + "\n\nSurface de stationnement: " + surfaceStationnement);
                } else {
                    displayTextView.setText(displayTextView.getText() + "\n\nAucune surface de stationnement renseignée.");
                }

                if (categorieAbri != null) {
                    displayTextView.setText(displayTextView.getText() + "\n\nCatégorie d'abri: " + categorieAbri);
                } else {
                    displayTextView.setText(displayTextView.getText() + "\n\nAucune catégorie d'abri renseignée.");
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

                displayTextView.setText(displayTextView.getText() + datesHeuresUlm);
                displayTextView.setText(displayTextView.getText() + datesHeuresAutogire);
                displayTextView.setText(displayTextView.getText() + datesHeuresBB);
                displayTextView.setText(displayTextView.getText() + datesHeuresLAPL);
                displayTextView.setText(displayTextView.getText() + datesHeuresPPL);
                displayTextView.setText(displayTextView.getText() + datesHeuresBapteme);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
