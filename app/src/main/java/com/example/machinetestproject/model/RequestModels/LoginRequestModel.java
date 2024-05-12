package com.example.machinetestproject.model.RequestModels;

public class LoginRequestModel {
    private String userName;

    private String password;

    public LoginRequestModel(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public LoginRequestModel() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
