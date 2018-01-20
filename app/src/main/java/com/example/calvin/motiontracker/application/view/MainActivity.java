package com.example.calvin.motiontracker.application.view;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.calvin.motiontracker.R;
import com.example.calvin.motiontracker.application.service.JourneyTrackingService;
import com.example.calvin.motiontracker.journeydetail.view.JourneyDetailActivity;
import com.example.calvin.motiontracker.journeylist.view.JourneyListFragment;
import com.example.calvin.motiontracker.model.Journey;

public class MainActivity extends AppCompatActivity implements JourneyListFragment.OnJourneySelectedListener {

    private static final int PERMISSION_REQUEST_ACCESS_FINE_LOCATION = 10000;

    private CoordinatorLayout layout;

    private Switch trackingServiceSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layout = findViewById(R.id.contentContainer);

        ViewPager viewPager = findViewById(R.id.contentViewPager);
        ContentPagerAdapter contentPagerAdapter = new ContentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(contentPagerAdapter);
        viewPager.setPageTransformer(true, new FlipPageTransformer());

        TabLayout tabLayout = findViewById(R.id.contentTabs);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(viewPager);

        trackingServiceSwitch = findViewById(R.id.trackingServiceSwitch);
        trackingServiceSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    trackingServiceSwitch.setChecked(false);
                    requestPermission();
                }else {
                    if (isChecked) {
                        startTrackingService();
                    } else {
                        stopTrackingService();
                    }
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        switch (requestCode) {
            case PERMISSION_REQUEST_ACCESS_FINE_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    trackingServiceSwitch.setChecked(true);
                }else {
                    boolean shouldShowRequestPermissionRationale = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION);
                    if (shouldShowRequestPermissionRationale) {
                        showRequestPermissionRational();
                    }else {
                        showApplicationPermissionSettings();
                    }
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }
    }

    private void showRequestPermissionRational() {
                new AlertDialog.Builder(this)
                .setMessage(R.string.permission_request_rationale)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        requestPermission();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_ACCESS_FINE_LOCATION);
    }

    private void showApplicationPermissionSettings() {
        Snackbar snackbar = Snackbar.make(layout, R.string.check_permission_settings, Snackbar.LENGTH_LONG);
        snackbar.setAction(R.string.ok, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + getPackageName()));
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        snackbar.show();
    }

    @Override
    public void onJourneySelected(Journey journey) {
        Intent intent = new Intent(this, JourneyDetailActivity.class);
        intent.putExtra(JourneyDetailActivity.KEY_JOURNEY, journey);
        startActivity(intent);
    }

    private void startTrackingService() {
        Intent service = new Intent(this, JourneyTrackingService.class);
        startService(service);
    }

    private void stopTrackingService() {
        Intent service = new Intent(this, JourneyTrackingService.class);
        stopService(service);
    }
}
