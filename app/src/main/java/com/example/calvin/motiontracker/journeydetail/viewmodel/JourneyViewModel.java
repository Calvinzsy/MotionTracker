package com.example.calvin.motiontracker.journeydetail.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.calvin.motiontracker.model.Journey;

public class JourneyViewModel extends ViewModel {

    private MutableLiveData<Journey> journey = new MutableLiveData<>();

    public void setJourney(Journey newJourney) {
        journey.setValue(newJourney);
    }

    public LiveData<Journey> getJourney() {
        return this.journey;
    }
}
