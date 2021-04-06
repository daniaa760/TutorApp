package com.example.tutorapp;

public class User {

    public String firstName, lastName, email, userType;

    public User(){

    }

    public User (String firstName, String lastName, String email){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String userType (String userType){
        this.userType = userType;
        return userType;
    }
}
