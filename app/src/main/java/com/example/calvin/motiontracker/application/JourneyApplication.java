package com.example.calvin.motiontracker.application;

import android.app.Application;

import com.example.calvin.motiontracker.application.module.ApplicationComponent;
import com.example.calvin.motiontracker.application.module.ApplicationModule;
import com.example.calvin.motiontracker.application.module.DaggerApplicationComponent;
import com.example.calvin.motiontracker.application.module.JourneyRepositoryModule;

/**
 * Custom application that injects application level components with application context.
 */
public class JourneyApplication extends Application {

    /**
     * The application level components used for injection.
     */
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
