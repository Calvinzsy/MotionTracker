package com.example.calvin.motiontracker.data;

import android.arch.lifecycle.LiveData;

import com.example.calvin.motiontracker.model.Journey;

import java.util.List;

public interface JourneyRepository {

    LiveData<List<Journey>> getJourneys();

    void addJourney(Journey journey);
}
