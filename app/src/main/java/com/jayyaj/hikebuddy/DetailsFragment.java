package com.jayyaj.hikebuddy;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

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
        viewPager = view.findViewById(R.id.detailsViewPager);

        parkViewModel = new ViewModelProvider(requireActivity())
                .get(ParkViewModel.class);
        parkViewModel.getSelectedPark().observe(this, park -> {
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