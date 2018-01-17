package com.example.calvin.motiontracker.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.calvin.motiontracker.model.Journey;

import java.util.List;

@Dao
public interface JourneyDao {

    @Insert
    void insertJourney(Journey journey);

    @Query("SELECT * FROM journey")
    LiveData<List<Journey>> getJourneys();
}
