package com.example.task_login_signup_screen.view.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.task_login_signup_screen.R;
import com.example.task_login_signup_screen.db.DataBaseHandler;
import com.example.task_login_signup_screen.models.ContactModel;
import com.example.task_login_signup_screen.models.UserProfileModel;
import com.example.task_login_signup_screen.utils.Constants;
import com.example.task_login_signup_screen.utils.ImageUtils;
import com.example.task_login_signup_screen.utils.Utils;
import com.example.task_login_signup_screen.view.activities.ContactFormActivity;
import com.example.task_login_signup_screen.view.activities.DashboardActivity;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {
    private FloatingActionButton fabBtn;
    private TextInputEditText etFullName, etPhone, etEmail;
    private AppCompatButton btnSave;
    private CircleImageView profileImage;
    private boolean isEditable = false;
    private Uri imageUri;
    SharedPreferences sharedPreferences;
    private byte[] imageData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        initUI(view);
        return view;
    }

    private void initUI(View view) {
        fabBtn = view.findViewById(R.id.btn_fab);
        etFullName = view.findViewById(R.id.et_full_name);
        etEmail = view.findViewById(R.id.et_email);
        etPhone = view.findViewById(R.id.et_phone);
        profileImage = view.findViewById(R.id.civ_profile_image);
        btnSave = view.findViewById(R.id.btn_save);
        etPhone.setEnabled(false);
        etFullName.setEnabled(false);
        etEmail.setEnabled(false);

        setUserDetails();
        saveUserProfile();
        profileImage.setImageResource(R.drawable.user);
        loadUserDetails();

    }

    private void loadUserDetails() {
        int userId = sharedPreferences.getInt(Constants.PREF_USER_ID, -1);
        UserProfileModel userProfileModel = DataBaseHandler.getInstance(requireContext()).getUserData(userId);
        if (userProfileModel != null) {
            Log.e("TAG", "UserProfileModel: " + userProfileModel.toString());
            // Load and set the user profile image
            byte[] imageData = userProfileModel.getImageData();
            if (imageData != null && imageData.length > 0) {
                Bitmap bitmap = ImageUtils.decodeBase64Image(imageData);
                profileImage.setImageBitmap(bitmap);
            }
            // Set the user details to the corresponding EditText controls
            etFullName.setText(userProfileModel.getFullName());
            etEmail.setText(userProfileModel.getEmail());
            etPhone.setText(userProfileModel.getPhone());
        }
    }


    private void saveUserProfile() {
        fabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(ProfileFragment.this)
                        .saveDir(requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES))
                        .crop()
                        .createIntent(intent -> {
                            imagePickerLauncher.launch(intent);
                            return null;
                        });
                etFullName.setEnabled(true);
                etPhone.setEnabled(true);
                btnSave.setVisibility(View.VISIBLE);

            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int userId = sharedPreferences.getInt(Constants.PREF_USER_ID, -1);
                UserProfileModel userProfileModel = new UserProfileModel();
                userProfileModel.setFullName(etFullName.getText().toString().trim());
                userProfileModel.setPhone(etPhone.getText().toString().trim());
                userProfileModel.setEmail(etEmail.getText().toString().trim());
                if (imageData != null && imageData.length > 0)
                    userProfileModel.setImageData(imageData);
                userProfileModel.setUserId(userId);
                boolean result = DataBaseHandler.getInstance(requireContext()).updateUserProfile(userProfileModel);
                if (result) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(Constants.PREF_NAME, etFullName.getText().toString().trim());
                    editor.apply();
                    Utils.navigateScreen(requireContext(), DashboardActivity.class);
                } else {
                    Utils.showToastMessage(requireContext(), getString(R.string.something_went_wrong));
                }
            }
        });
    }

    private final ActivityResultLauncher<Intent> imagePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                Intent data = result.getData();
                if (result.getResultCode() == Activity.RESULT_OK && data != null) {
                    int resultCode = result.getResultCode();
                    Uri imageUri = data.getData();
                    profileImage.setImageURI(imageUri);
                    try {
                        imageData = ImageUtils.convertUriToBase64String(requireContext(), imageUri);
                        imageData = ImageUtils.compressImage(imageData);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Utils.showToastMessage(requireContext(), "Unable to set image to your profile.");
                    }
                    // Rest of the code to handle the image URI
                } else if (result.getResultCode() == ImagePicker.RESULT_ERROR) {
                    Utils.showToastMessage(requireContext(), ImagePicker.getError(data));
                }
            }
    );

    private void setUserDetails() {
        sharedPreferences = requireContext().getSharedPreferences(Constants.PREF_FILENAME, Context.MODE_PRIVATE);
        String email = sharedPreferences.getString(Constants.PREF_EMAIL, "");
        String name = sharedPreferences.getString(Constants.PREF_NAME, "");
        etFullName.setText(name);
        etEmail.setText(email);
    }

}