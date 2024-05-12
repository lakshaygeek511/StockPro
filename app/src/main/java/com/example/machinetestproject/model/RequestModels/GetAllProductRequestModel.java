package com.example.machinetestproject.model.RequestModels;

public class GetAllProductRequestModel {
    private String userName;

    public GetAllProductRequestModel(String userName) {
        this.userName = userName;
    }

    public GetAllProductRequestModel() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
