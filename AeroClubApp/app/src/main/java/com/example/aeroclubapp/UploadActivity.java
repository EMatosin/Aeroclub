package com.example.aeroclubapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class UploadActivity extends AppCompatActivity {

    ImageView uploadImage;
    Button saveButton;
    FloatingActionButton selectDateButton;
    EditText uploadName, uploadEmail;
    TextView uploadBirth;
    String imageURL;
    Integer year, month, day;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        uploadImage = findViewById(R.id.uploadImage);
        uploadBirth = findViewById(R.id.uploadBirth);
        uploadName = findViewById(R.id.uploadName);
        uploadEmail = findViewById(R.id.uploadEmail);
        saveButton = findViewById(R.id.saveButton);
        selectDateButton = findViewById(R.id.selectDateButton);

        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);


        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK){
                            Intent data = result.getData();
                            uri = data.getData();
                            uploadImage.setImageURI(uri);
                        } else {
                            Toast.makeText(UploadActivity.this, "Pas d'image sélectionné !", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        selectDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });
    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int monthOfYear, int dayOfMonth) {
                        // Update the EditText with the selected date in your desired format
                        String formattedDate = String.format("%02d/%02d/%04d", dayOfMonth, monthOfYear + 1, selectedYear);
                        uploadBirth.setText(formattedDate);
                    }
                },
                year,
                month,
                day
        );

        datePickerDialog.show();
    }


    public void saveData() {
        if (uri == null) {
            Toast.makeText(this, "Choisissez une image", Toast.LENGTH_SHORT).show();
            return;
        }

        String name = uploadName.getText().toString().trim();
        String birth = uploadBirth.getText().toString().trim();
        String email = uploadEmail.getText().toString().trim();

        if (name.isEmpty() || birth.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Remplissez toutes les informations", Toast.LENGTH_SHORT).show();
            return;
        }

        StorageReference storageReference = FirebaseStorage.getInstance().getReference()
                .child("Android Images")
                .child(uri.getLastPathSegment());

        AlertDialog.Builder builder = new AlertDialog.Builder(UploadActivity.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();

        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete());
                Uri urlImage = uriTask.getResult();
                imageURL = urlImage.toString();
                uploadData();
                dialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
            }
        });
    }


    public void uploadData(){
        String name = uploadName.getText().toString();
        String birth = uploadBirth.getText().toString();
        String email = uploadEmail.getText().toString();

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String noeud = sharedPreferences.getString("title", "");

        DatabaseReference titleRef = FirebaseDatabase.getInstance().getReference("AeroClubUser").child(noeud);

        Map<String, Object> updateData = new HashMap<>();
        updateData.put("name", name);
        updateData.put("birth", birth);
        updateData.put("email_second", email);
        updateData.put("imageurl", imageURL);

        titleRef.updateChildren(updateData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(UploadActivity.this, "Enregistré !", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UploadActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}