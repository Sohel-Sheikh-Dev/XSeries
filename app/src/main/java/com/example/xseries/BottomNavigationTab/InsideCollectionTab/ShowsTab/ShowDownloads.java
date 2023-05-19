package com.example.xseries.BottomNavigationTab.InsideCollectionTab.ShowsTab;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.xseries.BuildConfig;
import com.example.xseries.R;

import java.io.File;
import java.io.IOException;

public class ShowDownloads extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_my__shows, container, false);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && !Environment.isExternalStorageManager()) {
            // Access to all files
            Uri uri = Uri.parse("package:" + BuildConfig.APPLICATION_ID);
            Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION, uri);
            startActivity(intent);

        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                try {
                    getFiles();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        }


//        File yourFile = new File(yourFilePath);


        return view;
    }


    @RequiresApi(api = Build.VERSION_CODES.R)
    public void getFiles() throws IOException {

        String path = Environment.getExternalStorageDirectory().toString() + "/Download/XSeries";
        File directory = new File(path);
        File[] files = directory.listFiles();
        Log.d("Files", "Size: " + path);
        for (int i = 0; i < files.length; i++) {
            Log.d("Files", "FileName:" + files[i].getPath());
        }
    }
}