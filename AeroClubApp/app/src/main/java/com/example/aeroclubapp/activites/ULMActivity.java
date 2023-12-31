package com.example.aeroclubapp.activites;


import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.example.aeroclubapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


public class ULMActivity extends AppCompatActivity {

    private CalendarView calendarView;

    private Button saveButton;

    private String selectedULM;

    private NumberPicker startNumber, endNumber;

    private LocalDateTime selectedDate;

    private ImageButton ulm;

    private ImageButton autogire;

    private Integer heureDebut, heureFin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ulm);

        calendarView = findViewById(R.id.calendarView);

        startNumber = findViewById(R.id.hourStart);

        endNumber = findViewById(R.id.hourEnd);

        calendarView.setWeekDayTextAppearance(R.style.CalendarTextAppearance);

        saveButton = findViewById(R.id.saveButtonULM);

        ulm=findViewById(R.id.ulm);

        autogire = findViewById(R.id.autogire);

        heureDebut = 7;

        heureFin = 8;


        calendarView.setMinDate(System.currentTimeMillis() - 1000);

        Calendar startDateCalendar = Calendar.getInstance();
        startDateCalendar.set(Calendar.YEAR, 2023);
        startDateCalendar.set(Calendar.MONTH, Calendar.APRIL);
        startDateCalendar.set(Calendar.DAY_OF_MONTH, 15);
        long startDateMillis = startDateCalendar.getTimeInMillis();

        Calendar endDateCalendar = Calendar.getInstance();
        endDateCalendar.set(Calendar.YEAR, 2023);
        endDateCalendar.set(Calendar.MONTH, Calendar.OCTOBER);
        endDateCalendar.set(Calendar.DAY_OF_MONTH, 15);
        long endDateMillis = endDateCalendar.getTimeInMillis();


        String[] hoursDebut = new String[]{"Heure début", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18"};
        startNumber.setMinValue(7);
        startNumber.setMaxValue(18);
        startNumber.setDisplayedValues(hoursDebut);
        startNumber.setWrapSelectorWheel(false);

        String[] hoursFin = new String[]{"Heure fin", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19"};

        endNumber.setMinValue(8);
        endNumber.setMaxValue(19);
        endNumber.setDisplayedValues(hoursFin);
        endNumber.setWrapSelectorWheel(false);


        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Calendar selectedCalendar = Calendar.getInstance();
                selectedCalendar.set(year, month, dayOfMonth);

                int selectedYear = selectedCalendar.get(Calendar.YEAR);
                int selectedMonth = selectedCalendar.get(Calendar.MONTH) + 1;
                int selectedDay = selectedCalendar.get(Calendar.DAY_OF_MONTH);

                long selectedDateMillis = selectedCalendar.getTimeInMillis();

                boolean isWithinOpeningPeriod = selectedDateMillis >= startDateMillis && selectedDateMillis <= endDateMillis;
                boolean isWeekend = selectedCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ||
                        selectedCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;

                if (isWithinOpeningPeriod || isWeekend) {
                    selectedDate = LocalDateTime.of(selectedYear, selectedMonth, selectedDay, 0, 0);
                } else {
                    Toast.makeText(ULMActivity.this, "Date en dehors des créneaux", Toast.LENGTH_SHORT).show();
                }
            }
        });

        startNumber.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                heureDebut = startNumber.getValue();
                Log.d("début value", heureDebut + "");
            }
        });

        endNumber.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                heureFin = endNumber.getValue();
                Log.d("fin value", heureFin + "");
            }
        });

        autogire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedULM = "autogire";
                Toast.makeText(ULMActivity.this, "Autogire choisi", Toast.LENGTH_SHORT).show();
            }

        });

        ulm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedULM = "ulm";
                Toast.makeText(ULMActivity.this, "ULM choisi", Toast.LENGTH_SHORT).show();
            }

        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (heureDebut == 7 || heureFin == 8 ) {
                    Toast.makeText(ULMActivity.this, "Veuillez sélectionner une heure valide", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (heureFin - heureDebut < 1) {
                    Toast.makeText(ULMActivity.this, "La durée de la réservation doit être d'au moins 1 heure", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (selectedDate == null) {
                    Toast.makeText(ULMActivity.this, "Veuillez sélectionner une date", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (selectedULM == null) {
                    Toast.makeText(ULMActivity.this, "Veuillez choisir un type d'ULM", Toast.LENGTH_SHORT).show();
                    return;
                }

                checkAndSaveDate();
            }
        });



    }

    private void checkAndSaveDate() {
        DatabaseReference ulmRef = FirebaseDatabase.getInstance().getReference("DatesChoisisULM/" + selectedULM);

        ulmRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean isDateReserved = false;
                Integer reservedHeureDebut = null;
                Integer reservedHeureFin = null;

                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot dateSnapshot : userSnapshot.getChildren()) {
                        String dateKey = dateSnapshot.getKey();
                        if (dateKey != null && dateKey.equals(selectedDate.toString())) {
                            Integer reservedHeureDebutValue = dateSnapshot.child("heureDebut").getValue(Integer.class);
                            Integer reservedHeureFinValue = dateSnapshot.child("heureFin").getValue(Integer.class);

                            if (reservedHeureDebutValue != null && reservedHeureFinValue != null) {
                                reservedHeureDebut = reservedHeureDebutValue;
                                reservedHeureFin = reservedHeureFinValue;

                                if ((heureDebut >= reservedHeureDebut && heureDebut < reservedHeureFin) ||
                                        (heureFin > reservedHeureDebut && heureFin <= reservedHeureFin) ||
                                        (heureDebut <= reservedHeureDebut && heureFin >= reservedHeureFin)) {
                                    isDateReserved = true;
                                    break;
                                }
                            }
                        }
                    }

                    if (isDateReserved) {
                        break;
                    }
                }

                if (isDateReserved) {
                    Toast.makeText(ULMActivity.this, "Un créneau est déjà réservé de " + reservedHeureDebut + "h à " + reservedHeureFin + "h.", Toast.LENGTH_SHORT).show();
                } else {
                    saveData();
                    saveDate();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ULMActivity.this, "Erreur lors de la vérification des dates", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String noeud = sharedPreferences.getString("title", "");

        DatabaseReference titleRef = FirebaseDatabase.getInstance().getReference("AeroClubUser").child(noeud + "/" + "date_uml_" + selectedULM);
        DatabaseReference reservationRef = titleRef.child(selectedDate.toString());

        Map<String, Object> updateData = new HashMap<>();
        updateData.put("heureDebut", heureDebut);
        updateData.put("heureFin", heureFin);

        reservationRef.updateChildren(updateData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(ULMActivity.this, "Enregistré !", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ULMActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveDate() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String noeud = sharedPreferences.getString("title", "");

        DatabaseReference ulmRef = FirebaseDatabase.getInstance().getReference("DatesChoisisULM/" + selectedULM + "/" + noeud);
        DatabaseReference reservationRef = ulmRef.child(selectedDate.toString());

        Map<String, Object> reservationData = new HashMap<>();
        reservationData.put("heureDebut", heureDebut);
        reservationData.put("heureFin", heureFin);

        reservationRef.updateChildren(reservationData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(ULMActivity.this, "Enregistré !", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ULMActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }



}
