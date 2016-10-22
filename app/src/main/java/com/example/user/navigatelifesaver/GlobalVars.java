package com.example.user.navigatelifesaver;

import android.app.Application;

/**
 * Created by ofir on 22/10/2016.
 */

public class GlobalVars extends Application {
    private String USERNAME;
    private boolean isDiagnosed;
    private String Type;
    public String getUSERNAME() {
        return USERNAME;
    }

    public void setUSERNAME(String USERNAME) {
        this.USERNAME = USERNAME;
    }

    public boolean getIsDiagnosed() {
        return isDiagnosed;
    }

    public void setIsDiagnosed(boolean isDiagnosed) {
        this.isDiagnosed = isDiagnosed;
    }

    public String getType() {
        return Type;
    }

    public void setType(String Type) {
        this.Type = Type;
    }
}

