package com.example.calvin.motiontracker.application;

import android.app.Application;

import com.example.calvin.motiontracker.application.module.ApplicationComponent;
import com.example.calvin.motiontracker.application.module.ApplicationModule;
import com.example.calvin.motiontracker.application.module.DaggerApplicationComponent;
import com.example.calvin.motiontracker.application.module.JourneyRepositoryModule;

public class JourneyApplication extends Application {

    private static ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .journeyRepositoryModule(new JourneyRepositoryModule())
                .build();
        applicationComponent.inject(this);
    }

    public static ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
