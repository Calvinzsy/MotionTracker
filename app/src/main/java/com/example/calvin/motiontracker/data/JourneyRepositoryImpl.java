package com.example.calvin.motiontracker.data;

import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.calvin.motiontracker.model.Journey;

import java.util.List;

public class JourneyRepositoryImpl implements JourneyRepository {

    private final JourneyDao journeyDao;

    public JourneyRepositoryImpl(JourneyDao journeyDao) {
        this.journeyDao = journeyDao;
    }

    @Override
    public LiveData<List<Journey>> getJourneys() {
        return journeyDao.getJourneys();
    }

    @Override
    public void addJourney(Journey journey) {
        AddJourneyTask task = new AddJourneyTask(journeyDao);
        task.execute(journey);
    }

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
