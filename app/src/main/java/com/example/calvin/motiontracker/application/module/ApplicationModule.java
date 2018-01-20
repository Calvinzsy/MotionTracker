package com.example.calvin.motiontracker.application.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * The application module that provides application context for {@link ApplicationScope} injection.
 */
@Module
public class ApplicationModule {

    /**
     * The application context.
     */
    private final Context context;

    /**
     * Construct an {@link ApplicationModule} with application's {@link Context}
     * @param context The application context.
     */
    public ApplicationModule(Context context) {
        this.context = context;
    }

    /**
     * Provides application context during injection.
     * @return The application context used for injection.
     */
    @ApplicationScope
    @Provides
    Context provideApplicationContext() {
        return context;
    }
}
