package com.example.calvin.motiontracker.journeylist.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.calvin.motiontracker.data.JourneyRepository;

public class JourneyListViewModelFactory implements ViewModelProvider.Factory {

    private JourneyRepository journeyRepository;

    public JourneyListViewModelFactory(JourneyRepository journeyRepository) {
        this.journeyRepository = journeyRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new JourneyListViewModel(journeyRepository);
    }
}
