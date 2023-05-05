package com.example.xseries.View;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.xseries.CollectionFragment;
import com.example.xseries.DiscoverFragment;
import com.example.xseries.R;
import com.example.xseries.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {


    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragments(new ExploreFragment());


        binding.bottomNav.setOnItemSelectedListener(item -> {

            switch (item.getItemId()) {

                case R.id.bottom_nav_explore:
                    replaceFragments(new ExploreFragment());
                    break;
                case R.id.bottom_nav_discover:
                    replaceFragments(new DiscoverFragment());
                    break;
                case R.id.bottom_nav_collection:
                    replaceFragments(new CollectionFragment());
                    break;
            }

            return true;
        });



    }

    private void replaceFragments(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

}

