package com.example.calvin.motiontracker.journeylist.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.calvin.motiontracker.data.JourneyRepository;
import com.example.calvin.motiontracker.model.Journey;

import java.util.List;

/**
 * The {@link ViewModel} for holding and getting journeys from the {@link JourneyRepository}.
 */
public class JourneyListViewModel extends ViewModel{

    /**
     * The {@link JourneyRepository} used by this ViewModel.
     */
    private JourneyRepository journeyRepository;

    /**
     * The list of journeys this ViewModel holds.
     */
    private LiveData<List<Journey>> journeys;

    JourneyListViewModel(JourneyRepository journeyRepository) {
        this.journeyRepository = journeyRepository;
    }

    public LiveData<List<Journey>> getJourneys() {
        if (journeys == null) {
            journeys = journeyRepository.getJourneys();
        }
        return journeys;
    }
}
