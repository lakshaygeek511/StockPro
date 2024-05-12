package com.example.machinetestproject.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.example.machinetestproject.R;
import com.example.machinetestproject.database.sharedPref.SharedPref;
import com.example.machinetestproject.databinding.ProductRowBinding;
import com.example.machinetestproject.model.Product;
import com.example.machinetestproject.model.RequestModels.DeleteProductRequestModel;
import com.example.machinetestproject.utils.CommonUtils;
import com.example.machinetestproject.utils.Constants;
import com.example.machinetestproject.views.Dashboard.DashboardActivity;
import com.example.machinetestproject.views.Dashboard.DashboardViewModel;
import com.example.machinetestproject.views.UpdateProduct.UpdateProductActivity;

import java.util.List;

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
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    ProductRowBinding binding;

    List<Product> productList;

    Context context;

    static DashboardViewModel viewModel;

    public ProductAdapter(Context context,List<Product> productList){
        this.productList=productList;
        this.context=context;

        SharedPref.init(context);
        viewModel=new ViewModelProvider((ViewModelStoreOwner) context).get(DashboardViewModel.class);
    }

    @NonNull
    @Override
    public ProductAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        binding= DataBindingUtil.inflate(inflater, R.layout.product_row,parent,false);
        View view = binding.getRoot();
        ProductViewHolder taskListViewHolder=new ProductViewHolder(view);
        return taskListViewHolder;
    }

    /*
    * This function sets data to each listItem one by one.
    * */
    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ProductViewHolder holder, int position) {
        Product product=productList.get(position);

        String productName=product.getProductName();
        String productQty="<b>" + context.getString(R.string.qty) + "</b>" + product.getProductQty();
        String productPrice= "<b>" + context.getString(R.string.Rs)+ "</b>" +product.getProductPrice();

        binding.productNameTextView.setText(Html.fromHtml(productName));
        binding.productPriceTextView.setText(Html.fromHtml(productPrice));
        binding.productQtyTextView.setText(Html.fromHtml(productQty));

        int randomColor=0;
        if(Integer.parseInt(product.getProductQty())<=Constants.LOW_QTY){
            randomColor= ContextCompat.getColor(context,R.color.lightRedColor);
        }else if (Integer.parseInt(product.getProductQty())>=Constants.LOW_QTY  &&  Integer.parseInt(product.getProductQty())<=Constants.MEDIUM_QTY) {
            randomColor= ContextCompat.getColor(context,R.color.lightYellowColor);
        } else if (Integer.parseInt(product.getProductQty())<=Constants.HIGH_QTY  &&  Integer.parseInt(product.getProductQty())>=Constants.MEDIUM_QTY) {
            randomColor= ContextCompat.getColor(context,R.color.lightBlueColor);
        } else if (Integer.parseInt(product.getProductQty())>=Constants.HIGH_QTY) {
            randomColor= ContextCompat.getColor(context,R.color.lightGreenColor);
        }
        binding.cardView.setCardBackgroundColor(randomColor);

        binding.deleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDeleteProductDialog(Constants.DELETE_PRODUCT, Constants.DELETE_PRODUCT_MESSAGE,context,product);
            }
        });
    }

    /*
    * This function makes delete api call for product
    * */
    private static void deleteProduct(Product product) {
        DeleteProductRequestModel deleteProductRequestModel=new DeleteProductRequestModel();
        deleteProductRequestModel.setUserName(SharedPref.getString(Constants.USER_NAME,null));
        deleteProductRequestModel.setProduct(product);

        viewModel.makeDeleteProductApiCall(deleteProductRequestModel);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    /*
     * This function is used to show delete Product Dialog with provided title and message of the error
     * */
    public static void showDeleteProductDialog(String deleteTitle, String deleteMessage, Context context, Product product){
        Dialog dialog=new Dialog(context);
        //TO DISABLE DEFAULT TITLE
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //TO CANCEL THE DIALOG BY CLICKING ANYWHERE OUTSIDE DIALOG
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.custom_delete_product_dialog);

        /*
         * Setting dialog window size */
        Window window=dialog.getWindow();
        if(window!=null){
            WindowManager.LayoutParams layoutParams=window.getAttributes();
            layoutParams.height=WindowManager.LayoutParams.WRAP_CONTENT;
            layoutParams.width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.85); // Set width to 85% of screen width
            window.setAttributes(layoutParams);
            window.setBackgroundDrawableResource(R.drawable.dialog_border);
            window.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        }


        TextView deleteMessageTextView=dialog.findViewById(R.id.deleteMessage);
        TextView deleteTitleTextView=dialog.findViewById(R.id.deleteTitle);
        Button cancelButton =dialog.findViewById(R.id.btnCancel);
        Button deleteButton =dialog.findViewById(R.id.btnDelete);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteProduct(product);
                dialog.dismiss();
            }
        });
        deleteMessageTextView.setText(deleteMessage);
        deleteTitleTextView.setText(deleteTitle);
        dialog.show();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Intent intent=new Intent(view.getContext(), UpdateProductActivity.class);
            intent.putExtra(Constants.PRODUCT_SHARE,productList.get(position));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ((Activity) view.getContext()).startActivityForResult(intent,Activity.RESULT_OK);
        }
    }
}
