package com.example.machinetestproject.views.UpdateProduct;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.machinetestproject.model.RequestModels.AddProductRequestModel;
import com.example.machinetestproject.model.ResponseModels.AddProductResponseModel;
import com.example.machinetestproject.network.AppRepo;
import com.example.machinetestproject.utils.CommonUtils;
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
public class UpdateProductViewModel extends AndroidViewModel {

    AppRepo appRepo;

    CompositeDisposable compositeDisposable=new CompositeDisposable();

    MutableLiveData<AddProductResponseModel> updateProductResponseMutableLiveData;

    @Inject
    public UpdateProductViewModel(@NonNull Application application, AppRepo appRepo) {
        super(application);
        this.appRepo=appRepo;

        updateProductResponseMutableLiveData=new MutableLiveData<>();
    }

    /*
    * This function makes update product api call
    * */
    public void makeUpdateProductApiCall(AddProductRequestModel addProductRequestModel){
        compositeDisposable.add(appRepo.updateProduct(addProductRequestModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<AddProductResponseModel>() {
                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull AddProductResponseModel updateProductResponseModel) {
                        updateProductResponseMutableLiveData.setValue(updateProductResponseModel);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        AddProductResponseModel errorInUpdateResponse = new AddProductResponseModel();
                        errorInUpdateResponse.setStatus(Constants.ERROR_RESPONSE);
                        errorInUpdateResponse.setMessage(Constants.SOMETHING_WENT_WRONG);

                        updateProductResponseMutableLiveData.setValue(errorInUpdateResponse);
                        e.printStackTrace();
                    }
                })
        );
    }

    /*
    * This function returns updateProductMutableLiveData to the Update Task activity
    * */
    public LiveData<AddProductResponseModel> getUpdateProductLiveData(){
        return updateProductResponseMutableLiveData;
    }
}
