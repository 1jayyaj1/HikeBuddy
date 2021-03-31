package com.jayyaj.hikebuddy;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jayyaj.hikebuddy.adapter.ParkRecyclerViewAdapter;
import com.jayyaj.hikebuddy.adapter.ViewPagerAdapter;
import com.jayyaj.hikebuddy.model.Park;
import com.jayyaj.hikebuddy.model.ParkViewModel;

import java.util.List;
import java.util.Observable;

public class DetailsFragment extends Fragment {
    private ParkViewModel parkViewModel;
    private ViewPager2 viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private TextView parkName;
    private TextView parkDesignation;
    private TextView parkDescription;
    private TextView parkActivities;
    private TextView parkEntranceFees;
    private TextView parkOperatingHours;
    private TextView parkTopics;
    private TextView parkDirections;

    public DetailsFragment() {
        // Required empty public constructor
    }

    public static DetailsFragment newInstance() {
        DetailsFragment fragment = new DetailsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager = view.findViewById(R.id.details_viewpager);
        parkName = view.findViewById(R.id.details_park_name);
        parkDesignation = view.findViewById(R.id.details_park_designation);
        parkDescription = view.getRootView().findViewById(R.id.details_description);
        parkActivities = view.getRootView().findViewById(R.id.details_activities);
        parkEntranceFees = view.getRootView().findViewById(R.id.details_entrancefees);
        parkOperatingHours = view.getRootView().findViewById(R.id.details_operatinghours);
        parkTopics = view.getRootView().findViewById(R.id.details_topics);
        parkDirections = view.getRootView().findViewById(R.id.details_directions);

        parkViewModel = new ViewModelProvider(requireActivity())
                .get(ParkViewModel.class);

        parkViewModel.getSelectedPark().observe(this, park -> {
            parkName.setText(park.getName());
            parkDesignation.setText(park.getDesignation());
            parkDescription.setText(park.getDescription());

            StringBuilder strBuilderActivities = new StringBuilder();
            for (int i = 0; i < park.getActivities().size(); i++) {
                strBuilderActivities.append(park.getActivities().get(i).getName());
                if (i != park.getActivities().size() - 1)
                    strBuilderActivities.append(" | ");
            }
            parkActivities.setText(strBuilderActivities);

            if (park.getEntranceFees().size() > 0) {
                StringBuilder strBuilderEntranceFees = new StringBuilder();
                for (int i = 0; i < park.getEntranceFees().size(); i++) {
                    strBuilderEntranceFees.append(park.getEntranceFees().get(i).getTitle())
                            .append(": ").append(park.getEntranceFees().get(i).getCost()).append("\n")
                            .append(park.getEntranceFees().get(i).getDescription());

                    if (i != park.getEntranceFees().size() - 1)
                        strBuilderEntranceFees.append("\n\n");
                }
                parkEntranceFees.setText(strBuilderEntranceFees);
            } else {
                parkEntranceFees.setText(R.string.no_fees);
            }

            StringBuilder strBuilderOperatingHours = new StringBuilder();
            strBuilderOperatingHours
                    .append("Monday: ").append(park.getOperatingHours().get(0).getStandardHours().getMonday()).append("\n")
                    .append("Tuesday: ").append(park.getOperatingHours().get(0).getStandardHours().getTuesday()).append("\n")
                    .append("Wednesday: ").append(park.getOperatingHours().get(0).getStandardHours().getWednesday()).append("\n")
                    .append("Thursday: ").append(park.getOperatingHours().get(0).getStandardHours().getThursday()).append("\n")
                    .append("Friday: ").append(park.getOperatingHours().get(0).getStandardHours().getFriday()).append("\n")
                    .append("Saturday: ").append(park.getOperatingHours().get(0).getStandardHours().getSaturday()).append("\n")
                    .append("Sunday: ").append(park.getOperatingHours().get(0).getStandardHours().getSunday());
            parkOperatingHours.setText(strBuilderOperatingHours);

            StringBuilder strBuilderTopics = new StringBuilder();
            for (int i = 0; i < park.getTopics().size(); i++) {
                strBuilderTopics.append(park.getTopics().get(i).getName());
                if (i != park.getTopics().size() - 1)
                    strBuilderTopics.append(" | ");
            }
            parkTopics.setText(strBuilderTopics);

            if (!TextUtils.isEmpty(park.getDirectionsInfo()))
                parkDirections.setText(park.getDirectionsInfo());
            else
                parkDirections.setText(R.string.no_directions);

            viewPagerAdapter = new ViewPagerAdapter(park.getImages());
            viewPager.setAdapter(viewPagerAdapter);
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false);
    }
}