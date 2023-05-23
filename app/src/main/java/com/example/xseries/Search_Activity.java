package com.example.xseries;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xseries.Adapter.SeriesAdapter;
import com.example.xseries.Model.Series_Model;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Search_Activity extends AppCompatActivity {

    List<Series_Model> seriesList;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    LinearLayoutManager linearLayoutManager;
    SeriesAdapter seriesAdapter;

    EditText query;
    Button searchBtn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search2);


        seriesList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerViewResults);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        seriesAdapter = new SeriesAdapter(getApplicationContext(), seriesList);
        query = findViewById(R.id.searchEditText);
        searchBtn = findViewById(R.id.searchButton);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!query.getText().toString().isEmpty()){
                    filterVids(query.getText().toString());
                    seriesAdapter.filtterList(seriesList);
                }else{
                    Toast.makeText(Search_Activity.this, "Please enter your query", Toast.LENGTH_SHORT).show();
                }

            }
        });






    }


    public void filterVids(String text){
        DatabaseReference fetchLogin = FirebaseDatabase.getInstance().getReference().child("Videos");
        seriesList.clear();
        fetchLogin.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Series_Model series_model = dataSnapshot.getValue(Series_Model.class);
                    Log.d("SERIES", "onDataChange: " + series_model.getId());
//                    seriesList.add(series_model);


                    if (series_model.getTitle().toLowerCase().contains(text.toLowerCase())) {
                        seriesList.add(series_model);


                    }
                    seriesAdapter.filtterList(seriesList);


                }
//                Collections.shuffle(seriesList);
                recyclerView.setAdapter(seriesAdapter);
                seriesAdapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}