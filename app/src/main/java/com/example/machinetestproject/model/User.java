package com.example.machinetestproject.model;

public class User {

    private String userName;

    private String fullName;

    private String password;

    private String mobileNumber;

    private String email;

    public User(String userName, String fullName, String password, String mobileNumber, String email) {
        this.userName = userName;
        this.fullName = fullName;
        this.password = password;
        this.mobileNumber = mobileNumber;
        this.email = email;
    }

    public User() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
