package com.example.task_login_signup_screen.activities;

import static com.example.task_login_signup_screen.utils.Constants.HANDLER_DELAY;
import static com.example.task_login_signup_screen.utils.Utils.navigateScreen;
import static com.example.task_login_signup_screen.utils.Utils.showToastMessage;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.task_login_signup_screen.R;
import com.google.android.material.textfield.TextInputEditText;

public class CreateNewPasswordActivity extends AppCompatActivity {
    TextInputEditText txtInputEditCreateNewPassword, txtInputEditConfirmPassword;
    Button btnResetPassword;
    RelativeLayout relativeLayoutProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_password);

        initUI();
        setUI();
    }

    private void initUI() {
        txtInputEditCreateNewPassword = findViewById(R.id.txt_input_edit_new_password);
        txtInputEditConfirmPassword = findViewById(R.id.txt_input_edit_confirm_password);
        relativeLayoutProgressBar = findViewById(R.id.relativelayout_progressbar);
        btnResetPassword = findViewById(R.id.btn_reset_password);

    }

    private void setUI() {
        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkValidation()){
                    relativeLayoutProgressBar.setVisibility(View.VISIBLE);
                    new Handler().postDelayed(() -> {
                        relativeLayoutProgressBar.setVisibility(View.GONE);
                        showToastMessage(CreateNewPasswordActivity.this, getString(R.string.password_set_successfully));
                        navigateScreen(CreateNewPasswordActivity.this, LoginActivity.class);
                    }, HANDLER_DELAY);
                }
            }

        });
    }

    public boolean checkValidation() {
        String createPassword = txtInputEditCreateNewPassword.getText().toString();
        String confirmPassword = txtInputEditConfirmPassword.getText().toString();


        if (createPassword.length() == 0) {
            txtInputEditCreateNewPassword.setError(getString(R.string.enter_new_password));
            txtInputEditCreateNewPassword.requestFocus();
            return false;
        }
        if (confirmPassword.length() == 0) {
            txtInputEditConfirmPassword.setError(getString(R.string.enter_confirm_password));
            txtInputEditConfirmPassword.requestFocus();
            return false;
        }
        if (!confirmPassword.equals(createPassword)) {
            txtInputEditConfirmPassword.setError(getString(R.string.password_do_not_match));
            txtInputEditConfirmPassword.requestFocus();
            return false;
        }
        if (createPassword.length() > 8) {
            txtInputEditCreateNewPassword.setError(getString(R.string.password_can_not_exceed_8_characters));
            txtInputEditCreateNewPassword.requestFocus();
            return false;
        }
        if (confirmPassword.length() > 8) {
            txtInputEditConfirmPassword.setError(getString(R.string.password_can_not_exceed_8_characters));
            txtInputEditConfirmPassword.requestFocus();
            return false;
        }
        return true;
    }
}