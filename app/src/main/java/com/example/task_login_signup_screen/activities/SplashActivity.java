package com.example.task_login_signup_screen.activities;

import static com.example.task_login_signup_screen.utils.Constants.PREF_EMAIL;
import static com.example.task_login_signup_screen.utils.Constants.PREF_NAME;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.example.task_login_signup_screen.BuildConfig;
import com.example.task_login_signup_screen.R;
import com.example.task_login_signup_screen.utils.Constants;

public class SplashActivity extends AppCompatActivity {
    LottieAnimationView lvScreen;
    TextView tvVersion;
    Intent nextScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        lvScreen = findViewById(R.id.lv_screen);
        tvVersion = findViewById(R.id.tv_version);

        tvVersion.setText(getString(R.string.version_prefix) + BuildConfig.VERSION_NAME);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREF_FILENAME, MODE_PRIVATE);
                String token = sharedPreferences.getString(Constants.PREF_TOKEN, "");
                if (token.length() > 0) {
                    nextScreen = new Intent(SplashActivity.this, DashboardActivity.class);
                    String email = sharedPreferences.getString(PREF_EMAIL, "");
                    String name = sharedPreferences.getString(PREF_NAME, "");
                    nextScreen.putExtra(PREF_EMAIL, email);
                    nextScreen.putExtra(PREF_NAME, name);
                    startActivity(nextScreen);
                } else {
                    nextScreen = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(nextScreen);
                }
            }
        }, Constants.HANDLER_DELAY);
    }
}