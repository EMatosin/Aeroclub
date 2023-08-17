package com.example.aeroclubapp.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.aeroclubapp.R;

public class AdminActivity extends AppCompatActivity {

    Button PDF_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        PDF_button = findViewById(R.id.pdf_button);

        PDF_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GeneratePDF.generateAndDownloadPDF(AdminActivity.this);
            }
        });
    }
}
