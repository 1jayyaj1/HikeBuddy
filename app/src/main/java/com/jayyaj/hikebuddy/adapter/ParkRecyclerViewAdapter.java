package com.jayyaj.hikebuddy.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jayyaj.hikebuddy.R;
import com.jayyaj.hikebuddy.model.Park;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ParkRecyclerViewAdapter extends RecyclerView.Adapter<ParkRecyclerViewAdapter.ViewHolder> {
    private final List<Park> parkList;
    private final OnParkClickListener parkClickListener;

    public ParkRecyclerViewAdapter(List<Park> parkList, OnParkClickListener parkClickListener) {
        this.parkList = parkList;
        this.parkClickListener = parkClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.park_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Park park = parkList.get(position);
        holder.parkName.setText(park.getName());
        holder.parkType.setText(park.getDesignation());
        holder.parkState.setText(park.getStates());
        if (park.getImages().size() > 0) {
            Picasso.get().load(park.getImages().get(0).getUrl())
                    .placeholder(android.R.drawable.stat_sys_download)
                    .error(android.R.drawable.stat_notify_error)
                    .resize(300,300)
                    .into(holder.parkImage);
        }
    }

    @Override
    public int getItemCount() {
        return parkList.size();
    }

    //ItemView is the view that we inflated from park row in the onCreateViewHolder methods
    //Fields are public so that we can ACCESS them from onBindViewHolder
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView parkImage;
        public TextView parkName;
        public TextView parkType;
        public TextView parkState;
        private OnParkClickListener onParkClickListener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parkImage = itemView.findViewById(R.id.rowParkImage);
            parkName = itemView.findViewById(R.id.rowParkNameTextView);
            parkType = itemView.findViewById(R.id.rowParkTypeTextView);
            parkState = itemView.findViewById(R.id.rowParkStateView);
            this.onParkClickListener = parkClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Park currPark = parkList.get(getAdapterPosition());
            onParkClickListener.onParkClicked(currPark);
        }
    }
}
