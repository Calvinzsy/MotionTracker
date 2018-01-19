package com.example.calvin.motiontracker.journeylist.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.calvin.motiontracker.data.JourneyRepository;
import com.example.calvin.motiontracker.model.Journey;

import java.util.List;

public class JourneyListViewModel extends ViewModel{

    private JourneyRepository journeyRepository;

    private LiveData<List<Journey>> journeys;

    public JourneyListViewModel(JourneyRepository journeyRepository) {
        this.journeyRepository = journeyRepository;
    }

    public LiveData<List<Journey>> getJourneys() {
        if (journeys == null) {
            journeys = journeyRepository.getJourneys();
        }
        return journeys;
    }
}
