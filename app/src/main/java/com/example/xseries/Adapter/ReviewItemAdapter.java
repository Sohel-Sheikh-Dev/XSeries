package com.example.xseries.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.xseries.Model.ReviewsModel;
import com.example.xseries.R;

import java.util.List;

public class ReviewItemAdapter extends RecyclerView.Adapter<ReviewItemAdapter.ViewHolder> {

    private Context context;
    private final List<ReviewsModel> reviewsModelList;

    public ReviewItemAdapter(Context context, List<ReviewsModel> reviewsModelList) {
        this.context = context;
        this.reviewsModelList = reviewsModelList;
    }

    @NonNull
    @Override
    public ReviewItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.reviews_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewItemAdapter.ViewHolder holder, int position) {
        holder.authorName.setText("A review by " + reviewsModelList.get(position).getAuthor());

        String ava = (reviewsModelList.get(position).getAuthorDetails().getAvatarPath());

        String uni = "https://cdn2.iconfinder.com/data/icons/gaming-and-beyond-part-2-1/80/User_gray-512.png";

        String otherAvatar = "https://www.themoviedb.org/t/p/w150_and_h150_face";

        if (ava != null) {
            String ava1 = ava.substring(1);
            Glide.with(context.getApplicationContext()).load(ava1).into(holder.avatar);
        }
        if(ava == null){
            Glide.with(context.getApplicationContext()).load(uni).into(holder.avatar);
        }
        if(ava != null && !(ava.startsWith("/http"))){
            Glide.with(context.getApplicationContext()).load(otherAvatar+ava).into(holder.avatar);
        }

        holder.detailedReviewResult.setText(reviewsModelList.get(position).getContent());

        holder.dropDownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.detailedReview.setVisibility(View.VISIBLE);
                holder.upButton.setVisibility(View.VISIBLE);
                holder.dropDownButton.setVisibility(View.GONE);
            }
        });

        holder.upButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.detailedReview.setVisibility(View.GONE);
                holder.upButton.setVisibility(View.GONE);
                holder.dropDownButton.setVisibility(View.VISIBLE);
            }
        });


    }

    @Override
    public int getItemCount() {
        return reviewsModelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView avatar;
        TextView authorName,detailedReviewResult;
        ScrollView detailedReview;
        Button dropDownButton, upButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            authorName = itemView.findViewById(R.id.authorName);
            avatar = itemView.findViewById(R.id.avatar);
            detailedReview = itemView.findViewById(R.id.detailedReview);
            dropDownButton = itemView.findViewById(R.id.dropdownButton);
            upButton = itemView.findViewById(R.id.UpButton);
            detailedReviewResult = itemView.findViewById(R.id.detailedReviewResult);

        }
    }
}
