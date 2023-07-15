package com.example.task_login_signup_screen.Model;

public class ContactModel {
    public int image;
    public String name,number;

    public ContactModel(int image,String name,String number){
        this.image=image;
        this.name=name;
        this.number=number;
    }

    public ContactModel(String name,String number){
        this.name=name;
        this.number=number;
    }

}
