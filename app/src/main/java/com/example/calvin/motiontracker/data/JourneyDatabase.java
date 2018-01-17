package com.example.calvin.motiontracker.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.calvin.motiontracker.model.Journey;

@Database(entities = {Journey.class}, version = 1, exportSchema = false)
public abstract class JourneyDatabase extends RoomDatabase {

    abstract JourneyDao journeyDao();
}
