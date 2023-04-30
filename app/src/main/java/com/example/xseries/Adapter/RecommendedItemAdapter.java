package com.example.xseries.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.xseries.Details.MovieDetails;
import com.example.xseries.Details.TVDetails;
import com.example.xseries.Model.MoviesModel;
import com.example.xseries.R;

import java.util.List;

public class RecommendedItemAdapter extends RecyclerView.Adapter<RecommendedItemAdapter.ViewHolder> {

    private Context context;
    private List<MoviesModel> castMovieModelList;

    public RecommendedItemAdapter(Context context, List<MoviesModel> castMovieModelList) {
        this.context = context;
        this.castMovieModelList = castMovieModelList;
    }

    @NonNull
    @Override
    public RecommendedItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recommended_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendedItemAdapter.ViewHolder holder, int position) {

        Glide.with(context.getApplicationContext()).load(castMovieModelList.get(position).getBackdrop_path()).into(holder.recomImage);



        float vote = castMovieModelList.get(position).getVote_average() * 10;
        int vote_final = Math.round(vote);

        String votePercentage = String.valueOf(vote_final);

        holder.recomPercent.setText(votePercentage+"%");

        if (castMovieModelList.get(position).getName() == null) {
            holder.recomName.setText(castMovieModelList.get(position).getTitle());
            holder.castImageCard.setOnClickListener(view -> {

                Intent intent = new Intent(context, MovieDetails.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ChildItemAdapter.mpos = vote_final;
                ChildItemAdapter.mid = castMovieModelList.get(position).getMovieOrTV_id();
                ChildItemSearchAdapter.mids = castMovieModelList.get(position).getMovieOrTV_id();
//                Log.d("myid", "movie id : "+ChildItemAdapter.mid);
                context.startActivity(intent);
            });

        }

        if (castMovieModelList.get(position).getTitle() == null) {
            holder.recomName.setText(castMovieModelList.get(position).getName());
            holder.castImageCard.setOnClickListener(view -> {

                Intent intent = new Intent(context, TVDetails.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ChildItemAdapter.mpos = vote_final;
                ChildItemAdapter.mid = castMovieModelList.get(position).getMovieOrTV_id();
                ChildItemSearchAdapter.mids = castMovieModelList.get(position).getMovieOrTV_id();
                Log.d("myid", "tv id : "+ChildItemAdapter.mid);
                context.startActivity(intent);
            });

        }

//        Toast.makeText(context.getApplicationContext(), "" + castMovieModelList.get(position).getTitle(), Toast.LENGTH_SHORT).show();




    }

    @Override
    public int getItemCount() {
        return castMovieModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView castImageCard;
        ImageView recomImage;
        TextView recomName,recomPercent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            castImageCard = itemView.findViewById(R.id.castImageCard);
            recomImage = itemView.findViewById(R.id.recomImage);
            recomName = itemView.findViewById(R.id.recomName);
            recomPercent = itemView.findViewById(R.id.recomPercent);
        }
    }
}
