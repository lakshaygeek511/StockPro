package com.example.machinetestproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.machinetestproject.database.sharedPref.SharedPref;
import com.example.machinetestproject.utils.Constants;
import com.example.machinetestproject.views.Dashboard.DashboardActivity;
import com.example.machinetestproject.views.Login.LoginActivity;
import com.google.android.gms.maps.model.Dash;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getSupportActionBar().hide();
        SharedPref.init(getApplicationContext());
        moveFromSplashScreen();
    }

    /*
    * This function navigates user to screens based on userLogin Status.
    * If user is Logged In he/she moves directly to Dashboard
    * else Login Screen.
    * */
    private void moveFromSplashScreen() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(userDetailsSaved()){
                    moveToDashboard();
                }
                else{
                    moveToLoginScreen();
                }
            }

        },3000);
    }

    /*
    * This function checks whether user details are saved in Shared Preferences
    * */
    public boolean userDetailsSaved(){
        return SharedPref.getBoolean(Constants.BOOL_USER_LOGGED_IN,false);
    }

    /*
    * This function is used to navigate the user to Login Screen
    * */
    public void moveToLoginScreen(){
        Intent intent=new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    /*
     * This function is used to navigate the user to Dashboard Screen
     * */
    public void moveToDashboard(){
        Intent intent=new Intent(SplashActivity.this, DashboardActivity.class);
        startActivity(intent);
        finish();
    }
}