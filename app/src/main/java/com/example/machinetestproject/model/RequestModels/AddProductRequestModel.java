package com.example.machinetestproject.model.RequestModels;
import com.example.machinetestproject.model.Product;

public class AddProductRequestModel {
    private String userName;
    private Product product;

    public AddProductRequestModel(String userName, Product product) {
        this.userName = userName;
        this.product = product;
    }

    public AddProductRequestModel() {
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
