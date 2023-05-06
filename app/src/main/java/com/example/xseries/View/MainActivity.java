package com.example.xseries.View;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.xseries.BottomNavigationTab.CollectionFragment;
import com.example.xseries.BottomNavigationTab.DiscoverFragment;
import com.example.xseries.BottomNavigationTab.ExploreFragment;
import com.example.xseries.R;
import com.example.xseries.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    static Button mainEditText;

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragments(new ExploreFragment());

        mainEditText = findViewById(R.id.search);

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

        search();


    }
    private void search() {
        mainEditText.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), SearchActivity.class);
//            intent.putExtra("query", mainEditText.toString());
            startActivity(intent);
/*
            if (TextUtils.isEmpty(mainEditText.getText().toString())) {
                mainEditText.setError("Please enter the query");
            }
            else {
                mainClickedOrDetailsClicked = true;
                TeleVmainClickedOrDetailsClicked = true;
                Intent intent = new Intent(view.getContext(), SearchActivity.class);
                intent.putExtra("query", mainEditText.toString());
                startActivity(intent);
//                Toast.makeText(view.getContext(), "" + mainEditText.getText().toString(), Toast.LENGTH_SHORT).show();
            }
*/
        });
    }

    private void replaceFragments(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

}

