package com.example.task_login_signup_screen.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task_login_signup_screen.Model.ContactModel;
import com.example.task_login_signup_screen.R;
import com.example.task_login_signup_screen.adapter.ContactAdapter;
import com.example.task_login_signup_screen.listeners.OnItemClickListener;
import com.example.task_login_signup_screen.utils.Utils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity implements OnItemClickListener {
    TextView userDetail;
    RecyclerView rvContacts;
    Toolbar toolbar;
    FloatingActionButton btnOpenDialog;

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
        btnOpenDialog = findViewById(R.id.btn_open_dialog);

    }

    private void setUI() {
        setSupportActionBar(toolbar);

        String name = getIntent().getStringExtra("name");
        String email = getIntent().getStringExtra("email");
        userDetail.setText("UserName: " + name + "\n\nEmail: " + email);

        rvContacts.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ContactAdapter(this, arrayContact, this);
        rvContacts.setAdapter(adapter);

        btnOpenDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog dialog = new Dialog(DashboardActivity.this);
                dialog.setContentView(R.layout.add_update_layout);

                EditText edName = dialog.findViewById(R.id.ed_name);
                EditText edNumber = dialog.findViewById(R.id.ed_number);
                AppCompatButton btnAction = dialog.findViewById(R.id.btn_add);

                btnAction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String name = "", number = "";
                        if (!edName.getText().toString().equals("")) {
                            name = edName.getText().toString();
                        }
                        if (!edName.getText().toString().equals("")) {
                            number = edNumber.getText().toString();
                        } else {
                            Utils.showToastMessage(DashboardActivity.this, getString(R.string.all_fields_required));
                        }

                        arrayContact.add(new ContactModel(name, number));
                        adapter.notifyItemInserted(arrayContact.size() - 1);
                        rvContacts.scrollToPosition(arrayContact.size() - 1);
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
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
            Utils.showToastMessage(DashboardActivity.this, "logout");
        } else if (itemId == R.id.opt_settings) {
            Utils.showToastMessage(DashboardActivity.this, "setting");
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(int position, int type) {
        if (type == 10) {
            String phoneNumber = arrayContact.get(position).number;
            Utils.dialContact(DashboardActivity.this, phoneNumber);
        } else if (type == 20) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.delete_contact))
                    .setMessage(getString(R.string.are_you_sure_want_to_delete))
                    .setPositiveButton(getString(R.string.dialog_yes), (dialog, which) -> {

                        arrayContact.remove(position);
                        adapter.notifyItemRemoved(position);
                    })
                    .setNegativeButton(getString(R.string.dialog_no), (dialog, which) -> {

                    });
            builder.show();
        } else if (type == 30) {
            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.add_update_layout);

            EditText edName = dialog.findViewById(R.id.ed_name);
            EditText edNumber = dialog.findViewById(R.id.ed_number);
            AppCompatButton btnAction = dialog.findViewById(R.id.btn_add);
            TextView tvTitle = dialog.findViewById(R.id.tv_title);

            btnAction.setText("Update");
            tvTitle.setText("Update Contact");

            edName.setText((arrayContact.get(position)).name);
            edNumber.setText((arrayContact.get(position)).number);

            btnAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name = "", number = "";
                    if (!edName.getText().toString().equals("")) {
                        name = edName.getText().toString();
                    }
                    if (!edName.getText().toString().equals("")) {
                        number = edNumber.getText().toString();
                    } else {
                        Utils.showToastMessage(DashboardActivity.this, getString(R.string.all_fields_required));
                    }

                    arrayContact.set(position, new ContactModel(name, number));
                    adapter.notifyItemChanged(position);

                    dialog.dismiss();
                    Utils.showToastMessage(DashboardActivity.this, getString(R.string.updated_successfully));
                }
            });
            dialog.show();
        }
    }
}