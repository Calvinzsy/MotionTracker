package com.example.calvin.motiontracker.journeylist.module;

import com.example.calvin.motiontracker.application.module.ApplicationComponent;
import com.example.calvin.motiontracker.journeylist.view.JourneyListFragment;

import dagger.Component;

@JourneyListScope
@Component(dependencies = {ApplicationComponent.class},modules = {JourneyListModule.class})
public interface JourneyListComponent {

    void inject(JourneyListFragment journeyListFragment);
}
