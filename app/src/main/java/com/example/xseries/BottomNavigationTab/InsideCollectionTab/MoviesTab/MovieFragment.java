package com.example.xseries.BottomNavigationTab.InsideCollectionTab.MoviesTab;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.xseries.BottomNavigationTab.InsideCollectionTab.Hidden;
import com.example.xseries.BottomNavigationTab.InsideCollectionTab.Watchlist;
import com.example.xseries.R;
import com.example.xseries.Adapter.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class MovieFragment extends Fragment {

    TabLayout tabLayout;
    ViewPager viewPager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        addFragment(view);

        return view;
    }


    private void addFragment(View view) {

        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.viewPager);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new My_Movies(), "Movies");
        adapter.addFragment(new Watchlist(), "Watchlist");
        adapter.addFragment(new Hidden(), "Hidden");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

}