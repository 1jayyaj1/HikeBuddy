package com.jayyaj.hikebuddy;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jayyaj.hikebuddy.adapter.ParkRecyclerViewAdapter;
import com.jayyaj.hikebuddy.data.AsyncResponse;
import com.jayyaj.hikebuddy.data.Repository;
import com.jayyaj.hikebuddy.model.Park;

import java.util.List;

public class ParksFragment extends Fragment {

    private RecyclerView recyclerView;
    private ParkRecyclerViewAdapter parkRecyclerViewAdapter;
    private List<Park> parkList;

    public ParksFragment() {
        // Required empty public constructor
    }


    public static ParksFragment newInstance() {
        ParksFragment fragment = new ParksFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Repository.getPark(parks -> {
            parkRecyclerViewAdapter = new ParkRecyclerViewAdapter(parks);
            recyclerView.setAdapter(parkRecyclerViewAdapter);
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_parks, container, false);
        recyclerView = view.findViewById(R.id.parkRecycler);
        recyclerView.setHasFixedSize(true);
        //getActivity instead of view/this cuz we want the activity in which the fragment
        //is hosted in
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }
}
