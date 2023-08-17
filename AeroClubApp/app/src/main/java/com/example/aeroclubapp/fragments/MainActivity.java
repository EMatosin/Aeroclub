package com.example.aeroclubapp.fragments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.example.aeroclubapp.PanierActivity;
import com.example.aeroclubapp.R;
import com.example.aeroclubapp.databinding.ActivityMainBinding;
import com.example.aeroclubapp.fragments.aeroclub.AeroclubFragment;
import com.example.aeroclubapp.fragments.basicservice.BasicServiceFragment;
import com.example.aeroclubapp.fragments.HomeFragment;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        replaceFragment(new HomeFragment());
        binding.bottomNavigationView.setBackground(null);

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            int itemId = item.getItemId();

            if (itemId == R.id.home) {
                replaceFragment(new HomeFragment());
            } else if (itemId == R.id.basicService) {
                replaceFragment(new BasicServiceFragment());
            } else if (itemId == R.id.aeroclub) {
                replaceFragment(new AeroclubFragment());
            } else if (itemId == R.id.commandes) {
                Intent panierActivity = new Intent(getApplicationContext(), PanierActivity.class);
                startActivity(panierActivity);
            }
            return true;
        });

    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}