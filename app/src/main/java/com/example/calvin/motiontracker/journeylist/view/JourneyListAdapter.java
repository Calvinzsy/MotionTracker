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

/**
 * The adapter used by journey list.
 */
public class JourneyListAdapter extends RecyclerView.Adapter<JourneyListAdapter.ViewHolder> {

    /**
     * The list of journeys associated with this adapter.
     */
    private List<Journey> journeys = new ArrayList<>();

    /**
     * The listener that gets invoked when a journey is selected from the list.
     */
    private OnItemSelectedListener onItemSelectedListener;

    /**
     * The listener that gets invoked when a journey is selected from the list.
     */
    public interface OnItemSelectedListener {
        void onItemSelected(Journey journey);
    }

    void setOnItemSelectedListener(OnItemSelectedListener onItemSelectedListener) {
        this.onItemSelectedListener = onItemSelectedListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView journeyTitle;

        ViewHolder(View itemView) {
            super(itemView);
            journeyTitle = itemView.findViewById(R.id.journeyTitle);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getLayoutPosition();
                    Journey journey = journeys.get(position);
                    onItemSelectedListener.onItemSelected(journey);
                }
            });
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
    }

    @Override
    public int getItemCount() {
        return journeys.size();
    }

    void setJourneys(List<Journey> journeys) {
        this.journeys = journeys;
        notifyDataSetChanged();
    }
}
