package com.example.task_login_signup_screen.view.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task_login_signup_screen.db.DataBaseHandler;
import com.example.task_login_signup_screen.models.ContactModel;
import com.example.task_login_signup_screen.R;
import com.example.task_login_signup_screen.adapter.ContactAdapter;
import com.example.task_login_signup_screen.listeners.OnItemClickListener;
import com.example.task_login_signup_screen.utils.Constants;
import com.example.task_login_signup_screen.utils.Utils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity implements OnItemClickListener {
    TextView userDetail;
    RecyclerView rvContacts;
    Toolbar toolbar;
    FloatingActionButton fabAddContact;

    ArrayList<ContactModel> arrayContact = new ArrayList<>();
    ContactAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        iniUI();
        setUI();
    }

    private void iniUI() {
        userDetail = findViewById(R.id.tv_user_detail);
        toolbar = findViewById(R.id.toolbar);
        rvContacts = findViewById(R.id.rv_contacts);
        fabAddContact = findViewById(R.id.btn_open_dialog);

    }

    private void setUI() {
        setSupportActionBar(toolbar);
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREF_FILENAME, MODE_PRIVATE);
        String email = sharedPreferences.getString(Constants.PREF_NAME, "");
        String name = sharedPreferences.getString(Constants.PREF_EMAIL, "");
        int userId = sharedPreferences.getInt(Constants.PREF_USER_ID, -1);
        userDetail.setText("UserName: " + name + "\n\nEmail: " + email);

        arrayContact.clear();
        arrayContact = DataBaseHandler.getInstance(this).getAllContact(DashboardActivity.this);
        rvContacts.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ContactAdapter(this, arrayContact, this);
        rvContacts.setAdapter(adapter);

        fabAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.navigateScreen(DashboardActivity.this, ContactFormActivity.class);
                refreshAdapter();
            }
        });
    }

    private void refreshAdapter() {
        arrayContact = DataBaseHandler.getInstance(this).getAllContact(DashboardActivity.this);
        adapter.refreshAdapter(arrayContact);
        rvContacts.scrollToPosition(arrayContact.size() - 1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.opt_logout) {
            userLogOut();
        } else if (itemId == R.id.opt_settings) {
            Utils.showToastMessage(DashboardActivity.this, "setting");
        }
        return super.onOptionsItemSelected(item);
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
    public void onItemClick(int position, int type) {
        if (type == 10) {
            String phoneNumber = arrayContact.get(position).getPhone();
            Utils.dialContact(DashboardActivity.this, phoneNumber);
        } else if (type == 20) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.delete_contact))
                    .setMessage(getString(R.string.are_you_sure_want_to_delete))
                    .setPositiveButton(getString(R.string.dialog_yes), (dialog, which) -> {
                        Log.e("TAG", "onItemClick: " + arrayContact.get(position).toString());
                        boolean result = DataBaseHandler.getInstance(DashboardActivity.this).deleteContact(arrayContact.get(position).getId());
                        if (result) {
                            Utils.showToastMessage(DashboardActivity.this, getString(R.string.contact_deleted_successfully));
                            arrayContact.clear();
                            arrayContact = DataBaseHandler.getInstance(this).getAllContact(DashboardActivity.this);
                            Log.e("TAG", "onItemClick: " + arrayContact.size());
                            adapter.refreshAdapter(arrayContact);
                        }
                        adapter.notifyDataSetChanged();
                    })
                    .setNegativeButton(getString(R.string.dialog_no), (dialog, which) -> {
                    });
            builder.show();
        } else if (type == 30) {
            Intent intent = new Intent(DashboardActivity.this, ContactFormActivity.class);
            intent.putExtra("contact", arrayContact.get(position));
            startActivity(intent);
        }
    }
}