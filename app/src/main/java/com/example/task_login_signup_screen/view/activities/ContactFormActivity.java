package com.example.task_login_signup_screen.view.activities;

import static com.example.task_login_signup_screen.utils.Constants.HANDLER_DELAY;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.example.task_login_signup_screen.R;
import com.example.task_login_signup_screen.db.DataBaseHandler;
import com.example.task_login_signup_screen.models.ContactModel;
import com.example.task_login_signup_screen.utils.Constants;
import com.example.task_login_signup_screen.utils.ImageUtils;
import com.example.task_login_signup_screen.utils.Utils;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContactFormActivity extends AppCompatActivity {
    private TextInputEditText etFullName, etPhone, etEmail, etAddress,
            etWorkInfo, etNickName, etRelationship, etWebsite;
    private AppCompatButton btnReset, btnAdd;

    private FloatingActionButton btnFab;
    private CircleImageView ivProfile;
    private SpinKitView spinKitView;
    private ContactModel contactModel;
    SharedPreferences sharedPreferences;
    private byte[] imageData;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_form);

        initUI();
        setListener();
        setUI();
        sharedPreferences = getSharedPreferences(Constants.PREF_FILENAME, MODE_PRIVATE);
    }

    private void setUI() {
        contactModel = (ContactModel) getIntent().getSerializableExtra("contact");
        Log.e("TAG", "setUI: " + contactModel);
        if (contactModel != null) {
            etFullName.setText(contactModel.getFullName());
            etPhone.setText(contactModel.getPhone());
            etEmail.setText(contactModel.getEmail());
            etNickName.setText(contactModel.getNickName());
            etAddress.setText(contactModel.getAddress());
            etWorkInfo.setText(contactModel.getWorkInfo());
            etRelationship.setText(contactModel.getRelationship());
            etWebsite.setText(contactModel.getWebsite());
            ivProfile.setImageBitmap(ImageUtils.decodeBase64Image(contactModel.getImageData()));
            btnAdd.setText("Edit");
            if (getSupportActionBar() != null)
                getSupportActionBar().setTitle("Edit " + contactModel.getFullName() + "'s Contact");
        } else {
            if (getSupportActionBar() != null)
                getSupportActionBar().setTitle("Add New Contact");
        }
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
        spinKitView = findViewById(R.id.spin_kit);
        ivProfile = findViewById(R.id.civ_profile_image);
        btnFab = findViewById(R.id.btn_fab);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
            String btnText = btnAdd.getText().toString();
            int userId = sharedPreferences.getInt(Constants.PREF_USER_ID, -1);
            if (validate()) {
                spinKitView.setVisibility(View.VISIBLE);
                new Handler().postDelayed(() -> {
                    spinKitView.setVisibility(View.GONE);
                    if (btnText.equals("Add")) {
                        ContactModel contactModel = new ContactModel();
                        contactModel.setFullName(etFullName.getText().toString().trim());
                        contactModel.setPhone(etPhone.getText().toString().trim());
                        contactModel.setAddress(etAddress.getText().toString().trim());
                        contactModel.setEmail(etEmail.getText().toString().trim());
                        contactModel.setWebsite(etWebsite.getText().toString().trim());
                        contactModel.setRelationship(etRelationship.getText().toString().trim());
                        contactModel.setNickName(etNickName.getText().toString().trim());
                        contactModel.setWorkInfo(etWorkInfo.getText().toString().trim());
                        contactModel.setUserId(userId);
                        if (imageData != null && imageData.length > 0)
                            contactModel.setImageData(imageData);
                        boolean result = DataBaseHandler.getInstance(ContactFormActivity.this).addNewContact(contactModel);
                        if (result) {
                            Utils.navigateScreen(ContactFormActivity.this, DashboardActivity.class);
                            finish();
                        } else {
                            Utils.showToastMessage(ContactFormActivity.this, getString(R.string.something_went_wrong));
                        }

                    } else if (btnText.equals("Edit")) {
                        ContactModel updatedContactModel = new ContactModel();
                        updatedContactModel.setId(contactModel.getId());
                        updatedContactModel.setFullName(etFullName.getText().toString().trim());
                        updatedContactModel.setPhone(etPhone.getText().toString().trim());
                        updatedContactModel.setAddress(etAddress.getText().toString().trim());
                        updatedContactModel.setEmail(etEmail.getText().toString().trim());
                        updatedContactModel.setWebsite(etWebsite.getText().toString().trim());
                        updatedContactModel.setRelationship(etRelationship.getText().toString().trim());
                        updatedContactModel.setNickName(etNickName.getText().toString().trim());
                        updatedContactModel.setWorkInfo(etWorkInfo.getText().toString().trim());
                        updatedContactModel.setImageData(imageData);

                        boolean result = DataBaseHandler.getInstance(ContactFormActivity.this).updateContact(updatedContactModel);

                        if (result) {
                            Utils.navigateScreen(ContactFormActivity.this, DashboardActivity.class);
                            finish();
                        } else {
                            Utils.showToastMessage(ContactFormActivity.this, getString(R.string.something_went_wrong));
                        }
                    }
                }, HANDLER_DELAY);
            }
        });
        btnFab.setOnClickListener(v ->
                ImagePicker.with(ContactFormActivity.this)
                        .crop()
                        .start());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            ivProfile.setImageURI(imageUri);
            // Convert the image URI to a byte array and store it in the ContactModel object
            try {
                imageData = ImageUtils.convertUriToBase64String(this, imageUri);
                imageData = ImageUtils.compressImage(imageData);
            } catch (IOException e) {
                e.printStackTrace();
                Utils.showToastMessage(this, "Unable to set image to your profile.");
            }
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Utils.showToastMessage(this, ImagePicker.getError(data));
        }
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