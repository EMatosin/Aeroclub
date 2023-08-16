package com.example.aeroclubapp;


import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
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


public class BaptemeActivity extends AppCompatActivity {

    private CalendarView calendarView;

    private Button saveButton;

    private String selectedDate;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bapteme);

        calendarView = findViewById(R.id.calendarView);

        calendarView.setWeekDayTextAppearance(R.style.CalendarTextAppearance);

        saveButton = findViewById(R.id.saveButtonBapteme);


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
                    Toast.makeText(BaptemeActivity.this, "Date en dehors des créneaux", Toast.LENGTH_SHORT).show();
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
        DatabaseReference reservedDatesRef = FirebaseDatabase.getInstance().getReference("DatesChoisisULM/robin");

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
                    Toast.makeText(BaptemeActivity.this, "Cette date est déjà réservée", Toast.LENGTH_SHORT).show();
                } else {
                    saveData();
                    saveDate();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(BaptemeActivity.this, "Erreur lors de la vérification de la date", Toast.LENGTH_SHORT).show();
            }
        });
    }




    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String noeud = sharedPreferences.getString("title", "");

        DatabaseReference titleRef = FirebaseDatabase.getInstance().getReference("AeroClubUser").child(noeud);

        Map<String, Object> updateData = new HashMap<>();
        updateData.put("date_bapteme_air", selectedDate);

        titleRef.updateChildren(updateData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(BaptemeActivity.this, "Enregistré !", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(BaptemeActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveDate() {

        DatabaseReference datesRef = FirebaseDatabase.getInstance().getReference("DatesChoisisULM/robin");

        datesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> existingDates = new ArrayList<>();

                for (DataSnapshot dateSnapshot : dataSnapshot.getChildren()) {
                    String existingDate = dateSnapshot.getValue(String.class);
                    if (existingDate != null) {
                        existingDates.add(existingDate);
                    }
                }

                existingDates.add(selectedDate);

                datesRef.setValue(existingDates).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(BaptemeActivity.this, "Enregistré !", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(BaptemeActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(BaptemeActivity.this, databaseError.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
