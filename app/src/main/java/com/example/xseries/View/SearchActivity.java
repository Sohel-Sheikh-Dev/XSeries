package com.example.xseries.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xseries.Adapter.ChildItemSearchAdapter;
import com.example.xseries.Model.MoviesModel;
import com.example.xseries.R;
import com.example.xseries.Response.MoviesResponse;
import com.example.xseries.Retrofit.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    EditText editTextSearch;
    Button searchButton;
    RecyclerView searchRecyclerView;
    ProgressBar progressBar;
    List<MoviesModel> moviesModelArrayListSearch;
    ChildItemSearchAdapter searchViewAdapter;
    String queryText;
    ImageView accountSearch, navigationSearch;
    TextView noResultTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        init();
        searchMoviesTvShows(queryText);
        searchViewSearch();

        navigationSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SearchActivity.this, "This navigation bar is in Progress!!", Toast.LENGTH_SHORT).show();
            }
        });


        accountSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SearchActivity.this, "This feature is in Progress!!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @SuppressLint("NotifyDataSetChanged")
    public void searchViewSearch() {
        searchButton.setOnClickListener(view -> {
            String qur = editTextSearch.getText().toString();
            if (TextUtils.isEmpty(qur)) {
                editTextSearch.setError("Please enter the query");
            } else {
                moviesModelArrayListSearch.clear();
                searchViewAdapter.notifyDataSetChanged();
                searchMoviesTvShows(qur);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SearchActivity.this,MainActivity.class);
        startActivity(intent);
    }

    private void init() {
        editTextSearch = findViewById(R.id.searchEditText);
        String theQuery = MainActivity.getMainEditText();
        editTextSearch.setText(theQuery);
        queryText = MainActivity.getMainEditText();
        noResultTV = findViewById(R.id.noResultTV);
        navigationSearch = findViewById(R.id.navigationSearch);

        /*
        if (MainActivity.mainClickedOrDetailsClicked) {
            String theQuery = MainActivity.getMainEditText();
            editTextSearch.setText(theQuery);
        }
        if (MovieDetails.DetailsClicked) {
            String theDetailQuery = MovieDetails.getDetailsMainEditText();
            editTextSearch.setText(theDetailQuery);
        }

        if (MainActivity.TeleVmainClickedOrDetailsClicked) {
            String theQuery = MainActivity.getMainEditText();
            editTextSearch.setText(theQuery);
        }
        if (!MainActivity.TeleVmainClickedOrDetailsClicked) {
            String theTVDetailQuery = TVDetails.TeleVgetDetailsMainEditText();
            editTextSearch.setText(theTVDetailQuery);
        }
*/
//        Log.d("TAG", "init: " + MainActivity.mainClickedOrDetailsClicked);

        searchButton = findViewById(R.id.searchButton);
        searchRecyclerView = findViewById(R.id.recyclerViewSearch);
        progressBar = findViewById(R.id.progressBar);
//        if (MainActivity.mainClickedOrDetailsClicked) {
//            queryText = MainActivity.getMainEditText();
//        }
//        if (MovieDetails.DetailsClicked) {
//            queryText = MovieDetails.getDetailsMainEditText();
//        }

//        if (MainActivity.TeleVmainClickedOrDetailsClicked) {
//            queryText = MainActivity.getMainEditText();
//        }
//        if (!MainActivity.TeleVmainClickedOrDetailsClicked) {
//            queryText = TVDetails.TeleVgetDetailsMainEditText();
//        }

//        Log.d("TAG", "init: " + MainActivity.mainClickedOrDetailsClicked);

        moviesModelArrayListSearch = new ArrayList<>();
        searchViewAdapter = new ChildItemSearchAdapter(SearchActivity.this, moviesModelArrayListSearch);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SearchActivity.this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        searchRecyclerView.setLayoutManager(linearLayoutManager);
        searchRecyclerView.setAdapter(searchViewAdapter);
        accountSearch = findViewById(R.id.accountSearch);
    }

    private void searchMoviesTvShows(String query) {
        Call<MoviesResponse> data = RetrofitInstance.getRetrofitInstance().searchMoviesTvShows(query);
        data.enqueue(new Callback<MoviesResponse>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<MoviesResponse> call, @NonNull Response<MoviesResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    progressBar.setVisibility(View.GONE);
                    moviesModelArrayListSearch.addAll(response.body().getResults());
                    noResultTV.setVisibility(View.GONE);
                    searchViewAdapter.notifyDataSetChanged();
                }
                if(response.body().getResults().size() == 0){
                    noResultTV.setVisibility(View.VISIBLE);
//                    Toast.makeText(SearchActivity.this,"No result",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<MoviesResponse> call, @NonNull Throwable t) {

            }
        });
    }

}