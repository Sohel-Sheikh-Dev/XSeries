package com.example.xseries.BottomNavigationTab;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.xseries.Adapter.SeriesAdapter;
import com.example.xseries.BuildConfig;
import com.example.xseries.Model.Series_Model;
import com.example.xseries.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExploreFragment extends Fragment {
    private static final int PERMISSION_REQUEST_CODE = 1;
/*

    List<MoviesModel> moviesModelArrayListTop, moviesModelArrayListPop, moviesModelArrayListTrendM, moviesModelArrayListTrendTV, moviesModelArrayListUpcoming, moviesModelArrayListPosters;
    private ParentItemAdapter adapter;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    LinearLayoutManager linearLayoutManager;
    public List<ParentItem> itemList;
    LatestTrailerAdapter latestTrailerAdapter;
    RecyclerView trailerRecyclerView;
    LinearLayoutManager trailerLinearLayoutManager;
    @SuppressLint("StaticFieldLeak")
    static Button mainEditText;
    Button searchButton;
//    private Spinner dropdownMenu;

    private String a1, a2, a3, a4;
    private String[] imagesText;
    private SliderAdapter sliderAdapter;
    private SliderView sliderView;
    Toolbar toolbar;
    ImageView mainAccount, navigationMain;

    public static boolean mainClickedOrDetailsClicked;
    public static boolean TeleVmainClickedOrDetailsClicked;
*/

    List<Series_Model> seriesList;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    LinearLayoutManager linearLayoutManager;
    SeriesAdapter seriesAdapter;

    TextView hot;
    SwipeRefreshLayout swipeRefreshLayout;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

/*

        moviesModelArrayListTop = new ArrayList<>();
        moviesModelArrayListPop = new ArrayList<>();
        moviesModelArrayListTrendM = new ArrayList<>();
        moviesModelArrayListTrendTV = new ArrayList<>();
        moviesModelArrayListUpcoming = new ArrayList<>();
        moviesModelArrayListPosters = new ArrayList<>();


        sliderView = view.findViewById(R.id.slider);
        searchButton = view.findViewById(R.id.searchButtonMain);
        mainEditText = view.findViewById(R.id.search);
        progressBar = view.findViewById(R.id.progressBar);
        toolbar = view.findViewById(R.id.toolbar);
        mainAccount = view.findViewById(R.id.mainAccount);
        navigationMain = view.findViewById(R.id.navigationMain);

        recyclerView = view.findViewById(R.id.recyclerView);

        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.suppressLayout(true);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);


        getUpcomingMovies();
        getMoviesAndTVs();
        search();
        accountInfo();
        navigationInfo();
*/


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && !Environment.isExternalStorageManager()) {
            Uri uri = Uri.parse("package:" + BuildConfig.APPLICATION_ID);
            Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION, uri);
            startActivity(intent);

        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }

        View view = inflater.inflate(R.layout.fragment_explore, container, false);

        seriesList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recyclerView);
        progressBar = view.findViewById(R.id.progressBar);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.suppressLayout(true);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        seriesAdapter = new SeriesAdapter(getActivity(), seriesList);
        hot = view.findViewById(R.id.nofilter);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refreshLayout);

        // Refresh  the layout
        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {

                        Collections.shuffle(seriesList);
                        recyclerView.setAdapter(seriesAdapter);
                        seriesAdapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);

                    }
                }
        );

        DatabaseReference fetchLogin = FirebaseDatabase.getInstance().getReference().child("Videos");
        seriesList.clear();
        fetchLogin.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    progressBar.setVisibility(View.GONE);
                    Series_Model series_model = dataSnapshot.getValue(Series_Model.class);
                    Log.d("SERIES", "onDataChange: " + series_model.getId());
                    seriesList.add(series_model);
                }
//                Collections.shuffle(seriesList);
                recyclerView.setAdapter(seriesAdapter);
                seriesAdapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        hot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                swipeRefreshLayout.setRefreshing(true);
                Collections.shuffle(seriesList);
                recyclerView.setAdapter(seriesAdapter);
                seriesAdapter.notifyDataSetChanged();
                onRefresh();
            }
        });


        return view;
    }



    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 1000);
    }


