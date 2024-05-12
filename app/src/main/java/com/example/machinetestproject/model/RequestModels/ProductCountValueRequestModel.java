package com.example.machinetestproject.model.RequestModels;

public class ProductCountValueRequestModel {

    private String userName;

    public ProductCountValueRequestModel(String userName) {
        this.userName = userName;
    }

    public ProductCountValueRequestModel() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
