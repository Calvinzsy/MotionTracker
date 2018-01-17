package com.example.calvin.motiontracker.data;

import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.calvin.motiontracker.model.Journey;

import java.util.List;

public class JourneyRepositoryImpl implements JourneyRepository {

    private final JourneyDatabase journeyDatabase;

    public JourneyRepositoryImpl(JourneyDatabase journeyDatabase) {
        this.journeyDatabase = journeyDatabase;
    }

    @Override
    public LiveData<List<Journey>> getJourneys() {
        return journeyDatabase.journeyDao().getJourneys();
    }

    @Override
    public void addJourney(Journey journey) {
        AddJourneyTask task = new AddJourneyTask(journeyDatabase);
        task.execute(journey);
    }

    private static class AddJourneyTask extends AsyncTask<Journey, Void, Void> {

        private JourneyDatabase journeyDatabase;

        AddJourneyTask(JourneyDatabase journeyDatabase) {
            this.journeyDatabase = journeyDatabase;
        }

        @Override
        protected Void doInBackground(Journey... journeys) {
            journeyDatabase.journeyDao().insertJourney(journeys[0]);
            return null;
        }
    }
}
