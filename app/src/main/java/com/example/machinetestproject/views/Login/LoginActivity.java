package com.example.machinetestproject.views.Login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.machinetestproject.R;
import com.example.machinetestproject.database.sharedPref.SharedPref;
import com.example.machinetestproject.databinding.ActivityLoginBinding;
import com.example.machinetestproject.model.RequestModels.LoginRequestModel;
import com.example.machinetestproject.model.User;
import com.example.machinetestproject.utils.CommonUtils;
import com.example.machinetestproject.utils.Constants;
import com.example.machinetestproject.views.Dashboard.DashboardActivity;
import com.example.machinetestproject.views.Register.RegisterActivity;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;

    LoginViewModel viewModel;

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(LoginActivity.this,R.layout.activity_login);

        init();
        observers();
        listeners();

    }

    /*
    * This function has all initializations
    * */
    public void init(){
        getSupportActionBar().hide();
        CommonUtils.adjustStatusBarColor(LoginActivity.this);

        SharedPref.init(getApplicationContext());

        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
    }

    /*
    * This function observes all Live Data of Objects required on Login Screen
    * */
    public void observers(){
        //This function Observes LiveData of Login ResponseModel
        viewModel.getLoginResponseLivedata().observe(LoginActivity.this,loginResponseModel -> {
            if(loginResponseModel.getStatus() != Constants.ERROR_RESPONSE){
                if(loginResponseModel.getStatus() == Constants.SUCCESS_RESPONSE){
                    showToast(Constants.LOGIN_SUCCESS_MESSAGE);

                    user=loginResponseModel.getUser();
                    saveLoginDetailsInSharedPref();

                    Intent intent=new Intent(LoginActivity.this, DashboardActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    showToast(loginResponseModel.getMessage());
                }
            }
            else {
                showToast(Constants.SOMETHING_WENT_WRONG);
            }
        });
    }

    /*
    * This function has all Listeners.
    * */
    public void listeners(){
        binding.layoutRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        //making Login Api call on click of Login Button
        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkValidations()){
                    LoginRequestModel loginRequestModel = new LoginRequestModel();
                    loginRequestModel.setUserName(binding.userNameEditText.getText().toString().trim());
                    loginRequestModel.setPassword(binding.passwordEditText.getText().toString().trim());

                    viewModel.makeLoginApiCall(loginRequestModel);
                }
            }
        });
    }

    /*
     * This function checks all validations of Login Screen Input Fields
     * */
    private boolean checkValidations() {
        if(!isValidUserName()) return false;
        else if(!isValidPassword()) return false;
        return true;
    }

    /*
     * This function checks validations on UserName
     * */
    private boolean isValidUserName(){
        if(TextUtils.isEmpty(binding.userNameEditText.getText().toString().trim())){
            showToast(Constants.ENTER_USER_NAME_STR);
            return false;
        }
        else if(binding.userNameEditText.getText().toString().trim().length() > Constants.LONG_USER_NAME){
            showToast(Constants.LONG_USER_NAME_STR);
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
    * This function saves user's details in Shared Preferences
    * */
    public void saveLoginDetailsInSharedPref(){
        SharedPref.putString(Constants.USER_NAME,user.getUserName());
        SharedPref.putString(Constants.FULL_NAME,user.getFullName());
        SharedPref.putString(Constants.USER_EMAIL,user.getEmail());
        SharedPref.putString(Constants.USER_MOBILE,user.getMobileNumber());

        SharedPref.putBoolean(Constants.BOOL_USER_LOGGED_IN,true);
    }

    /*
     * This function is used to show toast with the provided message.
     * */
    private void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}