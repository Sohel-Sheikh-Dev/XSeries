package com.example.xseries.BottomNavigationTab;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.xseries.BottomNavigationTab.InsideProgressTab.MoviesTab.MovieFragmentProgress;
import com.example.xseries.BottomNavigationTab.InsideProgressTab.ShowsTab.ShowFragmentProgress;
import com.example.xseries.R;
import com.google.android.material.tabs.TabLayout;

public class DiscoverFragment extends Fragment {
    TabLayout tabLayout;
    ViewPager viewPager;

    Button shows,movies;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_discover, container, false);

        shows = view.findViewById(R.id.button4);
        movies = view.findViewById(R.id.button5);

        ShowFragmentProgress nextFrag= new ShowFragmentProgress();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.showsMovies, nextFrag, "findThisFragment")
                .addToBackStack(null)
                .commit();

        shows.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowFragmentProgress nextFrag= new ShowFragmentProgress();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.showsMovies, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });

        movies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovieFragmentProgress nextFrag= new MovieFragmentProgress();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.showsMovies, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });

        // Inflate the layout for this fragment
        return view;
    }
}