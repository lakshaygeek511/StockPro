package com.example.machinetestproject.utils;

/*
* This file has constants used
* */
public class Constants {
    public static final String BASE_URL="http://10.0.2.2:8080/";

    public static final String SOMETHING_WENT_WRONG="Something went Wrong!";

    public static final String DB_NAME="MachineTestDb";

    public static final String REGISTRATION_SUCCESS_MESSAGE="Registration Successful";
    public static final int SUCCESS_RESPONSE=1;
    public static final String REGISTRATION_FAIL_MESSAGE="Registration Failed";
    public static final String USER_ALREADY_REGISTERED="User Name Already Registered";
    public static final String EMAIL_ALREADY_REGISTERED="Email id Already Registered";
    public static final int USER_ALREADY_REGISTERED_STATUS=2;
    public static final int EMAIL_ALREADY_REGISTERED_STATUS=3;
    public static final int ERROR_RESPONSE=-1;

    //For Login
    public static final int LOGIN_SUCCESS_STATUS=1;
    public static final int USER_NOT_FOUND_STATUS=0;
    public static final int USER_FOUND_INCORRECT_PASSWORD_STATUS=-1;

    public static final String LOGIN_SUCCESS_MESSAGE="Login Successful";
    public static final String USER_NOT_FOUND_MESSAGE="User Not Registered! Register Yourself";
    public static final String USER_FOUND_INCORRECT_PASSWORD_MESSAGE="Incorrect Password";



    public static final String RECORDS_FOUND_MESSAGE ="Records Found";
    public static final String UNAUTHORIZED_MESSAGE="Sorry! you are not Authorized to access this data.";


    //For Shared Preferences
    public static final String USER_NAME="User name";
    public static final String FULL_NAME="Full name";
    public static final String USER_EMAIL="User Email";
    public static final String USER_MOBILE="User Mobile";
    public static final String BOOL_USER_LOGGED_IN="User Log in Status";


    public static final String HI="Hi! ";
    public static final String PRODUCT_SHARE="Product Share";
    public static final String DELETE_PRODUCT="Delete Product";
    public static final String DELETE_PRODUCT_SUCCESS="Product Deleted Successfully!";
    public static final String DELETE_PRODUCT_MESSAGE="Are you sure you want to delete this Product?";
    public static final String LOGOUT_MESSAGE="Are you sure you want to logout?";
    public static final String LOGOUT="Logout";
    public static final String DELETE_ACCOUNT="Delete Account";
    public static final String DELETE_ACCOUNT_SUCCESS="Account Deleted Successfully!";
    public static final String DELETE_ACCOUNT_MESSAGE="Once Deleted account can't be recovered!";

    public static final int LOW_QTY=10;
    public static final int MEDIUM_QTY=20;
    public static final int HIGH_QTY=30;

    public static final int LONG_PRODUCT_NAME=30;
    public static final int MOBILE_LENGTH=10;
    public static final int LONG_FULL_NAME=40;
    public static final int LONG_USER_NAME=25;
    public static final String LONG_USER_NAME_STR="UserName Length too long";
    public static final String LONG_PRODUCT_NAME_STR="Product Name too long";
    public static final String LONG_PASSWORD_STR="Password Length too long";
    public static final String LONG_FULL_NAME_STR="Full Name Length too long";
    public static final String ENTER_PRODUCT_PRICE_STR="Please enter Product Price";
    public static final String ENTER_PRODUCT_NAME_STR="Please enter Product Name";
    public static final String ENTER_PRODUCT_QTY_STR="Please enter Product quantity";
    public static final String ENTER_USER_NAME_STR="Please enter UserName";
    public static final String ENTER_EMAIL_STR="Please enter your email";
    public static final String INVALID_EMAIL_STR="Invalid Email Format";
    public static final String VALID_MOBILE_STR="Please enter a valid Mobile Number";
    public static final String ENTER_USER_FULL_STR="Please enter Full Name";
    public static final String ENTER_MOBILE_STR="Please enter Mobile Number";
    public static final String ENTER_PASSWORD_STR="Please enter password";
    public static final String UPDATE_PRODUCT_QTY_PRICE_STR="Please update price or Quantity";




}
