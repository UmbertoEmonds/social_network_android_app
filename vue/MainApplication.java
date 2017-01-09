package com.mireal.admin.mireal7.vue;

import android.app.Application;

import com.mireal.admin.mireal7.controller.BackgroundService;

/**
 * Created by admin on 04/01/2017.
 */

public class MainApplication extends Application {

    public static MainApplication mainApplication;

    @Override
    public void onCreate() {
        super.onCreate();

        BackgroundService.launch(this);

        mainApplication = this;

    }



    public static MainApplication getMainApplication(){
        return mainApplication;
    }

}
