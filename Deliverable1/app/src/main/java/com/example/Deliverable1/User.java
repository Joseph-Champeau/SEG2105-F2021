package com.example.Deliverable1;

import android.widget.RadioButton;

public class User {

    public String fullName;
    public String age;
    public String email;
    public String username;
    public String type;

    public User(){

    }

    public User(String fullName,String age,String email,String username,String type){
        this.fullName = fullName;
        this.age=age;
        this.email=email;
        this.username=username;
        this.type=type;
    }
}
