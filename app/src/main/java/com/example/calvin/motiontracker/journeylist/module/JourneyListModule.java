package com.example.calvin.motiontracker.journeylist.module;

import android.arch.lifecycle.ViewModelProvider;

import com.example.calvin.motiontracker.data.JourneyRepository;
import com.example.calvin.motiontracker.journeylist.viewmodel.JourneyListViewModelFactory;

import dagger.Module;
import dagger.Provides;

/**
 * The application module that provides application context for {@link JourneyListScope} injection.
 */
@Module
public class JourneyListModule {

    /**
     * Provides a {@link ViewModelProvider.Factory} for creating {@link android.arch.lifecycle.ViewModel}.
     * @param journeyRepository The {@link JourneyRepository} used to create {@link android.arch.lifecycle.ViewModel}.
     * @return {@link ViewModelProvider.Factory} used for creating {@link android.arch.lifecycle.ViewModel}.
     */
    @JourneyListScope
    @Provides
    ViewModelProvider.Factory provideViewModelFactory(JourneyRepository journeyRepository) {
        return new JourneyListViewModelFactory(journeyRepository);
    }
}
