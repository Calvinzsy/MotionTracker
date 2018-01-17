package com.example.calvin.motiontracker.application;

import android.app.Application;

import com.example.calvin.motiontracker.application.module.AppComponent;
import com.example.calvin.motiontracker.application.module.AppModule;
import com.example.calvin.motiontracker.application.module.DaggerAppComponent;

public class JourneyApplication extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return this.appComponent;
    }
}
