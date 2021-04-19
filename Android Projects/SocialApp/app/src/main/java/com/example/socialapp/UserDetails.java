package com.example.socialapp;

public class UserDetails {
    private String email;
    private String password;
    private  String username;

    public void setLoginDetails(String email, String password){
        this.email = email;
        this.password = password;
    }

    public void setSignUpDetails(String email, String password, String username){
        this.email = email;
        this.password = password;
        this.username = username;
    }

    public String getEmail(){
        return this.email;
    }
    public String getUsername(){
        return this.username;
    }
    public String getPassword(){
        return this.password;
    }
}
