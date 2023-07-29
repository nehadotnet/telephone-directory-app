package com.example.task_login_signup_screen.utils;

    import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import java.util.regex.Pattern;

public class Utils {


    public static boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    public static void navigateScreen(Context context, Class<? extends Activity> activityClass) {
        Intent intent = new Intent(context, activityClass);
        context.startActivity(intent);
    }

    public static void showToastMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void dialContact(Context context, String phoneNumber) {
        Intent iDial = new Intent();
        iDial.setAction(Intent.ACTION_DIAL);
        iDial.setData(Uri.parse(Constants.TEL + phoneNumber));
        context.startActivity(iDial);
    }
}
