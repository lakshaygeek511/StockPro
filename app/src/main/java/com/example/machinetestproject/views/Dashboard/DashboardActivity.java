package com.example.machinetestproject.views.Dashboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.machinetestproject.R;
import com.example.machinetestproject.adapter.ProductAdapter;
import com.example.machinetestproject.database.sharedPref.SharedPref;
import com.example.machinetestproject.databinding.ActivityDashboardBinding;
import com.example.machinetestproject.model.Product;
import com.example.machinetestproject.model.RequestModels.GetAllProductRequestModel;
import com.example.machinetestproject.model.RequestModels.ProductCountValueRequestModel;
import com.example.machinetestproject.utils.AlertDialogUtils;
import com.example.machinetestproject.utils.CommonUtils;
import com.example.machinetestproject.utils.Constants;
import com.example.machinetestproject.views.AddProduct.AddProductActivity;
import com.example.machinetestproject.views.Login.LoginActivity;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class DashboardActivity extends AppCompatActivity {

    ActivityDashboardBinding binding;

    DashboardViewModel viewModel;

    TextView initialsTextView, firstNameTextView;

    List<Product> productList;

    ProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(DashboardActivity.this, R.layout.activity_dashboard);

        init();
        observers();
        listeners();
    }

    /*
    * This function has all initializations
    * */
    public void init() {
        //Setting Custom Toolbar
        setSupportActionBar(binding.appBarDashboard.toolbar);

        //changing Status bar color
        CommonUtils.adjustStatusBarColor(DashboardActivity.this);

        /*
         * The purpose of ActionBarDrawerToggle is to provide an indicator icon
         *  on the action bar or toolbar that represents the state of the
         * navigation drawer. When the navigation drawer is closed, the icon
         * typically shows a "hamburger" menu icon, and when the drawer is
         * opened, it usually changes to an arrow or "back" icon.
         * */
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this, binding.drawerLayout,binding.appBarDashboard.toolbar,R.string.OpenDrawer,R.string.CloseDrawer);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        SharedPref.init(getApplicationContext());
        setNavigationDrawerData();

        viewModel=new ViewModelProvider(this).get(DashboardViewModel.class);

        makeApiCalls();
    }

    /*
    * This function has all Observers of Dashboard Activity
    * */
    public void observers() {
        //observes ProductList
        viewModel.getAllProductsResponseLiveData().observe(DashboardActivity.this, getAllProductsResponseModel -> {
            if(getAllProductsResponseModel.getStatus() != Constants.ERROR_RESPONSE){
                if(getAllProductsResponseModel.getStatus() == Constants.SUCCESS_RESPONSE){
                    if(getAllProductsResponseModel.getProductList().size()>0){ // products Found
                        binding.appBarDashboard.contentDashboard.productListRecyclerView.setVisibility(View.VISIBLE);
                        binding.appBarDashboard.contentDashboard.noDataLayout.getRoot().setVisibility(View.GONE);
                        productList=getAllProductsResponseModel.getProductList();
                        setProductListRecyclerView();
                    }
                    else{ // No products found Inflate No data layout
                        binding.appBarDashboard.contentDashboard.productListRecyclerView.setVisibility(View.GONE);
                        binding.appBarDashboard.contentDashboard.noDataLayout.getRoot().setVisibility(View.VISIBLE);
                    }

                }
            }
            else {
                showToast(Constants.SOMETHING_WENT_WRONG);
            }
        });

        //observes Product Count
        viewModel.getProductCountMutableLiveData.observe(DashboardActivity.this,productCountResponseModel -> {
            if(productCountResponseModel.getStatus() == Constants.SUCCESS_RESPONSE){
                binding.appBarDashboard.contentDashboard.productCountTextView.setText(productCountResponseModel.getValue());
            }
            else {
                showToast(Constants.SOMETHING_WENT_WRONG);
            }
        });

        //observes product value
        viewModel.getProductsValueMutableLiveData.observe(DashboardActivity.this,productValueResponseModel ->{
            if(productValueResponseModel.getStatus() == Constants.SUCCESS_RESPONSE){
                //converting large value amount to short format
                String amountStr=CommonUtils.convertAmountToShortAmountStr(productValueResponseModel.getValue());
                binding.appBarDashboard.contentDashboard.productValueTextView.setText(amountStr);
            }
            else {
                showToast(Constants.SOMETHING_WENT_WRONG);
            }
        });

        //observe delete Api response
        viewModel.getDeleteProductResponseLiveData().observe(DashboardActivity.this, deleteProductResponseModel -> {
            if(deleteProductResponseModel.getStatus() == Constants.SUCCESS_RESPONSE){
                AlertDialogUtils.showSuccessDialog(Constants.DELETE_PRODUCT_SUCCESS,DashboardActivity.this);
                makeApiCalls();
            }
            else {
                showToast(Constants.SOMETHING_WENT_WRONG);
            }
        });
        binding.appBarDashboard.contentDashboard.dashboardProgressBar.setVisibility(View.GONE);
    }

    /*
    * This function has all event listeners
    * */
    public void listeners() {

        binding.navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();
                switch (id){
                    case R.id.logout:
                        binding.drawerLayout.closeDrawer(GravityCompat.START);
                        showLogoutConfirmationDialog();
                        return true;
                    default:
                        return true;
                }
            }
        });

        //click Listener for Add Product Floating Action Button
        binding.appBarDashboard.contentDashboard.addProductFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DashboardActivity.this, AddProductActivity.class);
                startActivity(intent);
            }
        });
    }

    /*
     * This function is used to show Logout confirmation dialog when user clicks logout menu option.
     * */
    public void showLogoutConfirmationDialog(){
        Dialog logoutDialog = new Dialog(DashboardActivity.this);
        //TO DISABLE DEFAULT TITLE
        logoutDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //TO CANCEL THE DIALOG BY CLICKING ANYWHERE OUTSIDE DIALOG
        logoutDialog.setCancelable(true);
        logoutDialog.setContentView(R.layout.custom_delete_product_dialog);

        /*
         * Setting dialog window size */
        Window window=logoutDialog.getWindow();
        if(window!=null){
            WindowManager.LayoutParams layoutParams=window.getAttributes();
            layoutParams.height=WindowManager.LayoutParams.WRAP_CONTENT;
            layoutParams.width = (int) (DashboardActivity.this.getResources().getDisplayMetrics().widthPixels * 0.85); // Set width to 85% of screen width
            window.setAttributes(layoutParams);
            window.setBackgroundDrawableResource(R.drawable.dialog_border);
            window.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);

        }

        ImageView logoutImage = logoutDialog.findViewById(R.id.dialogImage);
        TextView logoutMessageTextView=logoutDialog.findViewById(R.id.deleteMessage);
        TextView logoutTitleTextView=logoutDialog.findViewById(R.id.deleteTitle);
        Button logoutButton=logoutDialog.findViewById(R.id.btnDelete);
        Button cancelButton=logoutDialog.findViewById(R.id.btnCancel);

        logoutButton.setText(getString(R.string.log_out));
        logoutImage.setImageDrawable(getResources().getDrawable(R.drawable.logout_icon));
        cancelButton.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.colorPrimary));
        logoutTitleTextView.setText(Constants.LOGOUT);
        logoutMessageTextView.setText(Constants.LOGOUT_MESSAGE);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutDialog.dismiss();
            }
        });
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeUserDetailsFromSharedPrefAndMoveToLogin();
                logoutDialog.dismiss();
            }
        });
        logoutDialog.show();
    }


    /*
    * This function includes all api calls that are to be made whenever Dashboard Loads.
    * */
    public void makeApiCalls(){
        //make get All Products Api Call
        GetAllProductRequestModel getAllProductRequestModel=new GetAllProductRequestModel();
        getAllProductRequestModel.setUserName(SharedPref.getString(Constants.USER_NAME,null));
        viewModel.makeGetAllProductsApiCall(getAllProductRequestModel);

        //make api call to getProductCount
        ProductCountValueRequestModel getProductCountRequestModel = new ProductCountValueRequestModel();
        getProductCountRequestModel.setUserName(SharedPref.getString(Constants.USER_NAME, null));
        viewModel.makeProductCountApiCall(getProductCountRequestModel);

        //make api call to getProductValue
        ProductCountValueRequestModel getProductValueRequestModel = new ProductCountValueRequestModel();
        getProductValueRequestModel.setUserName(SharedPref.getString(Constants.USER_NAME,null));
        viewModel.makeProductValueApiCall(getProductValueRequestModel);
    }

    /*
    * This function sets Product List Recycler View
    * */
    public void setProductListRecyclerView(){
        productAdapter = new ProductAdapter(DashboardActivity.this,productList);
        binding.appBarDashboard.contentDashboard.productListRecyclerView.setAdapter(productAdapter);
    }

    /*
    * This is called whenever Dashboard Resumes
    * This is overridden because api calls are to made again
    *  as Product details may have been updated.
    * */
    @Override
    protected void onResume() {
        super.onResume();
        makeApiCalls();
    }

    /*
    * This function sets navigationView data in Navigation Drawer.
    * */
    public void setNavigationDrawerData(){
        View navigationHeaderView=binding.navigationView.getHeaderView(0);
        firstNameTextView = navigationHeaderView.findViewById(R.id.firstNameTextView);
        initialsTextView = navigationHeaderView.findViewById(R.id.initialsTextView);

        initialsTextView.setText(CommonUtils.getInitials(SharedPref.getString(Constants.FULL_NAME,null)));
        firstNameTextView.setText(Constants.HI+ CommonUtils.getFirstName(SharedPref.getString(Constants.FULL_NAME,null)));
    }

    /*
    * This function removes User Details from SharedPreferences and navigate user to Login Screen.
    * */
    private void removeUserDetailsFromSharedPrefAndMoveToLogin() {
        SharedPref.removePreference(Constants.USER_EMAIL);
        SharedPref.removePreference(Constants.USER_MOBILE);
        SharedPref.removePreference(Constants.USER_NAME);
        SharedPref.removePreference(Constants.FULL_NAME);

        SharedPref.removePreference(Constants.BOOL_USER_LOGGED_IN);

        Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    /*
     * This function is used to show toast with the provided message.
     * */
    private void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}