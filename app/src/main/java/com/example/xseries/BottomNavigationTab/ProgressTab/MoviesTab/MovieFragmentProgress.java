package com.example.xseries.BottomNavigationTab.ProgressTab.MoviesTab;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.xseries.BottomNavigationTab.ProgressTab.HiddenProgress;
import com.example.xseries.BottomNavigationTab.ProgressTab.WatchlistProgress;
import com.example.xseries.R;
import com.example.xseries.Adapter.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class MovieFragmentProgress extends Fragment {

    TabLayout tabLayout;
    ViewPager viewPager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_movie_progress, container, false);
        addFragment(view);

        return view;
    }


    private void addFragment(View view) {

        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.viewPager);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new My_MoviesProgress(), "Movies");
        adapter.addFragment(new WatchlistProgress(), "Watchlist");
        adapter.addFragment(new HiddenProgress(), "Hidden");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

}