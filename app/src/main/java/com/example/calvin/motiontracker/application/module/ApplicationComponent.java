package com.example.calvin.motiontracker.application.module;

import com.example.calvin.motiontracker.application.JourneyApplication;
import com.example.calvin.motiontracker.application.service.JourneyTrackingService;
import com.example.calvin.motiontracker.data.JourneyRepository;

import dagger.Component;

/**
 * The class describes modules used for injection. It also defines the
 * targets of injection and objects to be inherited by sub-components.
 */
@ApplicationScope
@Component(modules = {ApplicationModule.class, JourneyRepositoryModule.class})
public interface ApplicationComponent {

    /**
     * Inject this component into {@link JourneyApplication}.
     * @param journeyApplication The {@link JourneyApplication} to be injected into.
     */
    void inject(JourneyApplication journeyApplication);

    /**
     * Inject this component into {@link JourneyTrackingService}.
     * @param journeyTrackingService The {@link JourneyTrackingService} to be injected into.
     */
    void inject(JourneyTrackingService journeyTrackingService);

    /**
     * Expose the journeyRepository to sub-components.
     * @return The JourneyRepository exposed by this component.
     */
    JourneyRepository journeyRepository();
}
