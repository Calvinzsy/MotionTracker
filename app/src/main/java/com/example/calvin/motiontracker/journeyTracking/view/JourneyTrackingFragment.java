package com.example.calvin.motiontracker.journeyTracking.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.calvin.motiontracker.R;
import com.example.calvin.motiontracker.application.service.JourneyTrackingService;
import com.example.calvin.motiontracker.model.Journey;
import com.example.calvin.motiontracker.model.Location;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * The fragment that shows live journey on a map view.
 */
public class JourneyTrackingFragment extends Fragment implements OnMapReadyCallback {

    /**
     * The journey map.
     */
    private MapView mapView;

    /**
     * The {@link BroadcastReceiver} used for listening to journey updates posted from {@link JourneyTrackingService}.
     */
    private BroadcastReceiver journeyUpdateReceiver;

    /**
     * The polyline drawn on map.
     */
    private Polyline polyline;

    /**
     * The google map associated with the map view.
     */
    private GoogleMap googleMap;

    /**
     * Locations of recently updated journey.
     */
    private List<LatLng> journeyLocations;

    /**
     * Determines if the map is loaded.
     */
    private boolean mapReady;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        journeyUpdateReceiver = new BroadcastReceiver(){
            @Override
            public void onReceive(Context context, Intent intent) {
                //If we receive a message before the map is loaded, store the result.
                //Otherwise, update map directly.
                Journey journey = intent.getParcelableExtra(JourneyTrackingService.KEY_JOURNEY);
                if (mapReady) {
                    updateCurrentJourney(journey);
                }else {
                    storeJourneyLocations(journey);
                }
            }
        };
        journeyLocations = new ArrayList<>();
        mapReady = false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.journey_tracking, container, false);
        mapView = view.findViewById(R.id.journeyTrackingMap);
        if (mapView != null) {
            mapView.onCreate(savedInstanceState);
            mapView.getMapAsync(this);
        }
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(journeyUpdateReceiver, new IntentFilter(JourneyTrackingService.EVENT_JOURNEY_UPDATED));
        //Request a instant update when the fragment is started so that we don't have to wait for next broadcast.
        //This is useful when tracking service has a long interval for posting update.
        requestJourneyUpdate();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        mapView.onSaveInstanceState(savedInstanceState);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onStop() {
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(journeyUpdateReceiver);
        super.onStop();
    }

    @Override
    public void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        if (this.googleMap == null) {
            this.googleMap = map;
        }
        PolylineOptions polylineOptions = new PolylineOptions()
                .color(ContextCompat.getColor(getActivity(), R.color.colorPrimary)).width(12f);
        googleMap.clear();
        polyline = googleMap.addPolyline(polylineOptions);
        googleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                mapReady = true;
                updateJourneyMap();
            }
        });
    }

    /**
     * Send out broadcast message for instant journey update request.
     */
    private void requestJourneyUpdate() {
        Intent intent = new Intent(JourneyTrackingService.EVENT_JOURNEY_UPDATE_REQUEST);
        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
    }

    /**
     * Update current journey on map.
     * @param journey The latest journey.
     */
    private void updateCurrentJourney(Journey journey) {
        storeJourneyLocations(journey);
        updateJourneyMap();
    }

    /**
     * Store journey locations.
     * @param journey The journey of which locations to be stored.
     */
    private void storeJourneyLocations(Journey journey) {
        if (journey != null ) {
            journeyLocations.clear();
            List<Location> locations = journey.getPath();
            for (Location location : locations) {
                journeyLocations.add(new LatLng(location.getLatitude(), location.getLongitude()));
            }
        }
    }

    /**
     * Update map view with the journey and brings the camera to last location of current journey.
     */
    private void updateJourneyMap() {
        if (!journeyLocations.isEmpty()) {
            polyline.setPoints(journeyLocations);
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(journeyLocations.get(journeyLocations.size() - 1), 18f);
            googleMap.moveCamera(cameraUpdate);
        }
    }
}
