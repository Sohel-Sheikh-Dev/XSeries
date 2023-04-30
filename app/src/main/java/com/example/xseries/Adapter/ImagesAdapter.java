package com.example.xseries.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.xseries.Model.BackdropModel;
import com.example.xseries.Model.PosterModel;
import com.example.xseries.R;

import java.util.List;

public class ImagesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context context;
    List<PosterModel> imageResponseList;
    List<BackdropModel> backdropModelList;

    final int VIEW_TYPE_ONE = 1;
    final int VIEW_TYPE_TWO = 2;

    //false == backdrop

    public ImagesAdapter(Context context, List<PosterModel> imageResponseList, List<BackdropModel> backdropModelList) {
        this.context = context;
        this.imageResponseList = imageResponseList;
        this.backdropModelList = backdropModelList;
    }

//    public ImagesAdapter(Context context, List<PosterModel> imageResponseList) {
//        this.context = context;
//        this.imageResponseList = imageResponseList;
//    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        if (viewType == VIEW_TYPE_ONE) {
            return new ViewHolder1(LayoutInflater.from(context).inflate(R.layout.images_items, parent, false));
//        } else {
//            return new ViewHolder2(LayoutInflater.from(context).inflate(R.layout.images_poster_items, parent, false));
//        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
//        Log.d("taa", "onBindViewHolder: "+imageResponseList.get(position).getFilePath());
//        Glide.with(context).load(backdropModelList.get(position).getFilePath()).into(holder.imageView);
//        Log.d("taa", "onBindViewHolder: " + MovieDetails.selection);

//        if (MovieDetails.selection.equals(false)) {
            Glide.with(context).load(backdropModelList.get(position).getFilePath()).into(((ViewHolder1) holder).imageView);
//        }
//        if (MovieDetails.selection.equals(true)) {
//            Glide.with(context).load(imageResponseList.get(position).getFilePath()).into(((ViewHolder2) holder).imageView2);
//        }

    }


    @Override
    public int getItemCount() {
//        if (MovieDetails.selection.equals(false)) {
            return backdropModelList.size();
//        } else {
//            return imageResponseList.size();
//        }
    }
/*
    @Override
    public int getItemViewType(int position) {
        if (MovieDetails.selection.equals(false)) {
            return VIEW_TYPE_ONE;
        }
        return VIEW_TYPE_TWO;
    }
*/
    public static class ViewHolder1 extends RecyclerView.ViewHolder {

        ImageView imageView;

        public ViewHolder1(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgBackdrop);
        }
    }

    public static class ViewHolder2 extends RecyclerView.ViewHolder {

        ImageView imageView2;

        public ViewHolder2(@NonNull View itemView) {
            super(itemView);
            imageView2 = itemView.findViewById(R.id.imgPoster);
        }
    }
}
