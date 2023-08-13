package com.example.aeroclubapp;


import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ULMActivity extends AppCompatActivity {

    private CalendarView calendarView;

    private Button saveButton;

    private String selectedDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ulmactivity);

        calendarView = findViewById(R.id.calendarView);

        calendarView.setWeekDayTextAppearance(R.style.CalendarTextAppearance);

        saveButton = findViewById(R.id.saveButtonULM);

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

        int dayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);

        boolean isOpenOnWeekends = dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY;

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
                    selectedDate = selectedDay + "/" + selectedMonth + "/" + selectedYear;


                } else {
                    Toast.makeText(ULMActivity.this, "Date en dehors des créneaux", Toast.LENGTH_SHORT).show();
                }
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAndSaveDate(selectedDate);
            }

        });


    }

    private void checkAndSaveDate(String selectedDate) {
        DatabaseReference reservedDatesRef = FirebaseDatabase.getInstance().getReference("DatesChoisisULM/réservés");

        reservedDatesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean isDateReserved = false;

                for (DataSnapshot dateSnapshot : dataSnapshot.getChildren()) {
                    String reservedDate = dateSnapshot.getValue(String.class);

                    if (reservedDate != null && reservedDate.equals(selectedDate)) {
                        isDateReserved = true;
                        break;
                    }
                }

                if (isDateReserved) {
                    Toast.makeText(ULMActivity.this, "Cette date est déjà réservée", Toast.LENGTH_SHORT).show();
                } else {
                    saveData(selectedDate);
                    saveDate(selectedDate);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ULMActivity.this, "Erreur lors de la vérification de la date", Toast.LENGTH_SHORT).show();
            }
        });
    }




    private void saveData(String selectedDate) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String noeud = sharedPreferences.getString("title", "");

        DataClassUser umlClass = new DataClassUser(selectedDate);

        DatabaseReference titleRef = FirebaseDatabase.getInstance().getReference("AeroClubUser").child(noeud);

        Map<String, Object> updateData = new HashMap<>();
        updateData.put("date_uml", umlClass.getUmlDate());

        titleRef.updateChildren(updateData).addOnCompleteListener(new OnCompleteListener<Void>() {
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

    private void saveDate(String selectedDate) {

        DatabaseReference datesRef = FirebaseDatabase.getInstance().getReference("DatesChoisisULM").child("réservés");

        datesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Créer une liste pour stocker les dates existantes
                List<String> existingDates = new ArrayList<>();

                // Parcourir les données existantes et les ajouter à la liste
                for (DataSnapshot dateSnapshot : dataSnapshot.getChildren()) {
                    String existingDate = dateSnapshot.getValue(String.class);
                    if (existingDate != null) {
                        existingDates.add(existingDate);
                    }
                }

                // Ajouter la nouvelle date à la liste
                existingDates.add(selectedDate);

                // Mettre à jour les données dans la base de données avec la liste mise à jour
                datesRef.setValue(existingDates).addOnCompleteListener(new OnCompleteListener<Void>() {
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

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ULMActivity.this, databaseError.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
