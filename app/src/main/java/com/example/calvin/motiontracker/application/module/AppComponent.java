package com.example.calvin.motiontracker.application.module;

import android.content.Context;

import com.example.calvin.motiontracker.data.JourneyRepository;

import dagger.Component;

@AppScope
@Component(modules = {AppModule.class, JourneyRepositoryModule.class})
public interface AppComponent {
    Context context();
    JourneyRepository journeyRepository();
}
