package com.example.calvin.motiontracker.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.calvin.motiontracker.model.Journey;

/**
 * The database for storing journeys.
 */
@Database(entities = {Journey.class}, version = 1, exportSchema = false)
public abstract class JourneyDatabase extends RoomDatabase {

    /**
     * Get the data access object for this database.
     * @return Data access object for this database.
     */
    public abstract JourneyDao journeyDao();
}
