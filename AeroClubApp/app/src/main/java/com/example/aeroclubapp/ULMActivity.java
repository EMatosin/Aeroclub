package com.example.aeroclubapp;


import android.icu.util.Calendar;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


public class ULMActivity extends AppCompatActivity {

    private CalendarView calendarView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ulmactivity);

        calendarView = findViewById(R.id.calendarView);

        calendarView.setWeekDayTextAppearance(R.style.CalendarTextAppearance);

        calendarView.setMinDate(System.currentTimeMillis() - 1000);

        // Définir la date de début (15 avril) et de fin (15 octobre) en millisecondes
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

                long selectedDateMillis = selectedCalendar.getTimeInMillis();

                boolean isWithinOpeningPeriod = selectedDateMillis >= startDateMillis && selectedDateMillis <= endDateMillis;
                boolean isWeekend = selectedCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ||
                        selectedCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;

                if (isWithinOpeningPeriod || isWeekend) {
                    Toast.makeText(ULMActivity.this, "Enregistré !", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ULMActivity.this, "NON !", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
