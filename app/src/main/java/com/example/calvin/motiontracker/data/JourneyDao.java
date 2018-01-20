package com.example.calvin.motiontracker.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.calvin.motiontracker.model.Journey;

import java.util.List;

/**
 * Data access object used for database operations.
 */
@Dao
public interface JourneyDao {

    /**
     * Insert a journey into database.
     * @param journey The journey to be inserted.
     */
    @Insert
    void insertJourney(Journey journey);

    /**
     * Get all journeys from database.
     * @return All journeys in database.
     */
    @Query("SELECT * FROM journey")
    LiveData<List<Journey>> getJourneys();
}
