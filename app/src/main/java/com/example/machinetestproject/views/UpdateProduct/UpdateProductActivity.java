package com.example.machinetestproject.views.UpdateProduct;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.machinetestproject.R;
import com.example.machinetestproject.database.sharedPref.SharedPref;
import com.example.machinetestproject.databinding.ActivityUpdateProductBinding;
import com.example.machinetestproject.model.Product;
import com.example.machinetestproject.model.RequestModels.AddProductRequestModel;
import com.example.machinetestproject.utils.AlertDialogUtils;
import com.example.machinetestproject.utils.CommonUtils;
import com.example.machinetestproject.utils.Constants;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class UpdateProductActivity extends AppCompatActivity {

    ActivityUpdateProductBinding binding;

    Product product;

    UpdateProductViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(UpdateProductActivity.this,R.layout.activity_update_product);

        init();
        observers();
        listeners();
    }

    /*
    * this function has all initializations
    * */
    public void init(){
        getSupportActionBar().hide();
        CommonUtils.adjustStatusBarColor(UpdateProductActivity.this);

        SharedPref.init(UpdateProductActivity.this);

        product=getIntent().getParcelableExtra(Constants.PRODUCT_SHARE);
        binding.productNameTextView.setText(product.getProductName());
        binding.productPriceEditText.setText(product.getProductPrice());
        binding.productQtyEditText.setText(product.getProductQty());

        viewModel=new ViewModelProvider(this).get(UpdateProductViewModel.class);
    }

    /*
    * This function has all the observers
    * */
    public void observers(){
        //observing UpdateResponseModel Livedata
        viewModel.getUpdateProductLiveData().observe(UpdateProductActivity.this,updateProductResponseModel -> {
            if(updateProductResponseModel.getStatus() == Constants.SUCCESS_RESPONSE){
                AlertDialogUtils.showSuccessDialog(updateProductResponseModel.getMessage(),UpdateProductActivity.this);
                freezeForm();
            }
            else {
                showToast(Constants.SOMETHING_WENT_WRONG);
            }
        });
    }

    /*
    * This function has all listeners for Update Product Activity
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

        //make update Product Api Call on click of Update Button
        binding.updateProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkValidations()){
                    AddProductRequestModel updateProductRequestModel=new AddProductRequestModel();
                    updateProductRequestModel.setUserName(SharedPref.getString(Constants.USER_NAME,null));

                    Product newProduct=new Product();
                    newProduct.setProductId(product.getProductId());
                    newProduct.setProductName(product.getProductName());
                    newProduct.setProductPrice(binding.productPriceEditText.getText().toString().trim());
                    newProduct.setProductQty(binding.productQtyEditText.getText().toString().trim());
                    newProduct.setUserName(SharedPref.getString(Constants.USER_NAME,null));

                    updateProductRequestModel.setProduct(newProduct);

                    viewModel.makeUpdateProductApiCall(updateProductRequestModel);
                }
            }
        });
    }

    /*
    * This function checks validations on update product form
    * */
    public boolean checkValidations(){
        if(!isValidPrice()) return false;
        else if(!isValidQuantity()) return false;
        else if (!areProductDetailsChanged()) return false;
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
    * this function makes quantity and price editTexts uneditable once product details are updated.
    * */
    public void freezeForm(){
        binding.productQtyEditText.setFocusable(false);
        binding.productPriceEditText.setFocusable(false);
    }

    /*
    * This function checks whether new Product's price and quantity has been entered or not.
    * */
    public boolean areProductDetailsChanged(){
        String newPrice=binding.productPriceEditText.getText().toString().trim();
        String newQty=binding.productQtyEditText.getText().toString().trim();
        if(newQty.equals(product.getProductQty()) && newPrice.equals(product.getProductPrice())){
            showToast(Constants.UPDATE_PRODUCT_QTY_PRICE_STR);
            return false;
        }
        return true;
    }

    /*
     * This function is used to show toast with the provided message.
     * */
    private void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}