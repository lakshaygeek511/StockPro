package com.example.machinetestproject.views.AddProduct;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.machinetestproject.R;
import com.example.machinetestproject.database.sharedPref.SharedPref;
import com.example.machinetestproject.databinding.ActivityAddProductBinding;
import com.example.machinetestproject.model.Product;
import com.example.machinetestproject.model.RequestModels.AddProductRequestModel;
import com.example.machinetestproject.utils.AlertDialogUtils;
import com.example.machinetestproject.utils.CommonUtils;
import com.example.machinetestproject.utils.Constants;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AddProductActivity extends AppCompatActivity {
    ActivityAddProductBinding binding;

    AddProductViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(AddProductActivity.this, R.layout.activity_add_product);

        init();
        observers();
        listeners();
    }

    /*
    * This function handles all initializations
    * */
    public void init(){
        getSupportActionBar().hide();
        CommonUtils.adjustStatusBarColor(AddProductActivity.this);

        SharedPref.init(AddProductActivity.this);

        viewModel=new ViewModelProvider(this).get(AddProductViewModel.class);
    }

    /*
    * This function observes LiveData of various objects required on Add Product Activity
    * */
    public void observers(){
        //observing addProductResponse LiveData
        viewModel.getAddProductResponseLiveData().observe(AddProductActivity.this,addProductResponseModel -> {
            if(addProductResponseModel.getStatus() == Constants.SUCCESS_RESPONSE){
                AlertDialogUtils.showSuccessDialog("Product Added Successfully", AddProductActivity.this);
                resetForm();
            }
            else {
                showToast(Constants.SOMETHING_WENT_WRONG);
            }
        });
    }

    /*
    * This function handles all listeners of AddProductActivity
    * */
    public void listeners(){
        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultIntent = new Intent();
                //Result is set as we do not have parent activity setting result means move to parent activity.
                setResult(Activity.RESULT_OK);
                finish();
            }
        });

        //make add product api call on click of add product button
        binding.addProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkValidations()){
                    AddProductRequestModel addProductRequestModel=new AddProductRequestModel();
                    addProductRequestModel.setUserName(SharedPref.getString(Constants.USER_NAME,null));

                    Product product=new Product();
                    product.setProductName(binding.productNameTextView.getText().toString().trim());
                    product.setProductQty(binding.productQtyEditText.getText().toString().trim());
                    product.setProductPrice(binding.productPriceEditText.getText().toString().trim());
                    product.setUserName(SharedPref.getString(Constants.USER_NAME,null));

                    addProductRequestModel.setProduct(product);

                    viewModel.makeAddProductApiCall(addProductRequestModel);
                }
            }
        });
    }

    /*
     * This function checks validations on update product form
     * */
    public boolean checkValidations(){
        if(!isValidProductName()) return false;
        else if(!isValidPrice()) return false;
        else if(!isValidQuantity()) return false;
        return true;
    }

    /*
     * This function checks validations on Product Name
     * */
    public boolean isValidProductName(){
        if(TextUtils.isEmpty(binding.productNameTextView.getText().toString().trim())){
            showToast(Constants.ENTER_PRODUCT_NAME_STR);
            return false;
        }
        else if(binding.productNameTextView.getText().toString().trim().length() > Constants.LONG_PRODUCT_NAME){
            showToast(Constants.LONG_PRODUCT_NAME_STR);
            return false;
        }
        return true;
    }

    /*
     * This function checks validations on Product Price
     * */
    public boolean isValidPrice(){
        if(TextUtils.isEmpty(binding.productPriceEditText.getText().toString().trim())){
            showToast(Constants.ENTER_PRODUCT_PRICE_STR);
            return false;
        }
        return true;
    }

    /*
     * This function checks validations on Product quantity
     * */
    public boolean isValidQuantity(){
        if(TextUtils.isEmpty(binding.productQtyEditText.getText().toString().trim())){
            showToast(Constants.ENTER_PRODUCT_QTY_STR);
            return false;
        }
        return true;
    }

    /*
    * This function resets Add Product Form
    * */
    public void resetForm(){
        binding.productNameTextView.setText("");
        binding.productPriceEditText.setText("");
        binding.productQtyEditText.setText("");
    }

    /*
     * This function is used to show toast with the provided message.
     * */
    private void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}