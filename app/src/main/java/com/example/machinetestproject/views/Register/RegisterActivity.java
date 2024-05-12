package com.example.machinetestproject.views.Register;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.machinetestproject.R;
import com.example.machinetestproject.databinding.ActivityRegisterBinding;
import com.example.machinetestproject.model.RequestModels.RegisterRequestModel;
import com.example.machinetestproject.model.User;
import com.example.machinetestproject.utils.AlertDialogUtils;
import com.example.machinetestproject.utils.CommonUtils;
import com.example.machinetestproject.utils.Constants;
import com.example.machinetestproject.views.Login.LoginActivity;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding binding;

    RegisterViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(RegisterActivity.this,R.layout.activity_register);

        init();
        observers();
        listeners();

    }

    /*
    * This function has all initializations
    * */
    public void init(){
        getSupportActionBar().hide();
        CommonUtils.adjustStatusBarColor(RegisterActivity.this);

        viewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
    }

    /*
    * This function has all observers.
    * */
    public void observers(){
        /*
        * This function observes Live Data of Register Response Model on Register Screen
        * */
        viewModel.getRegisterResponseLiveData().observe(RegisterActivity.this, registerResponseModel -> {
            if(registerResponseModel.getStatus() != Constants.ERROR_RESPONSE){
                if(registerResponseModel.getStatus() == Constants.SUCCESS_RESPONSE){
                    AlertDialogUtils.showSuccessDialog(Constants.REGISTRATION_SUCCESS_MESSAGE,this);
                    resetForm();
                }
                else {
                    showToast(registerResponseModel.getMessage());
                }
            }
            else{
                showToast(Constants.SOMETHING_WENT_WRONG);
            }
        });
    }

    /*
    * This function has all listeners
    * */
    public void listeners(){
        binding.layoutLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //making register api call from Register Screen
        binding.signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkValidations()){
                    RegisterRequestModel registerRequestModel=new RegisterRequestModel();

                    User user=new User();
                    user.setUserName(binding.usernameEditText.getText().toString().trim());
                    user.setFullName(binding.fullNameEditText.getText().toString().trim());
                    user.setEmail(binding.emailEditText.getText().toString().trim());
                    user.setPassword(binding.passwordEditText.getText().toString().trim());
                    user.setMobileNumber(binding.mobileNoEditText.getText().toString().trim());

                    registerRequestModel.setUser(user);
                    viewModel.makeRegisterApiCall(registerRequestModel);
                }
            }
        });
    }

    /*
    * This function checks all validations of Register Screen Input Fields
    * */
    private boolean checkValidations() {
        if(!isValidUserName()) return false;
        else if(!isValidFullName()) return false;
        else if(!isValidEmail()) return false;
        else if(!isValidPhone()) return false;
        else if(!isValidPassword()) return false;
        return true;
    }

    /*
    * This function checks validations on UserName
    * */
    private boolean isValidUserName(){
        if(TextUtils.isEmpty(binding.usernameEditText.getText().toString().trim())){
            showToast(Constants.ENTER_USER_NAME_STR);
            return false;
        }
        else if(binding.usernameEditText.getText().toString().trim().length() > Constants.LONG_USER_NAME){
            showToast(Constants.LONG_USER_NAME_STR);
            return false;
        }
        return true;
    }

    /*
     * This function checks validations on Full Name
     * */
    private boolean isValidFullName(){
        if(TextUtils.isEmpty(binding.fullNameEditText.getText().toString().trim())){
            showToast(Constants.ENTER_USER_FULL_STR);
            return false;
        }
        else if(binding.fullNameEditText.getText().toString().trim().length() > Constants.LONG_FULL_NAME){
            showToast(Constants.LONG_FULL_NAME_STR);
            return false;
        }
        return true;
    }

    /*
     * This function checks validations on User Email
     * */
    private boolean isValidEmail(){
        if(TextUtils.isEmpty(binding.emailEditText.getText().toString().trim())){
            showToast(Constants.ENTER_EMAIL_STR);
            return false;
        }
        else if(!CommonUtils.isValidEmailFormat(binding.emailEditText.getText().toString().trim())){
            showToast(Constants.INVALID_EMAIL_STR);
            return false;
        }
        return true;
    }

    /*
     * This function checks validations on User Mobile Number
     * */
    private boolean isValidPhone(){
        if(TextUtils.isEmpty(binding.mobileNoEditText.getText().toString().trim())){
            showToast(Constants.ENTER_MOBILE_STR);
            return false;
        }
        else if(binding.mobileNoEditText.getText().toString().trim().length() != Constants.MOBILE_LENGTH){
            showToast(Constants.VALID_MOBILE_STR);
            return false;
        }
        return true;
    }

    /*
     * This function checks validations on User's Password
     * */
    private boolean isValidPassword(){
        if(TextUtils.isEmpty(binding.passwordEditText.getText().toString().trim())){
            showToast(Constants.ENTER_PASSWORD_STR);
            return false;
        }
        else if(binding.passwordEditText.getText().toString().trim().length() > Constants.LONG_USER_NAME){
            showToast(Constants.LONG_PASSWORD_STR);
            return false;
        }
        return true;
    }

    /*
    * This function resets Registration Form
    * */
    private void resetForm(){
        binding.usernameEditText.setText("");
        binding.fullNameEditText.setText("");
        binding.emailEditText.setText("");
        binding.mobileNoEditText.setText("");
        binding.passwordEditText.setText("");
    }

    /*
     * This function is used to show toast with the provided message.
     * */
    private void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}