/*
    private void navigationInfo() {
        navigationMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "This navigation bar is in Progress!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void accountInfo() {
        mainAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "This feature is in Progress!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void search() {
        mainEditText.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), SearchActivity.class);
//            intent.putExtra("query", mainEditText.toString());
            startActivity(intent);

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
});
    }

    public void searchMainText() {
        mainEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
//                    searchButton.setOnClickListener(view -> {
                    Intent intent = new Intent(getActivity(), SearchActivity.class);
                    intent.putExtra("query", mainEditText.toString());
                    startActivity(intent);
                    Toast.makeText(getActivity(), "Searching...", Toast.LENGTH_SHORT).show();
//                    });
                }
                return false;
            }
        });
    }

    public static String getMainEditText() {
        return mainEditText.getText().toString();
    }


    private void getUpcomingMovies() {

        Call<MoviesResponse> data = RetrofitInstance.getRetrofitInstance().getUpcomingMovies();
        data.enqueue(new Callback<MoviesResponse>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<MoviesResponse> call, @NonNull Response<MoviesResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    moviesModelArrayListUpcoming.addAll(response.body().getResults());
                    a1 = moviesModelArrayListUpcoming.get(0).getBackdrop_path();
                    a2 = moviesModelArrayListUpcoming.get(1).getBackdrop_path();
                    a3 = moviesModelArrayListUpcoming.get(2).getBackdrop_path();
                    a4 = moviesModelArrayListUpcoming.get(3).getBackdrop_path();

                    imagesText = new String[]{a1, a2, a3, a4};
                    Log.d("TAG", "onCreate: " + a1);

//                    sliderView.setSliderAdapter(sliderAdapter);

                    sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
                    sliderView.setIndicatorAnimation(IndicatorAnimationType.DROP);
                    sliderView.startAutoCycle();
//                    latestTrailerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(@NonNull Call<MoviesResponse> call, @NonNull Throwable t) {

            }
        });
    }


    private void getMoviesAndTVs() {

        itemList = new ArrayList<>();
        ParentItem itemPop = new ParentItem("What's Popular", moviesModelArrayListPop);
        getPopMovies();
        itemList.add(itemPop);

        ParentItem itemTop = new ParentItem("Top-Rated Movies", moviesModelArrayListTop);
        getTopRatedMovies();
        itemList.add(itemTop);

        ParentItem itemTrendM = new ParentItem("Trending Movies", moviesModelArrayListTrendM);
        getTrendingMovies();
        itemList.add(itemTrendM);

        ParentItem itemTrendTV = new ParentItem("Trending TV", moviesModelArrayListTrendTV);
        getTrendingTvShows();
        itemList.add(itemTrendTV);

//        ParentItem itemUp = new ParentItem("Upcoming");
//        getUpcomingMovies();
//        itemList.add(itemUp);

        adapter = new ParentItemAdapter(getActivity(), itemList);
        recyclerView.setAdapter(adapter);
    }


    private void getTopRatedMovies() {

        Call<MoviesResponse> data = RetrofitInstance.getRetrofitInstance().getTopRatedMovies();
        data.enqueue(new Callback<MoviesResponse>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<MoviesResponse> call, @NonNull Response<MoviesResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    progressBar.setVisibility(View.GONE);
                    moviesModelArrayListTop.addAll(response.body().getResults());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(@NonNull Call<MoviesResponse> call, @NonNull Throwable t) {

            }
        });
    }

    private void getPopMovies() {

        Call<MoviesResponse> data = RetrofitInstance.getRetrofitInstance().getPopMovies();
        data.enqueue(new Callback<MoviesResponse>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<MoviesResponse> call, @NonNull Response<MoviesResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    progressBar.setVisibility(View.GONE);
                    moviesModelArrayListPop.addAll(response.body().getResults());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(@NonNull Call<MoviesResponse> call, @NonNull Throwable t) {

            }
        });
    }

    private void getTrendingMovies() {

        Call<MoviesResponse> data = RetrofitInstance.getRetrofitInstance().getTrendingMovies();
        data.enqueue(new Callback<MoviesResponse>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<MoviesResponse> call, @NonNull Response<MoviesResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    progressBar.setVisibility(View.GONE);
                    moviesModelArrayListTrendM.addAll(response.body().getResults());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(@NonNull Call<MoviesResponse> call, @NonNull Throwable t) {

            }
        });
    }

    private void getTrendingTvShows() {

        Call<MoviesResponse> data = RetrofitInstance.getRetrofitInstance().getTrendingTvShows();
        data.enqueue(new Callback<MoviesResponse>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<MoviesResponse> call, @NonNull Response<MoviesResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    progressBar.setVisibility(View.GONE);
                    moviesModelArrayListTrendTV.addAll(response.body().getResults());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(@NonNull Call<MoviesResponse> call, @NonNull Throwable t) {

            }
        });
    }
*/

}