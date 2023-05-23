package com.example.xseries.View;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xseries.Adapter.SeriesAdapter;
import com.example.xseries.Model.Series_Model;
import com.example.xseries.R;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Video_Player extends AppCompatActivity {

    ImageView fullscreenButton;
    PlayerView playerView;
    String url, title;

    TextView titleTv;
    Button downloadBtn, shareBtn;
    ExoPlayer player;


    List<Series_Model> seriesList;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    LinearLayoutManager linearLayoutManager;
    SeriesAdapter seriesAdapter;
    RelativeLayout btmPlayerView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        titleTv = findViewById(R.id.seriesTitle);
        downloadBtn = findViewById(R.id.download);
        shareBtn = findViewById(R.id.shareBtn);
        btmPlayerView = findViewById(R.id.bottomPlayerView);

        seriesList = new ArrayList<>();
        recyclerView = findViewById(R.id.suggestedRV);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.suppressLayout(true);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        seriesAdapter = new SeriesAdapter(getApplicationContext(), seriesList);

        if (getIntent().hasExtra("url")) {


            url = getIntent().getStringExtra("url");
            title = getIntent().getStringExtra("title");

        }


        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);

                // type of the content to be shared
                sharingIntent.setType("text/plain");

                // Body of the content
                String shareBody = url;

                // subject of the content. you can share anything
                String shareSubject = title;

                // passing body of the content
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);

                // passing subject of the content
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSubject);
                startActivity(Intent.createChooser(sharingIntent, "Share using"));

            }
        });


        DatabaseReference fetchLogin = FirebaseDatabase.getInstance().getReference().child("Videos");
        seriesList.clear();
        fetchLogin.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Series_Model series_model = dataSnapshot.getValue(Series_Model.class);
                    Log.d("SERIES", "onDataChange: " + series_model.getId());
                    seriesList.add(series_model);
                }
                Collections.shuffle(seriesList);
                recyclerView.setAdapter(seriesAdapter);
                seriesAdapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Log.d("URLRESPONSE", "onCreate: " + getIntent().getStringExtra("url"));

        titleTv.setText(title);

        playerView = findViewById(R.id.playerView);
        String folderPath = Environment.getExternalStorageDirectory() + "/XSeries";
        File folder = new File(folderPath);
        play(url);

        downloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    try {
                        String fileName = Paths.get(new URI(url).getPath()).getFileName().toString();
                        downloadFile(url, fileName);

                    } catch (URISyntaxException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });


    }

    private void downloadFile(String url, String outputFile) {
        DownloadManager downloadmanager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);

        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setTitle(outputFile);
        request.setDescription("Downloading...");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setVisibleInDownloadsUi(false);
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath() + "/XSeries/" + outputFile);
        request.setDestinationUri(Uri.fromFile(file));

        downloadmanager.enqueue(request);
    }

    private void play(String url) {

        final boolean[] fullscreen = {false};

        player = new ExoPlayer.Builder(this).build();
        fullscreenButton = playerView.findViewById(R.id.exo_fullscreen_icon);

        fullscreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (fullscreen[0]) {
                    fullscreenButton.setImageDrawable(ContextCompat.getDrawable(Video_Player.this, R.drawable.ic_fullscreen_open));


                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);

                    if (getSupportActionBar() != null) {
                        getSupportActionBar().show();
                    }

                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) playerView.getLayoutParams();
                    params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    params.height = (int) (200 * getApplicationContext().getResources().getDisplayMetrics().density);
                    playerView.setLayoutParams(params);

                    fullscreen[0] = false;
                } else {
                    fullscreenButton.setImageDrawable(ContextCompat.getDrawable(Video_Player.this, R.drawable.ic_fullscreen_close));


                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

                    if (getSupportActionBar() != null) {
                        getSupportActionBar().hide();
                    }

                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) playerView.getLayoutParams();
                    params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    params.height = ViewGroup.LayoutParams.MATCH_PARENT;
                    playerView.setLayoutParams(params);

                    fullscreen[0] = true;
                }
            }
        });

        playerView.setPlayer(player);
        playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIXED_HEIGHT);

        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getApplicationContext(), Util.getUserAgent(getApplicationContext(), getApplicationContext().getString(R.string.app_name)));

        MediaSource videoSource = new ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(MediaItem.fromUri(Uri.parse(url)));

        player.prepare(videoSource);
        player.setPlayWhenReady(true);


        Uri videouri = Uri.parse(url);
        MediaItem mediaItem = MediaItem.fromUri(videouri);
        player.setMediaItem(mediaItem);
        player.prepare();
        player.play();

    }

    @Override
    protected void onPause() {
        super.onPause();
        player.pause();

    }

    @Override
    protected void onResume() {
        super.onResume();
        player.play();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Video_Player.this, MainActivity.class);
        startActivity(intent);
    }
}