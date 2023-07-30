package com.example.task_login_signup_screen.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImageUtils {
    public static Bitmap decodeBase64Image(byte[] imageData) {
        byte[] decodedImage = Base64.decode(imageData, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedImage, 0, decodedImage.length);
    }

    public static byte[] convertUriToBase64String(Context context, Uri imageUri) throws IOException {
        InputStream inputStream = context.getContentResolver().openInputStream(imageUri);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, bytesRead);
        }
        inputStream.close();
        byte[] imageData = byteArrayOutputStream.toByteArray();
        byteArrayOutputStream.close();
        return imageData;
    }

    // code for compressing image
    public static byte[] compressImage(byte[] imageData) {
        while (imageData.length > 500000) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
            Bitmap resized = Bitmap.createScaledBitmap(bitmap, (int) (bitmap.getWidth() * 0.8), (int) (bitmap.getHeight() * 0.8), true);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            resized.compress(Bitmap.CompressFormat.PNG, 100, stream);
            imageData = stream.toByteArray();
        }
        return imageData;
    }
}
