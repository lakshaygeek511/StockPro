package com.example.machinetestproject.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.machinetestproject.R;
import com.example.machinetestproject.views.AddProduct.AddProductActivity;
import com.example.machinetestproject.views.Dashboard.DashboardActivity;
import com.example.machinetestproject.views.Login.LoginActivity;
import com.example.machinetestproject.views.Register.RegisterActivity;
import com.example.machinetestproject.views.UpdateProduct.UpdateProductActivity;

/**
 * Project Name : MachineTestProject
 *
 * @author VE00YM576
 * @company YMSLI
 * @date 05-07-2023
 * Copyright (c) 2021, Yamaha Motor Solutions (INDIA) Pvt Ltd.
 * Description
 * -----------------------------------------------------------------------------------
 * MachineTestApp :
 * -----------------------------------------------------------------------------------
 * Revision History
 * -----------------------------------------------------------------------------------
 * Modified By          Modified On         Description
 * -----------------------------------------------------------------------------------
 */
public class AlertDialogUtils {

    /*
    * This function is used to show success message dialog whenever required.
    * */
    public static void showSuccessDialog(String title, Context context){
        Dialog dialog=new Dialog(context);
        //TO DISABLE DEFAULT TITLE
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //TO CANCEL THE DIALOG BY CLICKING ANYWHERE OUTSIDE DIALOG
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.custom_success_dialog_layout);

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

        TextView successMessageTextView=dialog.findViewById(R.id.successMessageTextView);
        Button okButton=dialog.findViewById(R.id.okButton);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((context instanceof UpdateProductActivity) || (context instanceof AddProductActivity)) {
                    Intent intent = new Intent(context, DashboardActivity.class);
                    context.startActivity(intent);
                    ((Activity) context).finish();
                }
                else if((context instanceof RegisterActivity)){
                    Intent intent = new Intent(context, LoginActivity.class);
                    context.startActivity(intent);
                    ((Activity) context).finish();
                }
                dialog.dismiss();
            }
        });
        successMessageTextView.setText(title);
        dialog.show();
    }


    /*
    * This function is used to show error Dialog with provided title and message of the error
    * */
    public static void showErrorDialog(String errorTitle, String errorMessage, Context context){
        Dialog dialog=new Dialog(context);
        //TO DISABLE DEFAULT TITLE
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //TO CANCEL THE DIALOG BY CLICKING ANYWHERE OUTSIDE DIALOG
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.custom_error_dialog_layout);

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


        TextView errorTitleTextView=dialog.findViewById(R.id.errorTitle);
        TextView errorMessageTextView=dialog.findViewById(R.id.errorMessage);
        Button closeButton =dialog.findViewById(R.id.btnClose);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        errorMessageTextView.setText(errorMessage);
        errorTitleTextView.setText(errorTitle);
        dialog.show();
    }
}
