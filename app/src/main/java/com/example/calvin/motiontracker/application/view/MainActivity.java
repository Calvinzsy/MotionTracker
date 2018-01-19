package com.example.calvin.motiontracker.application.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.calvin.motiontracker.R;
import com.example.calvin.motiontracker.journeydetail.view.JourneyDetailActivity;
import com.example.calvin.motiontracker.journeylist.view.JourneyListFragment;
import com.example.calvin.motiontracker.model.Journey;

public class MainActivity extends AppCompatActivity implements JourneyListFragment.OnJourneySelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = findViewById(R.id.contentViewPager);
        ContentPagerAdapter contentPagerAdapter = new ContentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(contentPagerAdapter);
        viewPager.setPageTransformer(true, new FlipPageTransformer());

        TabLayout tabLayout = findViewById(R.id.contentTabs);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onJourneySelected(Journey journey) {
        Intent intent = new Intent(this, JourneyDetailActivity.class);
        intent.putExtra(JourneyDetailActivity.KEY_JOURNEY, journey);
        startActivity(intent);
    }
}
