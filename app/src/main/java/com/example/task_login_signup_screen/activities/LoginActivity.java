package com.example.task_login_signup_screen.activities;

import static com.example.task_login_signup_screen.utils.Utils.navigateScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.task_login_signup_screen.R;
import com.example.task_login_signup_screen.network.RetrofitClient;
import com.example.task_login_signup_screen.network.requests.SignInRequest;
import com.example.task_login_signup_screen.network.responses.SignInResponse;
import com.example.task_login_signup_screen.utils.Constants;
import com.example.task_login_signup_screen.utils.Utils;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    TextView tvSignup, tvForgetPassword;
    TextInputEditText txtInputEditUsername, txtInputEditPassword;
    AppCompatButton btnLogin;

    private String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initUI();
        setUI();

    }

    private void initUI() {
        tvSignup = findViewById(R.id.tv_sign_up);
        tvForgetPassword = findViewById(R.id.tv_forget_password);
        txtInputEditUsername = findViewById(R.id.txt_edit_username);
        txtInputEditPassword = findViewById(R.id.txt_edit_password);
        btnLogin = findViewById(R.id.btn_login);
    }

    private void setUI() {
        tvSignup.setOnClickListener(v -> navigateScreen(this, SignupActivity.class));

        tvForgetPassword.setOnClickListener(v -> navigateScreen(this, CreateNewPasswordActivity.class));

        btnLogin.setOnClickListener(v -> {
            if (checkValidation()) {
                loginUser();
            }
        });
    }

    private void loginUser() {
        // creating request using json class.
        SignInRequest request = new SignInRequest(email, password);

        Log.e("TAG", request.toString());
        Call<SignInResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .loginUser(request);

        call.enqueue(new Callback<SignInResponse>() {
            @Override
            public void onResponse(Call<SignInResponse> call, Response<SignInResponse> response) {
                if (response.body() != null && response.isSuccessful()) {
                    SignInResponse signInResponse = response.body();
                    Log.e("TAG", "onResponse: " + signInResponse.toString());
                    if (signInResponse.getData() != null) {
                        saveLoginState(signInResponse);
                        switchScreen(signInResponse);
                    } else  {
                        Utils.showToastMessage(LoginActivity.this, signInResponse.getMessage());
                    }
                } else if (response.errorBody() != null) {
                    try {
                        Log.e("TAG", "onResponse() returned: " + response.errorBody().string());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<SignInResponse> call, Throwable t) {
                t.getStackTrace();
                Log.e("TAG", "onFailure: " + t.getMessage());
            }
        });
    }

    private void saveLoginState(SignInResponse signInResponse) {
        if (signInResponse != null && signInResponse.getData() != null) {
            SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREF_FILENAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(Constants.PREF_TOKEN, signInResponse.getData().getToken());
            editor.putString(Constants.PREF_EMAIL, signInResponse.getData().getEmail());
            editor.putString(Constants.PREF_NAME, signInResponse.getData().getName());
            editor.putInt(Constants.PREF_USER_ID, signInResponse.getData().getId());
            editor.apply();
        }
    }

    private void switchScreen(SignInResponse signInResponse) {
        Intent intent = new Intent(this, DashboardActivity.class);
        intent.putExtra(Constants.PREF_NAME, signInResponse.getData().getName());
        intent.putExtra(Constants.PREF_EMAIL, signInResponse.getData().getEmail());
        startActivity(intent);
        finishAffinity();
    }


    // method to validate controls
    public boolean checkValidation() {

        email = txtInputEditUsername.getText().toString();
        password = txtInputEditPassword.getText().toString();

        if (txtInputEditUsername.getText().toString().length() == 0) {
            txtInputEditUsername.setError(getString(R.string.required_username));
            txtInputEditUsername.requestFocus();
            return false;
        }

        if (txtInputEditPassword.getText().toString().length() == 0) {
            txtInputEditPassword.setError(getString(R.string.required_password));
            txtInputEditPassword.requestFocus();
            return false;
        }

        if (txtInputEditPassword.getText().toString().length() > 8) {
            txtInputEditPassword.setError(getString(R.string.password_can_not_exceed_8_characters));
            txtInputEditPassword.requestFocus();
            return false;
        }

        return true;
    }

}