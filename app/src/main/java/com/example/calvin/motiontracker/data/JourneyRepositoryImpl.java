package com.example.calvin.motiontracker.data;

import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.calvin.motiontracker.model.Journey;

import java.util.List;

/**
 * The implementation of {@link JourneyRepository} that wraps Room data
 * access object for sqlite database operations.
 */
public class JourneyRepositoryImpl implements JourneyRepository {

    /**
     * The Room data access object used for database operations.
     */
    private final JourneyDao journeyDao;

    /**
     * Construct this repository with a {@link JourneyDao}.
     * @param journeyDao The data access object used by this repository.
     */
    public JourneyRepositoryImpl(JourneyDao journeyDao) {
        this.journeyDao = journeyDao;
    }

    /**
     * Get all journeys in database. This is automatically an asynchronous operation due to its return type.
     * @return All journeys in database.
     */
    @Override
    public LiveData<List<Journey>> getJourneys() {
        return journeyDao.getJourneys();
    }

    /**
     * Add a new journey into database using {@link AsyncTask}.
     * @param journey The journey to be added.
     */
    @Override
    public void addJourney(Journey journey) {
        AddJourneyTask task = new AddJourneyTask(journeyDao);
        task.execute(journey);
    }

    /**
     * The task that inserts a journey into database using separate thread.
     */
    private static class AddJourneyTask extends AsyncTask<Journey, Void, Void> {

        private JourneyDao journeyDao;

        AddJourneyTask(JourneyDao journeyDao) {
            this.journeyDao = journeyDao;
        }

        @Override
        protected Void doInBackground(Journey... journeys) {
            journeyDao.insertJourney(journeys[0]);
            return null;
        }
    }
}
