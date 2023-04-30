package com.example.xseries.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.xseries.Model.CastModel;
import com.example.xseries.R;

import java.util.List;

public class CastItemAdapter extends RecyclerView.Adapter<CastItemAdapter.ViewHolder> {

    private Context context;
    private final List<CastModel> castMovieModelList;

    public CastItemAdapter(Context context, List<CastModel> castMovieModelList) {
        this.context = context;
        this.castMovieModelList = castMovieModelList;
    }

    @NonNull
    @Override
    public CastItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cast_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CastItemAdapter.ViewHolder holder, int position) {

        Glide.with(context.getApplicationContext()).load(castMovieModelList.get(position).getProfilePath()).into(holder.castImage);
        holder.castName.setText(castMovieModelList.get(position).getOriginalName());
        holder.realName.setText(castMovieModelList.get(position).getCharacter());

    }

    @Override
    public int getItemCount() {
        return castMovieModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView castCardView;
        ImageView castImage;
        TextView castName,realName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            castCardView = itemView.findViewById(R.id.castCardView);
            castImage = itemView.findViewById(R.id.castImage);
            castName = itemView.findViewById(R.id.castName);
            realName = itemView.findViewById(R.id.realName);

        }
    }
}
