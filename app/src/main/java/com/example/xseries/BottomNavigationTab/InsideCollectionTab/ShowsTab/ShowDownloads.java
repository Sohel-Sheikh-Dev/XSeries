package com.example.xseries.BottomNavigationTab.InsideCollectionTab.ShowsTab;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xseries.Adapter.SeriesAdapter;
import com.example.xseries.Model.Series_Model;
import com.example.xseries.R;

import java.io.File;
import java.net.URI;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ShowDownloads extends Fragment {

    List<Series_Model> seriesList;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    LinearLayoutManager linearLayoutManager;
    SeriesAdapter seriesAdapter;


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_my__shows, container, false);

        int result = ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE);


        seriesList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.rv_downloads);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.suppressLayout(true);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        seriesAdapter = new SeriesAdapter(getActivity(), seriesList);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            getFiles();
        } else {
            if (result != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(), "Permission not granted", Toast.LENGTH_SHORT).show();
            } else {
                getFiles();

            }
        }





/*
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.R) {
        } else {

        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && !Environment.isExternalStorageManager()) {
            // Access to all files


        } else {
            getFiles();

        }
*/

//        File yourFile = new File(yourFilePath);


        return view;
    }

    public void getFiles() {

        String path = Environment.getExternalStorageDirectory().toString() + "/Download/XSeries";
        File directory = new File(path);
        File[] files = directory.listFiles();
//        Log.d("Files", "Size: " + files[0].getPath());

        if (directory.exists()) {
            seriesList.clear();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                for (int i = 0; i < files.length; i++) {

                    String filePath = files[i].getPath();
                    URI fileUri = Paths.get(filePath).toUri();
//                    String filename = path.substring(path.lastIndexOf("/") + 1);

                    Uri uri = Uri.parse(filePath);
                    String token = uri.getLastPathSegment();


                    Log.d("Files", "File Path:" + filePath);
                    Log.d("Files", "File URL: " + fileUri);
                    Log.d("Files", "File name: " + token);

                    MediaMetadataRetriever retriever = new MediaMetadataRetriever();
                    retriever.setDataSource(getActivity(), Uri.fromFile(files[i]));
                    String time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
                    long timeInMillisec = Long.parseLong(time);
                    Log.d("Files", "File duration: " + timeInMillisec);

                    long minutes = (timeInMillisec / 1000) / 60;
                    long seconds = (timeInMillisec / 1000) % 60;

                    String mins = minutes + ":" + seconds;

                    Series_Model series_model = new Series_Model("", fileUri.toString(), fileUri.toString(), mins, token);
                    Log.d("SERIES", "onDataChange: " + series_model.getId());
                    seriesList.add(series_model);


                }
                recyclerView.setAdapter(seriesAdapter);
                seriesAdapter.notifyDataSetChanged();
            }


        } else {
            Toast.makeText(getActivity(), "No downloads", Toast.LENGTH_SHORT).show();
        }
    }
}