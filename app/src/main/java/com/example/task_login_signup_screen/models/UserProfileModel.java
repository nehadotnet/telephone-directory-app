package com.example.task_login_signup_screen.models;

import java.util.Arrays;

public class UserProfileModel {
    private int Id, userId;
    private String fullName, phone, email;
    private byte[] imageData;

    public UserProfileModel(int id, int userId, String fullName, String phone, String email, byte[] imageData) {
        Id = id;
        this.userId = userId;
        this.fullName = fullName;
        this.phone = phone;
        this.email = email;
        this.imageData = imageData;
    }

    public UserProfileModel() {

    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    @Override
    public String toString() {
        return "UserProfileModel{" +
                "Id=" + Id +
                ", userId=" + userId +
                ", fullName='" + fullName + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", imageData=" + Arrays.toString(imageData) +
                '}';
    }
}
