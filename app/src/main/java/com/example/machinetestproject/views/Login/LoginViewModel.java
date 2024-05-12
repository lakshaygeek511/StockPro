package com.example.machinetestproject.views.Login;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.machinetestproject.model.RequestModels.LoginRequestModel;
import com.example.machinetestproject.model.ResponseModels.LoginResponseModel;
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
public class LoginViewModel extends AndroidViewModel {

    AppRepo appRepo;

    CompositeDisposable compositeDisposable=new CompositeDisposable();

    MutableLiveData<LoginResponseModel> loginResponseMutableLiveData;

    @Inject
    public LoginViewModel(@NonNull Application application, AppRepo appRepo) {
        super(application);
        this.appRepo=appRepo;

        loginResponseMutableLiveData=new MutableLiveData<>();
    }

    /*
    * This function makes LoginAPI call sets value in LoginResponse Mutable LiveData
    * */
    public void makeLoginApiCall(LoginRequestModel loginRequestModel){
        compositeDisposable.add(appRepo.doLogin(loginRequestModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<LoginResponseModel>() {
                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull LoginResponseModel loginResponseModel) {
                        loginResponseMutableLiveData.setValue(loginResponseModel);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        LoginResponseModel errorResponseModel = new LoginResponseModel();
                        errorResponseModel.setStatus(Constants.ERROR_RESPONSE);
                        errorResponseModel.setMessage(Constants.SOMETHING_WENT_WRONG);
                        errorResponseModel.setUser(null);

                        loginResponseMutableLiveData.setValue(errorResponseModel);
                        e.printStackTrace();
                    }
                })
        );
    }

    /*
    * This function returns LiveData of LoginResponse Model to Login Screen
    * */
    public LiveData<LoginResponseModel> getLoginResponseLivedata(){
        return loginResponseMutableLiveData;
    }
}
