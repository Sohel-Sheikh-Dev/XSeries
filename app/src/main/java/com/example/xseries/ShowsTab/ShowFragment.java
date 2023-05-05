package com.example.xseries.ShowsTab;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.xseries.R;
import com.example.xseries.ShowsTab.Hidden;
import com.example.xseries.ShowsTab.My_Shows;
import com.example.xseries.ShowsTab.Watchlist;
import com.example.xseries.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class ShowFragment extends Fragment {
    TabLayout tabLayout;
    ViewPager viewPager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_show, container, false);
        addFragment(view);
        return view;
    }

    private void addFragment(View view) {

        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.viewPager);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new My_Shows(), "Shows");
        adapter.addFragment(new Watchlist(), "Watchlist");
        adapter.addFragment(new Hidden(), "Hidden");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

}