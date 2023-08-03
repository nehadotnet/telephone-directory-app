package com.example.task_login_signup_screen.view.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


import com.example.task_login_signup_screen.R;
import com.example.task_login_signup_screen.db.DataBaseHandler;
import com.example.task_login_signup_screen.models.UserProfileModel;
import com.example.task_login_signup_screen.utils.Constants;
import com.example.task_login_signup_screen.utils.ImageUtils;
import com.example.task_login_signup_screen.utils.Utils;
import com.example.task_login_signup_screen.view.fragments.HomeFragment;
import com.example.task_login_signup_screen.view.fragments.ProfileFragment;
import com.example.task_login_signup_screen.view.fragments.SettingFragment;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.navigation.NavigationView;

import de.hdodenhof.circleimageview.CircleImageView;

public class DashboardActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    String title = "Home";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
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
        setHeaderDetails();
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
                    userLogOut();
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

    private void userLogOut() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.logout))
                .setMessage(getString(R.string.are_you_sure_want_to_logout))
                .setPositiveButton(getString(R.string.dialog_yes), (dialog, which) -> {
                    SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREF_FILENAME, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.apply();
                    Utils.navigateScreen(DashboardActivity.this, SplashActivity.class);
                    finishAffinity();
                }).setNegativeButton(getString(R.string.dialog_no), (dialog, which) -> {
                });
        builder.show();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.close();
        } else {
            super.onBackPressed();
        }
    }

    private void setHeaderDetails() {
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREF_FILENAME, MODE_PRIVATE);
        String email = sharedPreferences.getString(Constants.PREF_EMAIL, "");
        String name = sharedPreferences.getString(Constants.PREF_NAME, "");

        View headerView = navigationView.getHeaderView(0);
        TextView tvName = headerView.findViewById(R.id.tv_user_name);
        tvName.setText(name);
        TextView tvEmail = headerView.findViewById(R.id.tv_user_email);
        tvEmail.setText(email);
        CircleImageView ivProfileImage = headerView.findViewById(R.id.civ_profile_image);

        int userId = sharedPreferences.getInt(Constants.PREF_USER_ID, -1);
        UserProfileModel userProfileModel = DataBaseHandler.getInstance(this).getUserData(userId);
        if (userProfileModel != null) {
            byte[] imageData = userProfileModel.getImageData();
            if (imageData != null && imageData.length > 0) {
                Bitmap bitmap = ImageUtils.decodeBase64Image(imageData);
                ivProfileImage.setImageBitmap(bitmap);
            }
        }
    }
}