package com.example.task_login_signup_screen.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.task_login_signup_screen.R;

public class DashboardActivity extends AppCompatActivity {
    TextView userDetail;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        userDetail=findViewById(R.id.tv_user_detail);
        toolbar=findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        if(getSupportActionBar()!=null){
            //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Telephone Directory");
        }



        String name=getIntent().getStringExtra("name");
        String email=getIntent().getStringExtra("email");
        userDetail.setText("UserName: "+name+"\n\nEmail: "+email);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.option_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId=item.getItemId();
        
        if(itemId==R.id.opt_logout){
            Toast.makeText(this, "logout", Toast.LENGTH_SHORT).show();
        } else if (itemId==R.id.opt_settings) {
            Toast.makeText(this, "setting", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "home", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }
}