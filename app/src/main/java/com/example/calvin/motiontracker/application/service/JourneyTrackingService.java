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
import com.example.calvin.motiontracker.util.Utils;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import javax.inject.Inject;

/**
 * The journey tracking service that once started, will periodically request and broadcast journey updates.
 * With each run of the service, a complete journey will be added to {@link JourneyRepository}. In addition to it,
 * The service also listens to broadcast messages that request for information of current journey.
 */
public class JourneyTrackingService extends Service {

    /**
     * The event used by service to broadcast journey updates.
     */
    public static final String EVENT_JOURNEY_UPDATED = "journey_updated";

    /**
     * The event used by service to listen to journey information requests.
     */
    public static final String EVENT_JOURNEY_UPDATE_REQUEST = "update_journey";

    /**
     * The key used for passing {@link Journey} in broadcast messages.
     */
    public static final String KEY_JOURNEY = "journey";

    /**
     * Regular update interval for current location.
     */
    private static final long UPDATE_INTERVAL = 10000;

    /**
     * Fastest update interval for current location.
     */
    private static final long FASTEST_INTERVAL = 5000;

    /**
     * {@link JourneyRepository} used for storing journey before service is stopped.
     */
    @Inject
    JourneyRepository journeyRepository;

    /**
     * Location client used for requesting location updates.
     */
    private FusedLocationProviderClient locationClient;

    /**
     * Location update request.
     */
    private LocationRequest locationRequest;

    /**
     * Location update callback.
     */
    private JourneyLocationCallback journeyLocationCallback;

    /**
     * Current journey.
     */
    private Journey journey;

    /**
     * The {@link BroadcastReceiver} used for listening to journey information request.
     */
    private BroadcastReceiver journeyUpdateRequestReceiver;

    /**
     * Custom {@link LocationCallback} that appends the last location in the result to current journey
     * and notifies it.
     */
    private class JourneyLocationCallback extends LocationCallback {

        public void onLocationResult(LocationResult result) {

            Location location = new Location(result.getLastLocation().getLatitude(), result.getLastLocation().getLongitude());
            journey.addLocation(location);
            notifyJourneyUpdated();
        }
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocalBroadcastManager.getInstance(this).registerReceiver(journeyUpdateRequestReceiver, new IntentFilter(JourneyTrackingService.EVENT_JOURNEY_UPDATE_REQUEST));
            journey = new Journey();
            journey.setStartTime(Utils.getCurrentTime());
            locationClient.requestLocationUpdates(locationRequest, journeyLocationCallback, Looper.myLooper());
        }
        return START_NOT_STICKY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(journeyUpdateRequestReceiver);
        storeCurrentJourney();
    }

    /**
     * Store the complete journey into {@link JourneyRepository}.
     */
    private void storeCurrentJourney() {
        if (journey != null && !journey.getPath().isEmpty()) {
            locationClient.removeLocationUpdates(journeyLocationCallback);
            journey.setEndTime(Utils.getCurrentTime());
            journeyRepository.addJourney(journey);
            journey = null;
        }
    }

    /**
     * Broadcast information for current journey.
     */
    private void notifyJourneyUpdated() {
        Intent intent = new Intent(EVENT_JOURNEY_UPDATED);
        intent.putExtra(KEY_JOURNEY, journey);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}
