package com.example.calvin.motiontracker.application.module;

import com.example.calvin.motiontracker.application.JourneyApplication;
import com.example.calvin.motiontracker.data.JourneyRepository;

import dagger.Component;

@ApplicationScope
@Component(modules = {ApplicationModule.class, JourneyRepositoryModule.class})
public interface ApplicationComponent {

    void inject(JourneyApplication journeyApplication);

    JourneyRepository journeyRepository();


}