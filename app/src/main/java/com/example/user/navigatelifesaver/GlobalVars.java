package com.example.user.navigatelifesaver;

import android.app.Application;

/**
 * Created by ofir on 22/10/2016.
 */

public class GlobalVars {
    private static String USERNAME;
    private static boolean isDiagnosed;
    private static String Type;
    private static String category;
    public static String getUSERNAME() {
        return USERNAME;
    }
    public static String getCategory(){
        return category;
    }
    public static void setCategory(String Category){
        category = Category;
    }
    public static void setUSERNAME(String username) {
        USERNAME = username;
    }

    public static boolean getIsDiagnosed() {
        return isDiagnosed;
    }

    public static void setIsDiagnosed(boolean isdiagnosed) {
        isDiagnosed = isdiagnosed;
    }

    public static String getType() {
        return Type;
    }

    public static void setType(String type) { Type = type;
    }
}

