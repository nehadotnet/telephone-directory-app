package com.example.task_login_signup_screen.view.activities;

import static com.example.task_login_signup_screen.utils.Utils.isValidEmail;
import static com.example.task_login_signup_screen.utils.Utils.navigateScreen;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.task_login_signup_screen.network.RetrofitClient;
import com.example.task_login_signup_screen.network.responses.RegisterResponse;
import com.example.task_login_signup_screen.R;
import com.example.task_login_signup_screen.network.requests.SignUpRequest;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {
    ImageView ivBack;
    TextInputEditText txtInputEditSignUpUsername, txtInputEditEmail, txtInputEditeSignupPassword;
    AppCompatButton btnSignUp;
    String email;
    String username;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        initUI();
        setUI();
    }

    private void initUI() {
        ivBack = findViewById(R.id.iv_back);
        txtInputEditSignUpUsername = findViewById(R.id.txt_edit_signup_username);
        txtInputEditEmail = findViewById(R.id.txt_edit_email_id);
        txtInputEditeSignupPassword = findViewById(R.id.txt_edit_signup_password);
        btnSignUp = findViewById(R.id.btn_sign_up);
    }

    private void setUI() {
        ivBack.setOnClickListener(v -> navigateScreen(this, LoginActivity.class));

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkValidation()) {
                    registerUser();
                }
            }

        });
    }

    private void registerUser() {


        // creating request using json class.
        SignUpRequest request = new SignUpRequest(username, email, password);

        Log.e("TAG",  request.toString());
        Call<RegisterResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .register(request);

        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.body() != null && response.isSuccessful()) {
                    RegisterResponse registerResponse = response.body();
                    Toast.makeText(SignupActivity.this, registerResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    navigateScreen(SignupActivity.this, LoginActivity.class);
                } else if (response.errorBody() != null) {
                    Log.e("TAG", "onResponse() returned: " + response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.getMessage());
            }
        });
    }

    public boolean checkValidation() {

        username = txtInputEditSignUpUsername.getText().toString();
        email = txtInputEditEmail.getText().toString();
        password = txtInputEditeSignupPassword.getText().toString();

        if (username.length() == 0) {
            txtInputEditSignUpUsername.setError(getString(R.string.enter_name));
            txtInputEditSignUpUsername.requestFocus();
            return false;
        }
        if (txtInputEditEmail.getText().toString().length() == 0) {
            txtInputEditEmail.setError(getString(R.string.enter_email_id));
            txtInputEditEmail.requestFocus();
            return false;
        }
        if (!isValidEmail(email)) {
            txtInputEditEmail.setError(getString(R.string.enter_valid_email_id));
            txtInputEditEmail.requestFocus();
            return false;
        }
        if (txtInputEditeSignupPassword.getText().toString().length() == 0) {
            txtInputEditeSignupPassword.setError(getString(R.string.enter_password));
            txtInputEditeSignupPassword.requestFocus();
            return false;
        }
        if (txtInputEditeSignupPassword.getText().toString().length() > 8) {
            txtInputEditeSignupPassword.setError(getString(R.string.password_can_not_exceed_8_characters));
            txtInputEditeSignupPassword.requestFocus();
            return false;
        }
        return true;
    }

}