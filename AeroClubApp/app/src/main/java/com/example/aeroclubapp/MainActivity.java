package com.example.aeroclubapp;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.io.IOException;
import java.nio.charset.Charset;

public class MainActivity extends AppCompatActivity{
    DrawerLayout drawerLayout;
    BottomNavigationView bottomNavigationView;


    private TextView welcomeTextView;
    private String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavigationView usernameRecuperation = findViewById(R.id.nav_view);
        View headerView = usernameRecuperation.getHeaderView(0);
        welcomeTextView = headerView.findViewById(R.id.welcomeTextView);

        username = getIntent().getStringExtra("username");

        welcomeTextView.setText("Bienvenue," + username);

        drawerLayout = findViewById(R.id.drawer_layout);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        NavigationView navigationView = findViewById(R.id.nav_view);
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }

        replaceFragment(new HomeFragment());

        bottomNavigationView.setBackground(null);
        bottomNavigationView.setOnItemSelectedListener(item -> {

            int itemId = item.getItemId();

            if (itemId == R.id.menu_home) {
                replaceFragment(new HomeFragment());
            } else if (itemId == R.id.menu_shorts) {
                replaceFragment(new ShortsFragment());
            } else if (itemId == R.id.menu_subscriptions) {
                replaceFragment(new SubscriptionsFragment());
            } else if (itemId == R.id.menu_library) {
                replaceFragment(new LibraryFragment());
            }
            return true;
        });

    }


    private  void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

}