package com.example.task_login_signup_screen.db;

import static android.content.Context.MODE_PRIVATE;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.AbstractWindowedCursor;
import android.database.Cursor;
import android.database.CursorWindow;
import android.database.sqlite.SQLiteBlobTooBigException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Base64;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.task_login_signup_screen.models.ContactModel;
import com.example.task_login_signup_screen.models.UserProfileModel;
import com.example.task_login_signup_screen.network.responses.Data;
import com.example.task_login_signup_screen.utils.Constants;

import java.util.ArrayList;

public class DataBaseHandler extends SQLiteOpenHelper {

    static DataBaseHandler dataBaseHandler;

    public static DataBaseHandler getInstance(Context context) {
        if (dataBaseHandler == null) {
            dataBaseHandler = new DataBaseHandler(context);
        } else {
            return dataBaseHandler;
        }
        return dataBaseHandler;
    }


    public DataBaseHandler(@Nullable Context context) {
        super(context, DBConstants.DB_NAME, null, DBConstants.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createContactTable = "CREATE TABLE " + DBConstants.TABLE_CONTACT + " (" + DBConstants.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DBConstants.COL_FULL_NAME + " VARCHAR, " + DBConstants.COL_PHONE + " VARCHAR, " + DBConstants.COL_EMAIL + " VARCHAR, " +
                DBConstants.COL_NICKNAME + " VARCHAR, " + DBConstants.COL_ADDRESS + " VARCHAR, " + DBConstants.COL_WORK_INFO + " VARCHAR, " +
                DBConstants.COL_RELATIONSHIP + " VARCHAR, " + DBConstants.COL_WEBSITE + " VARCHAR, " + DBConstants.COL_USERID + " INTEGER ," +
                DBConstants.COL_IMAGE + " BLOB)";
        db.execSQL(createContactTable);

        String createUserProfileTable = "CREATE TABLE " + DBConstants.USER_TABLE_PROFILE + " (" + DBConstants.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DBConstants.COL_FULL_NAME + " VARCHAR, " + DBConstants.COL_PHONE + " VARCHAR, " + DBConstants.COL_EMAIL + " VARCHAR, " +
                DBConstants.COL_USERID + " INTEGER," + DBConstants.COL_IMAGE + " BLOB)";
        Log.e("TAG", "onCreate: " + createUserProfileTable);
        db.execSQL(createUserProfileTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // this method is use to add new course to our sqlite database.
    public boolean addNewContact(ContactModel contactModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBConstants.COL_FULL_NAME, contactModel.getFullName());
        values.put(DBConstants.COL_PHONE, contactModel.getPhone());
        values.put(DBConstants.COL_EMAIL, contactModel.getEmail());
        values.put(DBConstants.COL_NICKNAME, contactModel.getNickName());
        values.put(DBConstants.COL_ADDRESS, contactModel.getAddress());
        values.put(DBConstants.COL_WORK_INFO, contactModel.getWorkInfo());
        values.put(DBConstants.COL_RELATIONSHIP, contactModel.getRelationship());
        values.put(DBConstants.COL_WEBSITE, contactModel.getWebsite());
        values.put(DBConstants.COL_USERID, contactModel.getUserId());
        String base64Image = Base64.encodeToString(contactModel.getImageData(), Base64.DEFAULT);
        values.put(DBConstants.COL_IMAGE, base64Image);

        long result = db.insert(DBConstants.TABLE_CONTACT, null, values);
        db.close();
        if (result > 0) {
            Log.d("DataBaseHandler", "New contact added successfully!");
        } else {
            Log.e("DataBaseHandler", "Failed to add new contact!");
        }
        return result > 0l;
    }

    public ArrayList<ContactModel> getAllContact(@NonNull Context context) {
        ArrayList<ContactModel> contactList = new ArrayList<>();
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.PREF_FILENAME, MODE_PRIVATE);
            int userId = sharedPreferences.getInt(Constants.PREF_USER_ID, -1);
            String query = "Select * from " + DBConstants.TABLE_CONTACT + " where " + DBConstants.COL_USERID + "=?";
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});
            if (cursor.moveToFirst()) {
                do {
                    ContactModel contactModel = new ContactModel();
                    contactModel.setId(cursor.getInt(0));
                    contactModel.setFullName(cursor.getString(1));
                    contactModel.setPhone(cursor.getString(2));
                    contactModel.setEmail(cursor.getString(3));
                    contactModel.setNickName(cursor.getString(4));
                    contactModel.setAddress(cursor.getString(5));
                    contactModel.setWorkInfo(cursor.getString(6));
                    contactModel.setRelationship(cursor.getString(7));
                    contactModel.setWebsite(cursor.getString(8));
                    contactModel.setUserId(cursor.getInt(9));
                    contactModel.setImageData(cursor.getBlob(10));
                    contactList.add(contactModel);
                } while (cursor.moveToNext());
            }
        } catch (SQLiteBlobTooBigException e) {
            Log.e("TAG", "getAllContact: " + e.getMessage());
            e.printStackTrace();
        }

        return contactList;
    }

    public boolean deleteContact(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int delete = db.delete(DBConstants.TABLE_CONTACT, DBConstants.COL_ID + "=? ", new String[]{String.valueOf(id)});
        db.close();
        return delete > 0;
    }

    public boolean updateContact(ContactModel contactModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBConstants.COL_FULL_NAME, contactModel.getFullName());
        values.put(DBConstants.COL_PHONE, contactModel.getPhone());
        values.put(DBConstants.COL_EMAIL, contactModel.getEmail());
        values.put(DBConstants.COL_NICKNAME, contactModel.getNickName());
        values.put(DBConstants.COL_ADDRESS, contactModel.getAddress());
        values.put(DBConstants.COL_WORK_INFO, contactModel.getWorkInfo());
        values.put(DBConstants.COL_RELATIONSHIP, contactModel.getRelationship());
        values.put(DBConstants.COL_WEBSITE, contactModel.getWebsite());
        String base64Image = Base64.encodeToString(contactModel.getImageData(), Base64.DEFAULT);
        values.put(DBConstants.COL_IMAGE, base64Image);
        int update = db.update(DBConstants.TABLE_CONTACT, values, DBConstants.COL_ID + "=?", new String[]{String.valueOf(contactModel.getId())});
        return update > 0;

    }

    public boolean saveUserDetails(UserProfileModel userProfileModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBConstants.COL_FULL_NAME, userProfileModel.getFullName());
        values.put(DBConstants.COL_PHONE, userProfileModel.getPhone());
        values.put(DBConstants.COL_EMAIL, userProfileModel.getEmail());
        if (userProfileModel.getImageData() != null) {
            String base64Image = Base64.encodeToString(userProfileModel.getImageData(), Base64.DEFAULT);
            values.put(DBConstants.COL_IMAGE, base64Image);
        }
        values.put(DBConstants.COL_USERID, userProfileModel.getUserId());
        long result = db.insert(DBConstants.USER_TABLE_PROFILE, null, values);
        db.close();
        if (result > 0) {
            Log.d("DataBaseHandler", "User's profile saved successfully! " + result);
        }
        return result > 0l;
    }

    public boolean updateUserProfile(UserProfileModel userProfileModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBConstants.COL_FULL_NAME, userProfileModel.getFullName());
        values.put(DBConstants.COL_PHONE, userProfileModel.getPhone());
        values.put(DBConstants.COL_EMAIL, userProfileModel.getEmail());
        if (userProfileModel.getImageData() != null) {
            String base64Image = Base64.encodeToString(userProfileModel.getImageData(), Base64.DEFAULT);
            values.put(DBConstants.COL_IMAGE, base64Image);
        }
        int update = db.update(DBConstants.USER_TABLE_PROFILE, values, DBConstants.COL_USERID + "=?", new String[]{String.valueOf(userProfileModel.getUserId())});
        return update > 0;
    }

    public boolean checkUserExistence(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select * from " + DBConstants.USER_TABLE_PROFILE + " where " + DBConstants.COL_EMAIL + "=? ";
        Cursor cursor = db.rawQuery(query, new String[]{email});
        boolean userExists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return userExists;
    }
    // Inside the DataBaseHandler class

    public UserProfileModel getUserData(int userId) {
        UserProfileModel userProfileModel = null;
        try {
            String query = "Select * from " + DBConstants.USER_TABLE_PROFILE + " where " + DBConstants.COL_USERID + "=?";
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});
            if (cursor != null && cursor.moveToFirst()) {
                userProfileModel = new UserProfileModel();
                userProfileModel.setId(cursor.getInt(0));
                userProfileModel.setFullName(cursor.getString(1));
                userProfileModel.setPhone(cursor.getString(2));
                userProfileModel.setEmail(cursor.getString(3));
                userProfileModel.setImageData(cursor.getBlob(5));
            }
            if (cursor != null) {
                cursor.close();
            }
        } catch (SQLiteBlobTooBigException e) {
            Log.e("TAG", "getAllContact: " + e.getMessage());
            e.printStackTrace();
        }
        return userProfileModel;
    }
}
