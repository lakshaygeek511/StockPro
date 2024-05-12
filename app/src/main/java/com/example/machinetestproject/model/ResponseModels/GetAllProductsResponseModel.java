package com.example.machinetestproject.model.ResponseModels;
import com.example.machinetestproject.model.Product;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetAllProductsResponseModel {

    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("productList")
    @Expose
    private List<Product> productList;

    public GetAllProductsResponseModel(int status, String message, List<Product> productList) {
        this.status = status;
        this.message = message;
        this.productList = productList;
    }

    public GetAllProductsResponseModel() {
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}
