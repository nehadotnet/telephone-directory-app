package com.example.task_login_signup_screen.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;

import com.example.task_login_signup_screen.R;
import com.example.task_login_signup_screen.db.DataBaseHandler;
import com.example.task_login_signup_screen.models.ContactModel;
import com.example.task_login_signup_screen.utils.Utils;
import com.google.android.material.textfield.TextInputEditText;

import okhttp3.internal.Util;

public class ContactFormActivity extends AppCompatActivity {
    private TextInputEditText etFullName, etPhone, etEmail, etAddress,
            etWorkInfo, etNickName, etRelationship, etWebsite;
    private AppCompatButton btnReset, btnAdd;

    private DataBaseHandler dataBaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_form);

        initUI();
        setListener();
    }


    private void initUI() {
        etFullName = findViewById(R.id.et_full_name);
        etPhone = findViewById(R.id.et_phone);
        etEmail = findViewById(R.id.et_email);
        etNickName = findViewById(R.id.et_nick_name);
        etAddress = findViewById(R.id.et_address);
        etWorkInfo = findViewById(R.id.et_work_info);
        etRelationship = findViewById(R.id.et_relationship);
        etWebsite = findViewById(R.id.et_website);
        btnReset = findViewById(R.id.btn_reset);
        btnAdd = findViewById(R.id.btn_add);
    }

    private void setListener() {
        btnReset.setOnClickListener(v -> {
            etFullName.setText("");
            etPhone.setText("");
            etEmail.setText("");
            etNickName.setText("");
            etAddress.setText("");
            etWorkInfo.setText("");
            etRelationship.setText("");
            etWebsite.setText("");
        });
        btnAdd.setOnClickListener(v -> {
            if (validate()) {
                ContactModel contactModel = new ContactModel();
                contactModel.setFullName(etFullName.getText().toString().trim());
                contactModel.setPhone(etPhone.getText().toString().trim());
                contactModel.setAddress(etAddress.getText().toString().trim());
                contactModel.setEmail(etEmail.getText().toString().trim());
                contactModel.setWebsite(etWebsite.getText().toString().trim());
                contactModel.setRelationship(etRelationship.getText().toString().trim());
                contactModel.setNickName(etNickName.getText().toString().trim());
                contactModel.setWorkInfo(etWorkInfo.getText().toString().trim());
                dataBaseHandler = new DataBaseHandler(ContactFormActivity.this);
                boolean result = dataBaseHandler.addNewContact(contactModel);
                if (result) {
                    Utils.navigateScreen(ContactFormActivity.this, DashboardActivity.class);
                    finish();
                } else {
                    Utils.showToastMessage(ContactFormActivity.this, getString(R.string.something_went_wrong));
                }

            }
        });
    }

    private boolean validate() {
        if (etFullName.getText().length() == 0) {
            etFullName.setError(getString(R.string.full_name_required));
            etFullName.requestFocus();
            return false;
        }
        if (etPhone.getText().length() == 0) {
            etPhone.setError(getString(R.string.phone_required));
            etPhone.requestFocus();
            return false;
        }
        if (etPhone.getText().length() < 10 || etPhone.getText().length() > 10) {
            etPhone.setError(getString(R.string.invalid_phone));
            etPhone.requestFocus();
            return false;
        }
        return true;

    }

}