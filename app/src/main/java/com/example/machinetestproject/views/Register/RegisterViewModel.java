package com.example.machinetestproject.views.Register;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.machinetestproject.model.RequestModels.RegisterRequestModel;
import com.example.machinetestproject.model.ResponseModels.RegisterResponseModel;
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
public class RegisterViewModel extends AndroidViewModel {

    AppRepo appRepo;

    CompositeDisposable compositeDisposable=new CompositeDisposable();

    private MutableLiveData<RegisterResponseModel> registerResponseMutableLiveData;

    @Inject
    public RegisterViewModel(@NonNull Application application, AppRepo appRepo) {
        super(application);
        this.appRepo=appRepo;
        registerResponseMutableLiveData=new MutableLiveData<>();
    }

    /*
    * This function makes register api call
    * */
    public void makeRegisterApiCall(RegisterRequestModel registerRequestModel){
        compositeDisposable.add(appRepo.registerUser(registerRequestModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<RegisterResponseModel>() {
                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull RegisterResponseModel registerResponseModel) {
                        registerResponseMutableLiveData.setValue(registerResponseModel);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        RegisterResponseModel errorResponse = new RegisterResponseModel();
                        errorResponse.setStatus(Constants.ERROR_RESPONSE);
                        errorResponse.setMessage(Constants.SOMETHING_WENT_WRONG);

                        registerResponseMutableLiveData.setValue(errorResponse);
                        e.printStackTrace();
                    }
                })
        );
    }

    /*
    * This function returns LiveData of RegisterResponseModel to the view i.e. Register Activity
    * */
    public LiveData<RegisterResponseModel> getRegisterResponseLiveData(){
        return registerResponseMutableLiveData;
    }
}
