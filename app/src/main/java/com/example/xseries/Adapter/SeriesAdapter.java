package com.example.xseries.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.xseries.Model.Series_Model;
import com.example.xseries.R;
import com.example.xseries.View.Video_Player;

import java.util.ArrayList;
import java.util.List;

public class SeriesAdapter extends RecyclerView.Adapter<SeriesAdapter.ParentViewHolder> {

    private final RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private static List<Series_Model> itemList = new ArrayList<>();
    static Context context;

    public SeriesAdapter(Context context, List<Series_Model> itemList) {
        SeriesAdapter.context = context;
        SeriesAdapter.itemList = itemList;
    }

    @NonNull
    @Override
    public ParentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.series_item, parent, false);
        return new ParentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ParentViewHolder holder, int position) {


        Log.d("SERIES", "onBindViewHolder: " + itemList.get(position).getId());

        Glide.with(context).load(itemList.get(position).getThumbnail()).into(holder.cardImage);
        holder.tvTitle.setText(itemList.get(position).getTitle());
        holder.durationTV.setText(itemList.get(position).getDuration());


    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    static class ParentViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout seriesItem;
        EditText mainEditText;
        Button searchButton;
        TextView tvTitle, tvDate, ratingPercentage, percentSign, durationTV;
        CardView cardview;
        ImageView cardImage;
        ProgressBar ratingProgress;

        ParentViewHolder(@NonNull View itemView) {
            super(itemView);

            cardImage = itemView.findViewById(R.id.cardImage);
            ratingProgress = itemView.findViewById(R.id.ratingProgress);
            ratingPercentage = itemView.findViewById(R.id.ratingPercentage);
            percentSign = itemView.findViewById(R.id.percentSign);
            tvTitle = itemView.findViewById(R.id.title);
            tvDate = itemView.findViewById(R.id.date);
            cardview = itemView.findViewById(R.id.movieOrTvCardView);
            searchButton = itemView.findViewById(R.id.searchButton);
            mainEditText = itemView.findViewById(R.id.search);
            durationTV = itemView.findViewById(R.id.durationTV);
            seriesItem = itemView.findViewById(R.id.seriesItem);

            seriesItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    Toast.makeText(context, "" + itemList.get(getBindingAdapterPosition()).getUrl(), Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(context, Video_Player.class);
                    intent.putExtra("url", itemList.get(getBindingAdapterPosition()).getUrl());
                    intent.putExtra("title", itemList.get(getBindingAdapterPosition()).getTitle());
                    context.startActivity(intent);

                }
            });


        }
    }
}
