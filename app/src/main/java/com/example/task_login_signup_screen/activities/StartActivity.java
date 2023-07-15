package com.example.task_login_signup_screen.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.task_login_signup_screen.BuildConfig;
import com.example.task_login_signup_screen.R;

public class StartActivity extends AppCompatActivity {
    LottieAnimationView lvScreen;
    TextView tvVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        lvScreen = findViewById(R.id.lv_screen);
        tvVersion = findViewById(R.id.tv_version);

        tvVersion.setText(getString(R.string.version_prefix) + BuildConfig.VERSION_NAME);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent nextScreen = new Intent(StartActivity.this, LoginActivity.class);
                startActivity(nextScreen);
            }
        }, 5000);
    }
}