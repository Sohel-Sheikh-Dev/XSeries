package com.example.xseries.BottomNavigationTab;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.xseries.R;
import com.example.xseries.BottomNavigationTab.CollectionTab.MoviesTab.MovieFragment;
import com.example.xseries.BottomNavigationTab.CollectionTab.ShowsTab.ShowFragment;
import com.example.xseries.Adapter.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;
public class CollectionFragment extends Fragment {

    TabLayout tabLayout;
    ViewPager viewPager;

    Button shows,movies;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_collection, container, false);

        shows = view.findViewById(R.id.button4);
        movies = view.findViewById(R.id.button5);

        ShowFragment nextFrag= new ShowFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.showsMovies, nextFrag, "findThisFragment")
                .addToBackStack(null)
                .commit();

        shows.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowFragment nextFrag= new ShowFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.showsMovies, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });

        movies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovieFragment nextFrag= new MovieFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.showsMovies, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });

        return view;
    }

    private void addFragment(View view) {

        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.viewPager);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new ShowFragment(), "Shows");
        adapter.addFragment(new MovieFragment(), "Movies");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}