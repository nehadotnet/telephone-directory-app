package com.example.task_login_signup_screen.view.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;


import com.example.task_login_signup_screen.R;
import com.example.task_login_signup_screen.utils.Utils;
import com.example.task_login_signup_screen.view.fragments.HomeFragment;
import com.example.task_login_signup_screen.view.fragments.ProfileFragment;
import com.example.task_login_signup_screen.view.fragments.SettingFragment;
import com.google.android.material.navigation.NavigationView;

public class NavigationDrawerActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    String title = "Home";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawerLayout, toolbar, R.string.OpenDrawer, R.string.CloseDrawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.fragment_container, new HomeFragment());
        ft.addToBackStack("");
        ft.commit();
        if (title.length() != 0 && getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                Fragment fragment = null;

                if (id == R.id.nav_home) {
                    fragment = new HomeFragment();
                    title = getString(R.string.home);

                } else if (id == R.id.nav_profile) {
                    fragment = new ProfileFragment();
                    title = getString(R.string.profile);

                } else if (id == R.id.nav_setting) {
                    fragment = new SettingFragment();
                    title = getString(R.string.settings);

                } else if (id == R.id.nav_logout) {
                    Utils.showToastMessage(NavigationDrawerActivity.this, "Logout");
                }
                if (fragment != null) {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.fragment_container, fragment);
                    ft.commit();
                    if (title.length() != 0 && getSupportActionBar() != null) {
                        getSupportActionBar().setTitle(title);
                    }
                }
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.close();
        } else {
            super.onBackPressed();
        }
    }
}