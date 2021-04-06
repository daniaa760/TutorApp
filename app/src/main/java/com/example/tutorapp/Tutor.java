package com.example.tutorapp;

public class Tutor {

    public String firstName, lastName, email, bio;

    public Tutor(){

    }

    public Tutor (String firstName, String lastName, String email){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }


    public String getFirtName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBio(String bio){
        this.bio = bio;
    }

    public String getBio(){
        return bio;
    }
}
