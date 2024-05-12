package com.example.machinetestproject.views.Dashboard;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.machinetestproject.model.RequestModels.DeleteProductRequestModel;
import com.example.machinetestproject.model.RequestModels.GetAllProductRequestModel;
import com.example.machinetestproject.model.RequestModels.ProductCountValueRequestModel;
import com.example.machinetestproject.model.ResponseModels.DeleteProductResponseModel;
import com.example.machinetestproject.model.ResponseModels.GetAllProductsResponseModel;
import com.example.machinetestproject.model.ResponseModels.ProductCountValueResponseModel;
import com.example.machinetestproject.network.AppRepo;
import com.example.machinetestproject.utils.Constants;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * Project Name : MachineProjectProject
 *
 * @author VE00YM576
 * @company YMSLI
 * @date 05-07-2023
 * Copyright (c) 2021, Yamaha Motor Solutions (INDIA) Pvt Ltd.
 * Description
 * -----------------------------------------------------------------------------------
 * InventoryApp :
 * -----------------------------------------------------------------------------------
 * Revision History
 * -----------------------------------------------------------------------------------
 * Modified By          Modified On         Description
 * -----------------------------------------------------------------------------------
 */

@HiltViewModel
public class DashboardViewModel extends AndroidViewModel {

    AppRepo appRepo;

    CompositeDisposable compositeDisposable=new CompositeDisposable();

    MutableLiveData<GetAllProductsResponseModel> getAllProductsResponseMutableLiveData;
    MutableLiveData<ProductCountValueResponseModel> getProductCountMutableLiveData;
    MutableLiveData<ProductCountValueResponseModel> getProductsValueMutableLiveData;
    MutableLiveData<DeleteProductResponseModel> deleteProductResponseMutableLiveData;


    @Inject
    public DashboardViewModel(@NonNull Application application, AppRepo appRepo) {
        super(application);
        this.appRepo=appRepo;

        getAllProductsResponseMutableLiveData=new MutableLiveData<>();
        getProductCountMutableLiveData=new MutableLiveData<>();
        getProductsValueMutableLiveData=new MutableLiveData<>();
        deleteProductResponseMutableLiveData=new MutableLiveData<>();
    }

    /*
    * This function makes api cal to get product List of a particular User.
    * */
    public void makeGetAllProductsApiCall(GetAllProductRequestModel getAllProductRequestModel){
        compositeDisposable.add(appRepo.getAllProductsOfUser(getAllProductRequestModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<GetAllProductsResponseModel>() {
                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull GetAllProductsResponseModel getAllProductsResponseModel) {
                        getAllProductsResponseMutableLiveData.setValue(getAllProductsResponseModel);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        GetAllProductsResponseModel errorResponse = new GetAllProductsResponseModel();
                        errorResponse.setStatus(Constants.ERROR_RESPONSE);
                        errorResponse.setMessage(Constants.SOMETHING_WENT_WRONG);
                        errorResponse.setProductList(null);

                        getAllProductsResponseMutableLiveData.setValue(errorResponse);
                        e.printStackTrace();
                    }
                })
        );
    }

    /*
    * This function returns LiveData of GetAllProductsResponseModel
    * */
    public LiveData<GetAllProductsResponseModel> getAllProductsResponseLiveData(){
        return getAllProductsResponseMutableLiveData;
    }

    /*
    * This function makes api call to fetch count of User's Products.
    * */
    public void makeProductCountApiCall(ProductCountValueRequestModel productCountRequestModel){
        compositeDisposable.add(appRepo.getProductCountOfUser(productCountRequestModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ProductCountValueResponseModel>() {
                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull ProductCountValueResponseModel productCountResponseModel) {
                        getProductCountMutableLiveData.setValue(productCountResponseModel);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        ProductCountValueResponseModel errorProductCountResponse=new ProductCountValueResponseModel();
                        errorProductCountResponse.setStatus(Constants.ERROR_RESPONSE);
                        errorProductCountResponse.setMessage(Constants.SOMETHING_WENT_WRONG);
                        errorProductCountResponse.setValue(null);

                        getProductCountMutableLiveData.setValue(errorProductCountResponse);
                        e.printStackTrace();
                    }
                })
        );
    }

    /*
    * This function returns LiveData of ProductCountResponseModel to the Dashboard
    * */
    public LiveData<ProductCountValueResponseModel> getProductCountLiveData(){
        return getProductCountMutableLiveData;
    }

    /*
    * This function makes api call to fetch Products Value of a Particular user.
    * */
    public void makeProductValueApiCall(ProductCountValueRequestModel productsValueRequestModel){
        compositeDisposable.add(appRepo.getProductsValueOfUser(productsValueRequestModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ProductCountValueResponseModel>() {
                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull ProductCountValueResponseModel productsValueResponseModel) {
                        getProductsValueMutableLiveData.setValue(productsValueResponseModel);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        ProductCountValueResponseModel errorProductValueResponseModel=new ProductCountValueResponseModel();
                        errorProductValueResponseModel.setStatus(Constants.ERROR_RESPONSE);
                        errorProductValueResponseModel.setMessage(Constants.SOMETHING_WENT_WRONG);
                        errorProductValueResponseModel.setValue(null);

                        getProductsValueMutableLiveData.setValue(errorProductValueResponseModel);
                        e.printStackTrace();
                    }
                })
        );
    }

    /*
    * This function returns Live Data of ProductsValueResponseModel to the Dashboard
    * */
    public LiveData<ProductCountValueResponseModel> getProductValueLiveData(){
        return getProductsValueMutableLiveData;
    }

    /*
    * This function makes delete Product Api call and updates deleteProductResponseMutableLiveData.
    * */
    public void makeDeleteProductApiCall(DeleteProductRequestModel deleteProductRequestModel){
        compositeDisposable.add(appRepo.deleteProduct(deleteProductRequestModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<DeleteProductResponseModel>() {
                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull DeleteProductResponseModel deleteProductResponseModel) {
                        deleteProductResponseMutableLiveData.setValue(deleteProductResponseModel);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        DeleteProductResponseModel errorResponse=new DeleteProductResponseModel();
                        errorResponse.setStatus(Constants.ERROR_RESPONSE);
                        errorResponse.setMessage(Constants.SOMETHING_WENT_WRONG);

                        deleteProductResponseMutableLiveData.setValue(errorResponse);
                        e.printStackTrace();
                    }
                })
        );
    }

    /*
    * This function returns Live Data of DeleteProductResponseModel to the View class.
    * */
    public LiveData<DeleteProductResponseModel> getDeleteProductResponseLiveData(){
        return deleteProductResponseMutableLiveData;
    }

}
