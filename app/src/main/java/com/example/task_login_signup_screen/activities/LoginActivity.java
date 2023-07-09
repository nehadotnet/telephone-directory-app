package com.example.task_login_signup_screen.activities;

import static com.example.task_login_signup_screen.utils.Utils.navigateScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.task_login_signup_screen.R;
import com.example.task_login_signup_screen.network.RetrofitClient;
import com.example.task_login_signup_screen.network.requests.SignInRequest;
import com.example.task_login_signup_screen.network.requests.SignUpRequest;
import com.example.task_login_signup_screen.network.responses.RegisterResponse;
import com.example.task_login_signup_screen.network.responses.SignInResponse;
import com.example.task_login_signup_screen.utils.Utils;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    TextView tvSignup, tvForgetPassword;
    TextInputEditText txtInputEditUsername, txtInputEditPassword;
    AppCompatButton btnLogin;

    String email;
    String password;

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

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkValidation()) {
                    loginUser();
                }
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
                    switchScreen(signInResponse);
                    Toast.makeText(LoginActivity.this, signInResponse.getData().getName(), Toast.LENGTH_LONG).show();
                    //navigateScreen(LoginActivity.this, DashboardActivity.class);
                } else if (response.errorBody() != null) {
                    Log.e("TAG", "onResponse() returned: " + response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<SignInResponse> call, Throwable t) {
                t.getStackTrace();
                Log.e("TAG", "onFailure: " + t.getMessage());
            }
        });
    }

    private void switchScreen(SignInResponse signInResponse) {
        Intent intent = new Intent(this, DashboardActivity.class);
        intent.putExtra("name", signInResponse.getData().getName());
        intent.putExtra("email", signInResponse.getData().getEmail());
        startActivity(intent);
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