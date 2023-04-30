package com.example.xseries.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xseries.Adapter.LatestTrailerAdapter;
import com.example.xseries.Adapter.ParentItemAdapter;
import com.example.xseries.Adapter.SliderAdapter;
import com.example.xseries.Items.ParentItem;
import com.example.xseries.Model.MoviesModel;
import com.example.xseries.R;
import com.example.xseries.Response.MoviesResponse;
import com.example.xseries.Retrofit.RetrofitInstance;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

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
    static EditText mainEditText;
    Button searchButton;
//    private Spinner dropdownMenu;

    private String a1, a2, a3, a4;
    private String[] imagesText;
    private SliderAdapter sliderAdapter;
    private SliderView sliderView;
    Toolbar toolbar;
    ImageView mainAccount,navigationMain;

    public static boolean mainClickedOrDetailsClicked;
    public static boolean TeleVmainClickedOrDetailsClicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(toolbar);

        init();
        getUpcomingMovies();
        getMoviesAndTVs();
        search();
        accountInfo();
        navigationInfo();

    }

    private void navigationInfo() {
        navigationMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "This navigation bar is in Progress!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void accountInfo() {
        mainAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "This feature is in Progress!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void search() {
        searchButton.setOnClickListener(view -> {

            if (TextUtils.isEmpty(mainEditText.getText().toString())) {
                mainEditText.setError("Please enter the query");
            } else {
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
                    Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                    intent.putExtra("query", mainEditText.toString());
                    startActivity(intent);
                    Toast.makeText(MainActivity.this, "Searching...", Toast.LENGTH_SHORT).show();
//                    });
                }
                return false;
            }
        });
    }

    public static String getMainEditText() {
        return mainEditText.getText().toString();
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

        adapter = new ParentItemAdapter(getApplicationContext(), itemList);
        recyclerView.setAdapter(adapter);
    }

    private void init() {
        moviesModelArrayListTop = new ArrayList<>();
        moviesModelArrayListPop = new ArrayList<>();
        moviesModelArrayListTrendM = new ArrayList<>();
        moviesModelArrayListTrendTV = new ArrayList<>();
        moviesModelArrayListUpcoming = new ArrayList<>();
        moviesModelArrayListPosters = new ArrayList<>();


        sliderView = findViewById(R.id.slider);
        searchButton = findViewById(R.id.searchButtonMain);
        mainEditText = findViewById(R.id.search);
        progressBar = findViewById(R.id.progressBar);
        toolbar = findViewById(R.id.toolbar);
        mainAccount = findViewById(R.id.mainAccount);
        navigationMain = findViewById(R.id.navigationMain);

        recyclerView = findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);




//        trailerRecyclerView = findViewById(R.id.recyclerView2);
//        trailerLinearLayoutManager = new LinearLayoutManager(MainActivity.this);
//        trailerLinearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
//        trailerRecyclerView.setLayoutManager(trailerLinearLayoutManager);
//        latestTrailerAdapter = new LatestTrailerAdapter(getApplicationContext(), moviesModelArrayListUpcoming);
//        trailerRecyclerView.setAdapter(latestTrailerAdapter);

//        String met = mainEditText.getText().toString();


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
                    sliderAdapter = new SliderAdapter(MainActivity.this, imagesText);

                    sliderView.setSliderAdapter(sliderAdapter);

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

}

