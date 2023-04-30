package com.example.xseries.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.xseries.Model.MovieTVTrailerModel;
import com.example.xseries.R;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.List;

public class MediaAdapter extends RecyclerView.Adapter<MediaAdapter.ViewHolder> {

    private final Context context;
    List<MovieTVTrailerModel> movieTVTrailerModelList;
    public static String videoKey;

    public MediaAdapter(Context context, List<MovieTVTrailerModel> movieTVTrailerModelList) {
        this.context = context;
        this.movieTVTrailerModelList = movieTVTrailerModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.media_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        videoKey = movieTVTrailerModelList.get(position).getKey();

        String thumbnail = "https://i3.ytimg.com/vi/"+videoKey+"/maxresdefault.jpg";

        Glide.with(context.getApplicationContext()).load(thumbnail).into(holder.thumbnailImageView);

        AppCompatActivity ac = new AppCompatActivity();
        ac.getLifecycle().addObserver(holder.youTubePlayerView);
        holder.youTubePlayerView.getPlayerUiController();

        holder.thumbnailImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.thumbnailImageView.setVisibility(View.GONE);

                holder.youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                    @Override
                    public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                        youTubePlayer.loadVideo(videoKey, 0);
                    }
                });

            }
        });


//        holder.thumbnailImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
////                AppCompatActivity ac = new AppCompatActivity();
////                ac.getLifecycle().addObserver(holder.youTubePlayerView);
//                holder.youTubePlayerView.getPlayerUiController();
//                holder.thumbnailImageView.setVisibility(View.GONE);
//
//            }
//        });


    }


    @Override
    public int getItemCount() {
        return movieTVTrailerModelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView thumbnailImageView;
        YouTubePlayerView youTubePlayerView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            youTubePlayerView = itemView.findViewById(R.id.youtube_player_view);
            thumbnailImageView = itemView.findViewById(R.id.thumbnailImageView);
        }
    }
}
