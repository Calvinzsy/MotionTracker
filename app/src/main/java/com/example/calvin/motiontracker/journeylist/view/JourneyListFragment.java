package com.example.calvin.motiontracker.journeylist.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.calvin.motiontracker.R;
import com.example.calvin.motiontracker.application.JourneyApplication;
import com.example.calvin.motiontracker.journeylist.module.DaggerJourneyListComponent;
import com.example.calvin.motiontracker.journeylist.module.JourneyListModule;
import com.example.calvin.motiontracker.journeylist.viewmodel.JourneyListViewModel;
import com.example.calvin.motiontracker.model.Journey;

import java.util.List;

import javax.inject.Inject;

public class JourneyListFragment extends Fragment {

    @Inject
    ViewModelProvider.Factory factory;

    private JourneyListAdapter journeyListAdapter;

    private OnJourneySelectedListener onJourneySelectedListener;

    public interface OnJourneySelectedListener {
        void onJourneySelected(Journey journey);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnJourneySelectedListener) {
            onJourneySelectedListener = (OnJourneySelectedListener) context;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        DaggerJourneyListComponent.builder()
                .applicationComponent(JourneyApplication.getApplicationComponent())
                .journeyListModule(new JourneyListModule())
                .build()
                .inject(this);

        journeyListAdapter = new JourneyListAdapter();
        journeyListAdapter.setOnItemSelectedListener(new JourneyListAdapter.OnItemSelectedListener() {
            @Override
            public void onItemSelected(Journey journey) {
                if (onJourneySelectedListener != null) {
                    onJourneySelectedListener.onJourneySelected(journey);
                }
            }
        });
        JourneyListViewModel journeyListViewModel = ViewModelProviders.of(this, factory).get(JourneyListViewModel.class);
        journeyListViewModel.getJourneys().observe(this, new Observer<List<Journey>>() {
            @Override
            public void onChanged(@Nullable List<Journey> journeys) {
                journeyListAdapter.setJourneys(journeys);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.journey_list, container, false);
        RecyclerView journeyList = view.findViewById(R.id.journeyList);
        journeyList.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        journeyList.setLayoutManager(layoutManager);
        journeyList.setAdapter(journeyListAdapter);

        return view;
    }
}
