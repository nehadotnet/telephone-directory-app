package com.example.task_login_signup_screen.models;

import java.io.Serializable;

public class ContactModel implements Serializable {
    private int Id;
    private String fullName, phone, email, nickName, address, workInfo, relationship, website;

    public ContactModel() {
    }
    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public ContactModel(int Id, String fullName, String phone, String email, String nickName, String address, String workInfo, String relationship, String website) {
        this.Id = Id;
        this.fullName = fullName;
        this.phone = phone;
        this.email = email;
        this.nickName = nickName;
        this.address = address;
        this.workInfo = workInfo;
        this.relationship = relationship;
        this.website = website;
    }

    @Override
    public String toString() {
        return "ContactModel{" +
                "Id=" + Id +
                ", fullName='" + fullName + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", nickName='" + nickName + '\'' +
                ", address='" + address + '\'' +
                ", workInfo='" + workInfo + '\'' +
                ", relationship='" + relationship + '\'' +
                ", website='" + website + '\'' +
                '}';
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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWorkInfo() {
        return workInfo;
    }

    public void setWorkInfo(String workInfo) {
        this.workInfo = workInfo;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
}
