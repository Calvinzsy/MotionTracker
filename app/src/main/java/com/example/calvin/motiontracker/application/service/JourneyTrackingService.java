package com.example.calvin.motiontracker.application.service;

import android.Manifest;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;

import com.example.calvin.motiontracker.application.JourneyApplication;
import com.example.calvin.motiontracker.data.JourneyRepository;
import com.example.calvin.motiontracker.model.Journey;
import com.example.calvin.motiontracker.model.Location;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.util.Calendar;

import javax.inject.Inject;

public class JourneyTrackingService extends Service {

    public static final String EVENT_JOURNEY_UPDATED = "journey_updated";

    public static final String EVENT_JOURNEY_UPDATE_REQUEST = "update_journey";

    public static final String KEY_JOURNEY = "journey";

    private static final long UPDATE_INTERVAL = 10000;
    private static final long FASTEST_INTERVAL = 5000;

    @Inject
    JourneyRepository journeyRepository;

    private FusedLocationProviderClient locationClient;

    private LocationRequest locationRequest;

    private JourneyLocationCallback journeyLocationCallback;

    private Journey journey;

    private BroadcastReceiver journeyUpdateRequestReceiver;

    private class JourneyLocationCallback extends LocationCallback {

        public void onLocationResult(LocationResult result) {

            Location location = new Location(result.getLastLocation().getLatitude(), result.getLastLocation().getLongitude());
            journey.addLocation(location);
            notifyJourneyUpdated();
        }
    }

    @Override
    public void onCreate() {
        JourneyApplication.getApplicationComponent().inject(this);
        locationClient = LocationServices.getFusedLocationProviderClient(this);
        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(UPDATE_INTERVAL);
        locationRequest.setFastestInterval(FASTEST_INTERVAL);
        journeyLocationCallback = new JourneyLocationCallback();
        journeyUpdateRequestReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                notifyJourneyUpdated();
            }
        };
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocalBroadcastManager.getInstance(this).registerReceiver(journeyUpdateRequestReceiver, new IntentFilter(JourneyTrackingService.EVENT_JOURNEY_UPDATE_REQUEST));
            journey = new Journey();
            journey.setStartTime(getCurrentTime());
            locationClient.requestLocationUpdates(locationRequest, journeyLocationCallback, Looper.myLooper());
        }
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(journeyUpdateRequestReceiver);
        storeCurrentJourney();
    }

    private void storeCurrentJourney() {
        if (journey != null && !journey.getPath().isEmpty()) {
            locationClient.removeLocationUpdates(journeyLocationCallback);
            journey.setEndTime(getCurrentTime());
            journeyRepository.addJourney(journey);
            journey = null;
        }
    }

    private long getCurrentTime() {
        return Calendar.getInstance().getTimeInMillis();
    }

    private void notifyJourneyUpdated() {
        Intent intent = new Intent(EVENT_JOURNEY_UPDATED);
        intent.putExtra(KEY_JOURNEY, journey);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}
