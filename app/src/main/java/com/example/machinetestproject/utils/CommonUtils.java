package com.example.machinetestproject.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.example.machinetestproject.R;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Project Name : StockPro
 *
 * @author VE00YM576
 * @company YMSLI
 * @date 05-07-2023
 * Copyright (c) 2021, Yamaha Motor Solutions (INDIA) Pvt Ltd.
 * Description
 * -----------------------------------------------------------------------------------
 * MachineTestProject :
 * -----------------------------------------------------------------------------------
 * Revision History
 * -----------------------------------------------------------------------------------
 * Modified By          Modified On         Description
 * -----------------------------------------------------------------------------------
 */
public class CommonUtils {

    public static final int[] lightColorsArray={
            R.color.color1light_gray,
            R.color.color2pastel_yellow,
            R.color.color3pale_green,
            R.color.color4snow,
            R.color.color5lavender_blush,
            R.color.color6beige,
            R.color.color7azure,
            R.color.color8mint_cream,
            R.color.color9honeydew,
            R.color.color10ivory
    };

    /*
    * This function returns random color from an integer array of colors
    * */
    public static int getRandomLightColor(int[] colors,Context context){
        int randomIndex=new Random().nextInt(colors.length);
        int colorResource = colors[randomIndex];
        int color = ContextCompat.getColor(context,colorResource);
        return color;
    }


    /*
     * This function sets status bar color to white and status bar icons to black
     * It is done in order to match app theme.
     * */
    public static void adjustStatusBarColor(Activity activity){
        Window window = activity.getWindow();
//        By setting this flag, you ensure that the window takes control of drawing the background of the system bars.
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        /*
         * By clearing this flag, you remove the translucent effect from the status bar,
         * allowing it to have a solid background color. This ensures that the status bar
         * doesn't overlap or blend with the content behind it.
         * */
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(activity, R.color.white));

//        following line set the status bar icons to black
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    /*
     * This function checks validations on ein that it should start with VE00 followed by
     * two alphabets and three digits.
     * */
    public static boolean checkValidEin(String ein){
        String pattern = "^(?i)VE00[A-Za-z]{2}\\d{3}$";
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(ein);

        return matcher.matches();
    }

    /*This function takes full name as a parameter and
     * returns an Uppercase String of initial letters in the name
     * */
    public static String getInitials(String name) {
        String[] words = name.trim().split("\\s+"); // Split the name into words

        if (words.length >= 2) {
            String firstWord = words[0].substring(0, 1).toUpperCase(); // Get the first letter of the first word
            String secondWord = words[1].substring(0, 1).toUpperCase(); // Get the first letter of the second word

            return firstWord + secondWord;
        } else if (words.length == 1) {
            // If there's only one word, return the initial of that word
            return words[0].substring(0, 2).toUpperCase();
        } else {
            return ""; // Return an empty string if there are no words
        }
    }

    /*
    * This function returns first name of user from Full Name
    * */
    public static String getFirstName(String fullName) {
        if (fullName == null || fullName.isEmpty()) {
            return "";
        }
        // Split the full name into words based on spaces
        String[] nameParts = fullName.trim().split("\\s+");

        // The first part will be the first name
        return nameParts[0];
    }

    /*
    * This function checks and validates email id format.
    * */
    public static boolean isValidEmailFormat(String email) {
        // Regular expression pattern for email validation
        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

    /*
    * This function converts numeric strings in short format
    * like 12300 to 12.3k
    * 123000 to 12.3l
    * 1230000 to 12.3l and so on
    * */
    public static String convertAmountToShortAmountStr(String numericString) {
        double number = Double.parseDouble(numericString);

        if (number >= 1e7) { // Crore or above (>= 10,000,000)
            return String.format("%.2fC", number / 1e7);
        } else if (number >= 1e5) { // Lakh or above (>= 100,000)
            return String.format("%.2fL", number / 1e5);
        } else if (number >= 1e3) { // Thousand or above (>= 1,000)
            return String.format("%.2fk", number / 1e3);
        } else {
            return numericString; // Return the original string if not in the above ranges
        }
    }

}
