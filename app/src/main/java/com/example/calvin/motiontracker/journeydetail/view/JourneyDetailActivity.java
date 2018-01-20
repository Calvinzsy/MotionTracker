package com.example.calvin.motiontracker.journeydetail.view;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.calvin.motiontracker.R;
import com.example.calvin.motiontracker.model.Journey;
import com.example.calvin.motiontracker.model.Location;
import com.example.calvin.motiontracker.util.Utils;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.SphericalUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JourneyDetailActivity extends AppCompatActivity implements OnMapReadyCallback {

    public static final String KEY_JOURNEY = "journey";

    private static final String STATE_JOURNEY = "state_journey";

    private MapView mapView;

    private GoogleMap googleMap;

    private Journey journey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journey_detail);

        if (savedInstanceState != null){
            journey = savedInstanceState.getParcelable(STATE_JOURNEY);
            savedInstanceState.remove(STATE_JOURNEY);
        }else {
            Bundle extra = getIntent().getExtras();
            if (extra != null) {
                journey = extra.getParcelable(KEY_JOURNEY);
            }
        }

        mapView = findViewById(R.id.journeyMap);
        if (mapView != null) {
            mapView.onCreate(savedInstanceState);
            mapView.getMapAsync(this);
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null){
            toolbar.setTitle("");
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        mapView.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putParcelable(STATE_JOURNEY, journey);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (this.googleMap == null) {
            this.googleMap = googleMap;
        }
        double length = updateJourneyMap(journey);
        updateJourneyDetail(journey, length);
    }

    private double updateJourneyMap(Journey journey) {

        List<Location> path = journey.getPath();
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        List<LatLng> list = new ArrayList<>();
        for (Location location : path) {
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            builder.include(latLng);
            list.add(latLng);
        }
        PolylineOptions polylineOptions = new PolylineOptions()
                .addAll(list)
                .color(ContextCompat.getColor(this, R.color.colorPrimary)).width(12f);
        googleMap.clear();
        googleMap.addPolyline(polylineOptions);

        final LatLngBounds bounds = builder.build();
        googleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, getResources().getDimensionPixelSize(R.dimen.map_padding));
                googleMap.moveCamera(cameraUpdate);
            }
        });
        return SphericalUtil.computeLength(list);
    }

    private void updateJourneyDetail(Journey journey, double journeyLength) {
        TextView startTime = findViewById(R.id.startTime);
        TextView endTime = findViewById(R.id.endTime);
        TextView duration = findViewById(R.id.duration);
        TextView length = findViewById(R.id.length);
        TextView speed = findViewById(R.id.speed);
        startTime.setText(Utils.formatDate(new Date(journey.getStartTime())));
        endTime.setText(Utils.formatDate(new Date(journey.getEndTime())));
        long timeDifference = (journey.getEndTime() - journey.getStartTime()) / 1000;
        duration.setText(getString(R.string.duration, timeDifference));
        length.setText(getString(R.string.length, (int)journeyLength));
        speed.setText(timeDifference == 0 ? getString(R.string.not_applicable) : getString(R.string.speed, (int)journeyLength / timeDifference));
    }
}
