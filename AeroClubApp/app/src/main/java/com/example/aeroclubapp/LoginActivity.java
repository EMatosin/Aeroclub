package com.example.aeroclubapp;


import androidx.appcompat.app.AppCompatActivity;


import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.content.Intent;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends AppCompatActivity {

    private EditText loginEmail, loginPassword;
    private TextView signupRedirectText;
    private Button loginButton;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginEmail = findViewById(R.id.login_email);
        loginPassword = findViewById(R.id.login_password);
        loginButton = findViewById(R.id.login_button);
        signupRedirectText = findViewById(R.id.signUpRedirectText);

        auth = FirebaseAuth.getInstance();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = loginEmail.getText().toString();
                String pass = loginPassword.getText().toString();


                if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    if (!pass.isEmpty()) {
                        auth.signInWithEmailAndPassword(email, pass)
                                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(AuthResult authResult) {
                                        FirebaseUser currentUser = auth.getCurrentUser();
                                        if (currentUser != null) {
                                            String email = currentUser.getEmail();
                                            if (email.equals("admin@admin.fr")) {
                                                Toast.makeText(LoginActivity.this, "Connexion réussie en tant qu'administrateur", Toast.LENGTH_SHORT).show();
                                                Intent adminActivity = new Intent(getApplicationContext(), AdminActivity.class);
                                                startActivity(adminActivity);
                                            } else {
                                                Toast.makeText(LoginActivity.this, "Connexion réussie", Toast.LENGTH_SHORT).show();
                                                saveData();
                                                Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
                                                startActivity(mainActivity);
                                            }
                                            finish();
                                        }
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(LoginActivity.this, "Connexion échouée", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else {
                        loginPassword.setError("Champs vides interdits");
                    }
                } else if (email.isEmpty()) {
                    loginEmail.setError("Champs vides interdits");
                } else {
                    loginEmail.setError("Email incorrect");
                }
            }
        });

        signupRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignInActivity.class));
            }
        });
    }

    private void saveData() {

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            String uid = currentUser.getUid();
            String email = currentUser.getEmail();

            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("title", uid);
            editor.apply();

            Map<String, Object> updateData = new HashMap<>();
            updateData.put("dataEmail", email);

            FirebaseDatabase.getInstance().getReference("AeroClubUser").child(uid)
                    .updateChildren(updateData).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                finish();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(LoginActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}