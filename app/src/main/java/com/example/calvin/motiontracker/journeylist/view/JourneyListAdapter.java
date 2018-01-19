package com.example.calvin.motiontracker.journeylist.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.calvin.motiontracker.R;
import com.example.calvin.motiontracker.model.Journey;
import com.example.calvin.motiontracker.util.Utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JourneyListAdapter extends RecyclerView.Adapter<JourneyListAdapter.ViewHolder> {

    private List<Journey> journeys = new ArrayList<>();

    private OnItemSelectedListener onItemSelectedListener;

    private JourneyItemOnClickListener listener = new JourneyItemOnClickListener();

    public interface OnItemSelectedListener {
        void onItemSelected(Journey journey);
    }

    public void setOnItemSelectedListener(OnItemSelectedListener onItemSelectedListener) {
        this.onItemSelectedListener = onItemSelectedListener;
    }

    private class JourneyItemOnClickListener implements View.OnClickListener {

        private int position = -1;

        public void setPosition(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            if (onItemSelectedListener != null && position >= 0) {
                Journey journey = journeys.get(position);
                onItemSelectedListener.onItemSelected(journey);
            }
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView journeyTitle;

        ViewHolder(View itemView) {
            super(itemView);
            journeyTitle = itemView.findViewById(R.id.journeyTitle);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.journey_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Journey journey = journeys.get(position);
        String title = Utils.formatDate(new Date(journey.getStartTime())) + " - " + Utils.formatDate(new Date(journey.getEndTime()));
        holder.journeyTitle.setText(title);
        listener.setPosition(position);
        holder.itemView.setOnClickListener(listener);
    }

    @Override
    public int getItemCount() {
        return journeys.size();
    }

    public void setJourneys(List<Journey> journeys) {
        this.journeys = journeys;
        notifyDataSetChanged();
    }
}
