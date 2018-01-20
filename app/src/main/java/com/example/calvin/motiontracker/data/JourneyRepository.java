package com.example.calvin.motiontracker.data;

import android.arch.lifecycle.LiveData;

import com.example.calvin.motiontracker.model.Journey;

import java.util.List;

/**
 * The interface defines basic operations a journey repository should support.
 */
public interface JourneyRepository {

    /**
     * Get all journeys from the repository.
     * @return All journeys from the repository.
     */
    LiveData<List<Journey>> getJourneys();

    /**
     * Add a journey to repository.
     * @param journey The journey to be added.
     */
    void addJourney(Journey journey);
}
