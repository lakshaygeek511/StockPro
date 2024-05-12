package com.example.machinetestproject.model.RequestModels;

import com.example.machinetestproject.model.Product;

public class DeleteProductRequestModel {
    private String userName;
    private Product product;

    public DeleteProductRequestModel(String userName, Product product) {
        this.userName = userName;
        this.product = product;
    }

    public DeleteProductRequestModel() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
