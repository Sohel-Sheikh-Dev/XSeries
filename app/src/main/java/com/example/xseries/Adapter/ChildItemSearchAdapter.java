package com.example.xseries.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.xseries.Details.MovieDetails;
import com.example.xseries.Details.TVDetails;
import com.example.xseries.Model.MoviesModel;
import com.example.xseries.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ChildItemSearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    List<MoviesModel> moviesModelArrayList;
    public static int mids;
    public static int mposs;
    final int VIEW_TYPE_ONE = 1;
    final int VIEW_TYPE_TWO = 2;


    public ChildItemSearchAdapter(Context context, List<MoviesModel> moviesModelArrayList) {
        this.context = context;
        this.moviesModelArrayList = moviesModelArrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == VIEW_TYPE_ONE) {
            return new ChildItemSearchAdapter.ViewHolder1(LayoutInflater.from(context).inflate(R.layout.child_search_movie_item, parent, false));
        }
        return new ChildItemSearchAdapter.ViewHolder2(LayoutInflater.from(context).inflate(R.layout.child_search_tv_item, parent, false));

    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (moviesModelArrayList.get(position).getName() == null) {

            if (moviesModelArrayList.get(position).getRelease_date() != null) {
                @SuppressLint("SimpleDateFormat") String date_format = parseDate(moviesModelArrayList.get(position).getRelease_date(),
                        new java.text.SimpleDateFormat("yyyy-MM-dd"),
                        new SimpleDateFormat("MMM dd, yyyy"));
                ((ChildItemSearchAdapter.ViewHolder1) holder).tvDate.setText(date_format);

                ((ChildItemSearchAdapter.ViewHolder1) holder).tvTitle.setText(moviesModelArrayList.get(position).getTitle());
                ((ChildItemSearchAdapter.ViewHolder1) holder).tvDescription.setText(moviesModelArrayList.get(position).getOverview());
            }

            if (moviesModelArrayList.get(position).getRelease_date() == null) {
                ((ChildItemSearchAdapter.ViewHolder1) holder).tvDate.setText("");
                ((ViewHolder1) holder).tvOnlyTitleWhileNull.setText(moviesModelArrayList.get(position).getTitle());
                ((ChildItemSearchAdapter.ViewHolder1) holder).tvTitle.setText("");
                ((ChildItemSearchAdapter.ViewHolder1) holder).tvDescription.setText("");
            }

            float vote = moviesModelArrayList.get(position).getVote_average() * 10;
            int vote_final = Math.round(vote);

            Glide.with(context).load(moviesModelArrayList.get(position).getPoster_path()).into(((ChildItemSearchAdapter.ViewHolder1) holder).cardImage);
            ((ChildItemSearchAdapter.ViewHolder1) holder).cardview.setOnClickListener(view -> {
                Intent intent = new Intent(context, MovieDetails.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mposs = vote_final;
                mids = moviesModelArrayList.get(position).getMovieOrTV_id();
//                Toast.makeText(context.getApplicationContext(), "ID: " + mids, Toast.LENGTH_SHORT).show();
                ChildItemAdapter.which_movie_item = false;
                context.startActivity(intent);
            });


        }


        if (moviesModelArrayList.get(position).getTitle() == null) {

//            ((ViewHolder2) holder).tvTitleSEARCHACT.setText(moviesModelArrayList.get(position).getTitle());
//            ((ViewHolder2) holder).tvDescriptionSEARCHACT.setText(moviesModelArrayList.get(position).getOverview());


            if (moviesModelArrayList.get(position).getAir_date() != null) {
                @SuppressLint("SimpleDateFormat") String date_format = parseDate(moviesModelArrayList.get(position).getAir_date(),
                        new java.text.SimpleDateFormat("yyyy-MM-dd"),
                        new SimpleDateFormat("MMM dd, yyyy"));
                ((ViewHolder2) holder).tvDateSEARCHACT.setText(date_format);

                ((ViewHolder2) holder).tvTitleSEARCHACT.setText(moviesModelArrayList.get(position).getName());
                ((ViewHolder2) holder).tvDescriptionSEARCHACT.setText(moviesModelArrayList.get(position).getOverview());
            }

            if (moviesModelArrayList.get(position).getAir_date() == null) {
                ((ViewHolder2) holder).tvDateSEARCHACT.setText("");
                ((ViewHolder2) holder).tvOnlyTitleWhileNullSEARCHACT.setText(moviesModelArrayList.get(position).getTitle());
                ((ViewHolder2) holder).tvTitleSEARCHACT.setText("");
                ((ViewHolder2) holder).tvDescriptionSEARCHACT.setText("");
            }

            float vote = moviesModelArrayList.get(position).getVote_average() * 10;
            int vote_final = Math.round(vote);

            Glide.with(context).load(moviesModelArrayList.get(position).getPoster_path()).into(((ViewHolder2) holder).cardImageSEARCHACT);
            ((ViewHolder2) holder).cardViewSEARCHACT.setOnClickListener(view -> {

                Intent intent = new Intent(context, TVDetails.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mposs = vote_final;
                mids = moviesModelArrayList.get(position).getMovieOrTV_id();
//                Toast.makeText(context.getApplicationContext(), "ID: " + mids, Toast.LENGTH_SHORT).show();
                ChildItemAdapter.which_tv_item = false;
                context.startActivity(intent);
            });


        }


    }

    public static String parseDate(String inputDateString, java.text.SimpleDateFormat inputDateFormat, java.text.SimpleDateFormat outputDateFormat) {
        String outputDateString = null;
        try {
            Date date = inputDateFormat.parse(inputDateString);
            assert date != null;
            outputDateString = outputDateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return outputDateString;
    }

    @Override
    public int getItemCount() {
        return moviesModelArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (moviesModelArrayList.get(position).getName() == null) {
            return VIEW_TYPE_ONE;
        }
        return VIEW_TYPE_TWO;

    }

    private static class ViewHolder1 extends RecyclerView.ViewHolder {

        EditText mainEditText;
        Button searchButton;
        TextView tvTitle, tvDate, ratingPercentage, percentSign, tvDescription, tvOnlyTitleWhileNull;
        CardView cardview;
        ImageView cardImage;
        ProgressBar ratingProgress;


        ViewHolder1(final View itemView) {
            super(itemView);

            cardImage = itemView.findViewById(R.id.cardImage);
            ratingProgress = itemView.findViewById(R.id.ratingProgress);
            ratingPercentage = itemView.findViewById(R.id.ratingPercentage);
            percentSign = itemView.findViewById(R.id.percentSign);
            tvTitle = itemView.findViewById(R.id.title);
            tvDescription = itemView.findViewById(R.id.description);
            tvDate = itemView.findViewById(R.id.date);
            cardview = itemView.findViewById(R.id.SearchmovieOrTvCardViews);
            searchButton = itemView.findViewById(R.id.searchButton);
            mainEditText = itemView.findViewById(R.id.search);
            tvOnlyTitleWhileNull = itemView.findViewById(R.id.onlyTitleWhileNull);
        }
    }

    private static class ViewHolder2 extends RecyclerView.ViewHolder {

        TextView tvTitleSEARCHACT, tvDateSEARCHACT, ratingPercentageSEARCHACT, percentSignSEARCHACT, tvDescriptionSEARCHACT, tvOnlyTitleWhileNullSEARCHACT;
        CardView cardViewSEARCHACT;
        ImageView cardImageSEARCHACT;
//        ProgressBar ratingProgressSEARCHACT;

        public ViewHolder2(final View itemView) {
            super(itemView);

            tvTitleSEARCHACT = itemView.findViewById(R.id.TVtitleSEARCHACT);
            tvDateSEARCHACT = itemView.findViewById(R.id.TVdateSEARCHACT);
//            ratingPercentageSEARCHACT = itemView.findViewById(R.id.TVtitleSEARCHACT);
//            percentSignSEARCHACT = itemView.findViewById(R.id.TVtitleSEARCHACT);
            tvDescriptionSEARCHACT = itemView.findViewById(R.id.TVdescriptionSEARCHACT);
            tvOnlyTitleWhileNullSEARCHACT = itemView.findViewById(R.id.TVonlyTitleWhileNullSEARCHACT);
            cardViewSEARCHACT = itemView.findViewById(R.id.movieOrTvCardViewsSEARCHACT);
            cardImageSEARCHACT = itemView.findViewById(R.id.TVcardImageSEARCHACT);


        }
    }


}

