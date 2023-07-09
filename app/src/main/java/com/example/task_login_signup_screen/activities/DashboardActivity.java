package com.example.task_login_signup_screen.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.task_login_signup_screen.R;

public class DashboardActivity extends AppCompatActivity {
    TextView userDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        userDetail=findViewById(R.id.tv_user_detail);


        String name=getIntent().getStringExtra("name");
        String email=getIntent().getStringExtra("email");

        userDetail.setText("UserName: "+name+"Email: "+email);




    }
}