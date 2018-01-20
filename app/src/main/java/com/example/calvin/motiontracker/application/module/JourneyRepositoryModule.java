package com.example.calvin.motiontracker.application.module;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.example.calvin.motiontracker.data.JourneyDatabase;
import com.example.calvin.motiontracker.data.JourneyRepository;
import com.example.calvin.motiontracker.data.JourneyRepositoryImpl;

import dagger.Module;
import dagger.Provides;

/**
 * The module that provides {@link JourneyRepository} to be used for {@link ApplicationScope} injection.
 */
@Module
public class JourneyRepositoryModule {

    /**
     * The database name.
     */
    private static final String DATABASE_NAME = "journey_db";

    /**
     * Provides the {@link JourneyRepository} during injection.
     * @param context The application context used to construct the room database.
     * @return {@link JourneyRepository} that supports Sqlite database operations using {@link Room}.
     */
    @ApplicationScope
    @Provides
    JourneyRepository provideJourneyRepository(Context context) {
        JourneyDatabase journeyDatabase = Room.databaseBuilder(context, JourneyDatabase.class, DATABASE_NAME).build();
        return new JourneyRepositoryImpl(journeyDatabase.journeyDao());
    }
}
