package com.example.calvin.motiontracker.application.module;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.example.calvin.motiontracker.data.JourneyDatabase;
import com.example.calvin.motiontracker.data.JourneyRepository;
import com.example.calvin.motiontracker.data.JourneyRepositoryImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class JourneyRepositoryModule {

    private static final String DATABASE_NAME = "journey_db";

    @AppScope
    @Provides
    JourneyDatabase provideJourneyDatabase(Context context) {
        return Room.databaseBuilder(context, JourneyDatabase.class, DATABASE_NAME).build();
    }

    @AppScope
    @Provides
    JourneyRepository provideJourneyRepository(JourneyDatabase journeyDatabase) {
        return new JourneyRepositoryImpl(journeyDatabase.journeyDao());
    }
}
