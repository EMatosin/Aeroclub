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


public class ParachutismeActivity extends AppCompatActivity {

    private CalendarView calendarView;

    private Button saveButton, decouverteButton, aventureButton, expertButton, videoButton, photoButton;

    private String selectedDate, selectedForfait, selectedOption;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parachutisme);

        calendarView = findViewById(R.id.calendarView);

        calendarView.setWeekDayTextAppearance(R.style.CalendarTextAppearance);

        saveButton = findViewById(R.id.saveButtonULM);
        decouverteButton = findViewById(R.id.buttonDecouverte);
        aventureButton = findViewById(R.id.buttonAventure);
        expertButton = findViewById(R.id.buttonExpert);
        videoButton = findViewById(R.id.buttonVideo);
        photoButton = findViewById(R.id.buttonPhoto);

        selectedOption = "sans options";


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
                    Toast.makeText(ParachutismeActivity.this, "Date en dehors des créneaux", Toast.LENGTH_SHORT).show();
                }
            }
        });

        decouverteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedForfait = "découverte";
                Toast.makeText(ParachutismeActivity.this, "Forfait découverte choisi", Toast.LENGTH_SHORT).show();
            }

        });

        aventureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedForfait = "aventure";
                Toast.makeText(ParachutismeActivity.this, "Forfait Aventure choisi", Toast.LENGTH_SHORT).show();
            }

        });

        expertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedForfait = "expert";
                Toast.makeText(ParachutismeActivity.this, "Forfait Expert choisi", Toast.LENGTH_SHORT).show();
            }

        });

        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedOption = "photo";
                Toast.makeText(ParachutismeActivity.this, "Photo inclus !", Toast.LENGTH_SHORT).show();
            }

        });

        videoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedOption = "video";
                Toast.makeText(ParachutismeActivity.this, "Vidéos incluses !", Toast.LENGTH_SHORT).show();
            }

        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData(selectedDate, selectedForfait, selectedOption);
            }

        });


    }

    private void saveData(String selectedDate, String selectedForfait, String selectedOption) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String noeud = sharedPreferences.getString("title", "");

        DataClassUser umlClass = new DataClassUser(selectedForfait, selectedOption, selectedDate);

        DatabaseReference titleRef = FirebaseDatabase.getInstance().getReference("AeroClubUser").child(noeud);

        Map<String, Object> updateData = new HashMap<>();
        updateData.put("date_parachutisme",umlClass.getParachutismeDate());
        updateData.put("forfait_parachutisme",umlClass.getTypeForfait());
        updateData.put("option_parachutisme",umlClass.getTypeOption());

        titleRef.updateChildren(updateData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(ParachutismeActivity.this, "Enregistré !", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ParachutismeActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
