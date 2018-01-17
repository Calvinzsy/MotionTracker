package com.example.calvin.motiontracker.application.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private final Context appContext;

    public AppModule(Context appContext) {
        this.appContext = appContext;
    }

    @AppScope
    @Provides
    Context provideAppContext() {
        return appContext;
    }
}
