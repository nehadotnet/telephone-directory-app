package com.example.task_login_signup_screen.db;

public interface DbQueries {
    String createUserProfileTable = "CREATE TABLE " + DBConstants.USER_TABLE_PROFILE + " (" + DBConstants.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            DBConstants.COL_FULL_NAME + " VARCHAR, " + DBConstants.COL_PHONE + " VARCHAR, " + DBConstants.COL_EMAIL + " VARCHAR, " +
            DBConstants.COL_USERID + " INTEGER," + DBConstants.COL_IMAGE + " BLOB)";
    String createContactTable = "CREATE TABLE " + DBConstants.TABLE_CONTACT + " (" + DBConstants.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            DBConstants.COL_FULL_NAME + " VARCHAR, " + DBConstants.COL_PHONE + " VARCHAR, " + DBConstants.COL_EMAIL + " VARCHAR, " +
            DBConstants.COL_NICKNAME + " VARCHAR, " + DBConstants.COL_ADDRESS + " VARCHAR, " + DBConstants.COL_WORK_INFO + " VARCHAR, " +
            DBConstants.COL_RELATIONSHIP + " VARCHAR, " + DBConstants.COL_WEBSITE + " VARCHAR, " + DBConstants.COL_USERID + " INTEGER ," +
            DBConstants.COL_IMAGE + " BLOB)";
}
