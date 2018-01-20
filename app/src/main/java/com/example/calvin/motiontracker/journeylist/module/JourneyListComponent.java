package com.example.calvin.motiontracker.journeylist.module;

import com.example.calvin.motiontracker.application.module.ApplicationComponent;
import com.example.calvin.motiontracker.journeylist.view.JourneyListFragment;

import dagger.Component;

/**
 * The class describes injection modules for journey list component. It also defines the
 * targets of injection.
 */
@JourneyListScope
@Component(dependencies = {ApplicationComponent.class},modules = {JourneyListModule.class})
public interface JourneyListComponent {

    /**
     * Inject this component into {@link JourneyListFragment}.
     * @param journeyListFragment The {@link JourneyListFragment} to be injected into.
     */
    void inject(JourneyListFragment journeyListFragment);
}
