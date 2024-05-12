package com.example.machinetestproject.model.RequestModels;

import com.example.machinetestproject.model.User;

public class RegisterRequestModel {
    private User user;

    public RegisterRequestModel(User user) {
        this.user = user;
    }

    public RegisterRequestModel() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
