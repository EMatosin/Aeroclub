package com.example.aeroclubapp.activites;


import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
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


public class BBActivity extends AppCompatActivity {

    private CalendarView calendarView;

    private Button saveButton;

    private NumberPicker startNumber, endNumber;
    private LocalDateTime selectedDate;

    private Integer heureDebut, heureFin;

    private int totalHoursRegistered = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bbactivity);

        calendarView = findViewById(R.id.calendarView);

        calendarView.setWeekDayTextAppearance(R.style.CalendarTextAppearance);

        startNumber = findViewById(R.id.hourStart);

        endNumber = findViewById(R.id.hourEnd);

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

        String[] hoursDebut = new String[]{"8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18"};
        startNumber.setMinValue(8);
        startNumber.setMaxValue(18);
        startNumber.setDisplayedValues(hoursDebut);
        startNumber.setWrapSelectorWheel(false);

        String[] hoursFin = new String[]{"9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19"};

        endNumber.setMinValue(9);
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
                    Toast.makeText(BBActivity.this, "Date en dehors des créneaux", Toast.LENGTH_SHORT).show();
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

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (heureFin - heureDebut < 1) {
                    Toast.makeText(BBActivity.this, "La durée de la réservation doit être d'au moins 1 heure", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (selectedDate == null) {
                    Toast.makeText(BBActivity.this, "Veuillez sélectionner une date", Toast.LENGTH_SHORT).show();
                    return;
                }

                checkAndSaveDate();
            }
        });


    }

    private void checkAndSaveDate() {
        DatabaseReference ulmRef = FirebaseDatabase.getInstance().getReference("DatesChoisisULM/robin");

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
                    Toast.makeText(BBActivity.this, "Un créneaux est déjà réservé de " + reservedHeureDebut + "h à " + reservedHeureFin + "h.", Toast.LENGTH_SHORT).show();
                } else {
                    saveData();
                    saveDate();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(BBActivity.this, "Erreur lors de la vérification des dates", Toast.LENGTH_SHORT).show();
            }
        });
    }




    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String noeud = sharedPreferences.getString("title", "");

        DatabaseReference titleRef = FirebaseDatabase.getInstance().getReference("AeroClubUser").child(noeud + "/" + "date_BB");
        DatabaseReference reservationRef = titleRef.child(selectedDate.toString());

        Map<String, Object> updateData = new HashMap<>();
        updateData.put("heureDebut", heureDebut);
        updateData.put("heureFin", heureFin);

        reservationRef.updateChildren(updateData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    updateTotalHours(heureFin - heureDebut);
                } else {
                    Toast.makeText(BBActivity.this, "Erreur lors de l'enregistrement", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateTotalHours(int duration) {
        totalHoursRegistered += duration;

        if (totalHoursRegistered >= 10) {
            Toast.makeText(BBActivity.this, "Total de 10 heures atteint.", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            int remainingHours = 10 - totalHoursRegistered;
            Toast.makeText(BBActivity.this, "Enregistré ! Il vous reste encore " + remainingHours + " heures pour atteindre 10 heures.", Toast.LENGTH_SHORT).show();
        }
    }


    private void saveDate() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String noeud = sharedPreferences.getString("title", "");

        DatabaseReference ulmRef = FirebaseDatabase.getInstance().getReference("DatesChoisisULM/robin/" + noeud);
        DatabaseReference reservationRef = ulmRef.child(selectedDate.toString());

        Map<String, Object> reservationData = new HashMap<>();
        reservationData.put("heureDebut", heureDebut);
        reservationData.put("heureFin", heureFin);

        reservationRef.updateChildren(reservationData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(BBActivity.this, "Enregistré !", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(BBActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}