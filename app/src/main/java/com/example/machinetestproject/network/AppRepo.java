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

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class AppRepo {

    @Inject
    APIServices apiServices;

    @Inject
    public AppRepo(){}

    /*
    * This function registers user to tha app.
    * */
    public Single<RegisterResponseModel> registerUser(RegisterRequestModel registerRequestModel){
        return apiServices.registerUser(registerRequestModel);
    }

    /*
    * This function login the user and fetches LoginResponseModel.
    * */
    public Single<LoginResponseModel> doLogin(LoginRequestModel loginRequestModel){
        return apiServices.doLogin(loginRequestModel);
    }

    /*
    * This function fetches product list of a particular user.
    * */
    public Single<GetAllProductsResponseModel> getAllProductsOfUser(GetAllProductRequestModel getAllProductRequestModel){
        return apiServices.getAllProducts(getAllProductRequestModel);
    }

    /*
    * This function fetches count of Products of a particular User
    * */
    public Single<ProductCountValueResponseModel> getProductCountOfUser(ProductCountValueRequestModel productCountValueRequestModel){
        return apiServices.getProductCountOfUser(productCountValueRequestModel);
    }

    /*
    * This function fetches total value of products of a particular User
    * */
    public Single<ProductCountValueResponseModel> getProductsValueOfUser(ProductCountValueRequestModel productCountValueRequestModel){
        return apiServices.getProductsValueOfUser(productCountValueRequestModel);
    }

    /*
    * This function updates Product details (quantity and Price)
    * */
    public Single<AddProductResponseModel> updateProduct(AddProductRequestModel addProductRequestModel){
        return apiServices.updateProductDetails(addProductRequestModel);
    }

    /*
    * This function adds Product for a particular User
    * */
    public Single<AddProductResponseModel> addProduct(AddProductRequestModel addProductRequestModel){
        return apiServices.addProduct(addProductRequestModel);
    }

    /*
    * This function deletes a Product of a particular user
    * */
    public Single<DeleteProductResponseModel> deleteProduct(DeleteProductRequestModel deleteProductRequestModel){
      return apiServices.deleteProduct(deleteProductRequestModel);
    }
}
