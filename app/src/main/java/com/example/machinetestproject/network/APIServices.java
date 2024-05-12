package com.example.machinetestproject.network;

import com.example.machinetestproject.model.RequestModels.AddProductRequestModel;
import com.example.machinetestproject.model.RequestModels.DeleteProductRequestModel;
import com.example.machinetestproject.model.RequestModels.GetAllProductRequestModel;
import com.example.machinetestproject.model.RequestModels.LoginRequestModel;
import com.example.machinetestproject.model.RequestModels.ProductCountValueRequestModel;
import com.example.machinetestproject.model.RequestModels.RegisterRequestModel;
import com.example.machinetestproject.model.ResponseModels.AddProductResponseModel;
import com.example.machinetestproject.model.ResponseModels.DeleteProductResponseModel;
import com.example.machinetestproject.model.ResponseModels.GetAllProductsResponseModel;
import com.example.machinetestproject.model.ResponseModels.LoginResponseModel;
import com.example.machinetestproject.model.ResponseModels.ProductCountValueResponseModel;
import com.example.machinetestproject.model.ResponseModels.RegisterResponseModel;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface APIServices {

    /*
    * Register User Api.
    * */
    @POST("/register/registerUser")
    Single<RegisterResponseModel> registerUser(@Body RegisterRequestModel registerRequestModel);

    /*
    * Login User Api
    * */
    @POST("/login/doLogin")
    Single<LoginResponseModel> doLogin(@Body LoginRequestModel loginRequestModel);

    /*
    * get All Products of a particular User
    * */
    @POST("/dashboard/getAllProducts")
    Single<GetAllProductsResponseModel> getAllProducts(@Body GetAllProductRequestModel getAllProductRequestModel);

    /*
    * get Product count of a particular User
    * */
    @POST("/dashboard/getProductCount")
    Single<ProductCountValueResponseModel> getProductCountOfUser(@Body ProductCountValueRequestModel productCountValueRequestModel);

    /*
    * get Total value of Products of a particular User
    * */
    @POST("/dashboard/getProductValue")
    Single<ProductCountValueResponseModel> getProductsValueOfUser(@Body ProductCountValueRequestModel productCountValueRequestModel);

    /*
    * update product quantity and price
    * */
    @POST("/dashboard/updateProduct")
    Single<AddProductResponseModel> updateProductDetails(@Body AddProductRequestModel addProductRequestModel);

    /*
    * add Product for a Particular User
    * */
    @POST("/dashboard/addProduct")
    Single<AddProductResponseModel> addProduct(@Body AddProductRequestModel addProductRequestModel);

    /*
    * delete Product of a Particular user
    * */
    @POST("/dashboard/deleteProduct")
    Single<DeleteProductResponseModel> deleteProduct(@Body DeleteProductRequestModel deleteProductRequestModel);

}
