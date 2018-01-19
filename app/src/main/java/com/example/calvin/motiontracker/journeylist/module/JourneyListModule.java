package com.example.calvin.motiontracker.journeylist.module;

import android.arch.lifecycle.ViewModelProvider;

import com.example.calvin.motiontracker.data.JourneyRepository;
import com.example.calvin.motiontracker.journeylist.viewmodel.JourneyListViewModelFactory;

import dagger.Module;
import dagger.Provides;

@Module
public class JourneyListModule {

    @JourneyListScope
    @Provides
    ViewModelProvider.Factory provideViewModelFactory(JourneyRepository journeyRepository) {
        return new JourneyListViewModelFactory(journeyRepository);
    }
}
