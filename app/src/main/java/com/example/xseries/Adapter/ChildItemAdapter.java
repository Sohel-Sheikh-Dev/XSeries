package com.example.xseries.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
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
import androidx.core.content.ContextCompat;
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

public class ChildItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context context;
    List<MoviesModel> moviesModelArrayList;
    public static int mid;
    public static int mpos;
    final int VIEW_TYPE_ONE = 1;
    final int VIEW_TYPE_TWO = 2;

    public static boolean which_movie_item;
    public static boolean which_tv_item;


    public ChildItemAdapter(Context context, List<MoviesModel> moviesModelArrayList) {
        this.context = context;
        this.moviesModelArrayList = moviesModelArrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == VIEW_TYPE_ONE) {
            return new ViewHolder1(LayoutInflater.from(context).inflate(R.layout.new_item, parent, false));
        }
        return new ViewHolder2(LayoutInflater.from(context).inflate(R.layout.new_tv_item, parent, false));

    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        if (moviesModelArrayList.get(position).getName() == null) {

            ((ViewHolder1) holder).tvTitle.setText(moviesModelArrayList.get(position).getTitle());

//            Toast.makeText(context.getApplicationContext(), "" + moviesModelArrayList.get(0).getCredits().getCast().get(0).getCreditId(), Toast.LENGTH_SHORT).show();


            @SuppressLint("SimpleDateFormat") String date_format = parseDate(moviesModelArrayList.get(position).getRelease_date(),
                    new SimpleDateFormat("yyyy-MM-dd"),
                    new SimpleDateFormat("MMM dd, yyyy"));
            ((ViewHolder1) holder).tvDate.setText(date_format);


            float vote = moviesModelArrayList.get(position).getVote_average() * 10;
            int vote_final = Math.round(vote);

            Log.d("togg", "onResponse: " + position);


            String votePercentage = String.valueOf(vote_final);

            if (vote_final > 69) {

                ((ViewHolder1) holder).ratingPercentage.setText(votePercentage);
                Drawable drawable = ContextCompat.getDrawable(context, R.drawable.circle_green);
                ((ViewHolder1) holder).ratingProgress.setProgressDrawable(drawable);

                ((ViewHolder1) holder).ratingProgress.setProgress(vote_final);
            }

            if (vote_final < 70) {
                ((ViewHolder1) holder).ratingPercentage.setText(votePercentage);
                Drawable drawable = ContextCompat.getDrawable(context, R.drawable.circle_yellow);
                ((ViewHolder1) holder).ratingProgress.setProgressDrawable(drawable);

                ((ViewHolder1) holder).ratingProgress.setProgress(vote_final);

            }


            if (vote_final == 0 && votePercentage.equals("0")) {
                ((ViewHolder1) holder).ratingPercentage.setText("NR");
                ((ViewHolder1) holder).percentSign.setVisibility(View.GONE);
//                Drawable drawable = ContextCompat.getDrawable(context, R.drawable.circle_grey);
//                ((ViewHolder1) holder).ratingProgress.setProgressDrawable(drawable);

                ((ViewHolder1) holder).ratingProgress.getProgressDrawable().setColorFilter(Color.GRAY, android.graphics.PorterDuff.Mode.SRC_IN);

                int paddingDp = 1;
                float density = context.getResources().getDisplayMetrics().density;
                int paddingPixel = (int) (paddingDp * density);
                ((ViewHolder1) holder).ratingPercentage.setPadding(paddingPixel, 0, 0, 0);
                ((ViewHolder1) holder).ratingProgress.setProgress(0);
            }

            Glide.with(context).load(moviesModelArrayList.get(position).getPoster_path()).into(((ViewHolder1) holder).cardImage);
            ((ViewHolder1) holder).cardview.setOnClickListener(view -> {

                Intent intent = new Intent(context, MovieDetails.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mid = moviesModelArrayList.get(position).getMovieOrTV_id();
                mpos = vote_final;
//                Toast.makeText(context.getApplicationContext(), "ID: " + mid, Toast.LENGTH_SHORT).show();
                which_movie_item = true;
                context.startActivity(intent);
            });

        }


        if (moviesModelArrayList.get(position).getTitle() == null) {

            ((ViewHolder2) holder).TVtvTitle.setText(moviesModelArrayList.get(position).getName());

            @SuppressLint("SimpleDateFormat") String date_format = parseDate(moviesModelArrayList.get(position).getAir_date(),
                    new SimpleDateFormat("yyyy-MM-dd"),
                    new SimpleDateFormat("MMM dd, yyyy"));
            ((ViewHolder2) holder).TVtvDate.setText(date_format);

/*
            float vote = moviesModelArrayList.get(position).getVote_average() * 10;
            int vote_final = Math.round(vote);

            String votePercentage = String.valueOf(vote_final);

            if (vote_final > 69) {

                ((ViewHolder2) holder).TVratingPercentage.setText(votePercentage);
                Drawable drawable = ContextCompat.getDrawable(context, R.drawable.circle_green);
                ((ViewHolder2) holder).TVratingProgress.setProgressDrawable(drawable);

                ((ViewHolder2) holder).TVratingProgress.setProgress(vote_final);
            }

            if (vote_final < 70) {
                ((ViewHolder2) holder).TVratingPercentage.setText(votePercentage);
                Drawable drawable = ContextCompat.getDrawable(context, R.drawable.circle_yellow);
                ((ViewHolder2) holder).TVratingProgress.setProgressDrawable(drawable);

                ((ViewHolder2) holder).TVratingProgress.setProgress(vote_final);

            }


            if (vote_final == 0 && votePercentage.equals("0")) {
                ((ViewHolder2) holder).TVratingPercentage.setText("NR");
                ((ViewHolder2) holder).TVpercentSign.setVisibility(View.GONE);
//                Drawable drawable = ContextCompat.getDrawable(context, R.drawable.circle_grey);
//                ((ViewHolder1) holder).ratingProgress.setProgressDrawable(drawable);

                ((ViewHolder2) holder).TVratingProgress.getProgressDrawable().setColorFilter(Color.GRAY, android.graphics.PorterDuff.Mode.SRC_IN);

                int paddingDp = 1;
                float density = context.getResources().getDisplayMetrics().density;
                int paddingPixel = (int) (paddingDp * density);
                ((ViewHolder2) holder).TVratingPercentage.setPadding(paddingPixel, 0, 0, 0);
                ((ViewHolder2) holder).TVratingProgress.setProgress(0);
            }

            Glide.with(context).load(moviesModelArrayList.get(position).getPoster_path()).into(((ViewHolder2) holder).TVcardImage);
            ((ViewHolder2) holder).TVcardview.setOnClickListener(view -> {

                Intent intent = new Intent(context, TVDetails.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mid = moviesModelArrayList.get(position).getMovieOrTV_id();
                mpos = vote_final;
//                Toast.makeText(context.getApplicationContext(), "ID: " + mid, Toast.LENGTH_SHORT).show();
                which_tv_item = true;
                context.startActivity(intent);
            });
*/
        }


    }


    public static String parseDate(String inputDateString, SimpleDateFormat inputDateFormat, SimpleDateFormat outputDateFormat) {
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
        TextView tvTitle, tvDate, ratingPercentage, percentSign;
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
            tvDate = itemView.findViewById(R.id.date);
            cardview = itemView.findViewById(R.id.movieOrTvCardView);
            searchButton = itemView.findViewById(R.id.searchButton);
            mainEditText = itemView.findViewById(R.id.search);
        }
    }

    private static class ViewHolder2 extends RecyclerView.ViewHolder {

        TextView TVtvTitle, TVtvDate, TVratingPercentage, TVpercentSign;
        CardView TVcardview;
        ImageView TVcardImage;
        ProgressBar TVratingProgress;

        public ViewHolder2(final View itemView) {
            super(itemView);

            TVcardImage = itemView.findViewById(R.id.TVcardImage);
            TVratingProgress = itemView.findViewById(R.id.TVratingProgress);
            TVratingPercentage = itemView.findViewById(R.id.TvRatingPercentage);
            TVpercentSign = itemView.findViewById(R.id.TVpercentSign);
            TVtvTitle = itemView.findViewById(R.id.TVtitle);
            TVtvDate = itemView.findViewById(R.id.TVdate);
            TVcardview = itemView.findViewById(R.id.TvCardView);
        }
    }


}
