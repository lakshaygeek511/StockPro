package com.example.machinetestproject.views.AddProduct;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.machinetestproject.model.RequestModels.AddProductRequestModel;
import com.example.machinetestproject.model.ResponseModels.AddProductResponseModel;
import com.example.machinetestproject.network.AppRepo;
import com.example.machinetestproject.utils.Constants;

import java.util.ConcurrentModificationException;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import hilt_aggregated_deps._com_example_machinetestproject_di_AppModule;
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
public class AddProductViewModel extends AndroidViewModel {

    AppRepo appRepo;

    CompositeDisposable compositeDisposable=new CompositeDisposable();

    MutableLiveData<AddProductResponseModel> addProductResponseMutableLiveData;

    @Inject
    public AddProductViewModel(@NonNull Application application, AppRepo appRepo) {
        super(application);
        this.appRepo=appRepo;

        addProductResponseMutableLiveData=new MutableLiveData<>();
    }

    /*
    * This function makes api call to add Product.
    * */
    public void makeAddProductApiCall(AddProductRequestModel addProductRequestModel){
        compositeDisposable.add(appRepo.addProduct(addProductRequestModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<AddProductResponseModel>() {
                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull AddProductResponseModel addProductResponseModel) {
                        addProductResponseMutableLiveData.setValue(addProductResponseModel);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        AddProductResponseModel errorResponse=new AddProductResponseModel();
                        errorResponse.setStatus(Constants.ERROR_RESPONSE);
                        errorResponse.setMessage(Constants.SOMETHING_WENT_WRONG);

                        addProductResponseMutableLiveData.setValue(errorResponse);
                        e.printStackTrace();
                    }
                })
        );
    }

    /*
    * This function returns LiveData of AddProductResponseModel
    * */
    public LiveData<AddProductResponseModel> getAddProductResponseLiveData(){
        return addProductResponseMutableLiveData;
    }
}
