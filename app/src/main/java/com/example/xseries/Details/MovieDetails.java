package com.example.xseries.Details;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.xseries.Adapter.CastItemAdapter;
import com.example.xseries.Adapter.ChildItemAdapter;
import com.example.xseries.Adapter.ChildItemSearchAdapter;
import com.example.xseries.Adapter.ImagesAdapter;
import com.example.xseries.Adapter.MediaAdapter;
import com.example.xseries.Adapter.RecommendedItemAdapter;
import com.example.xseries.Adapter.ReviewItemAdapter;
import com.example.xseries.Model.BackdropModel;
import com.example.xseries.Model.CastModel;
import com.example.xseries.Model.MovieTVTrailerModel;
import com.example.xseries.Model.MoviesModel;
import com.example.xseries.Model.PosterModel;
import com.example.xseries.Model.ReviewsModel;
import com.example.xseries.R;
import com.example.xseries.Response.ImageResponse;
import com.example.xseries.Response.MoviesResponse;
import com.example.xseries.Response.ReviewsResponse;
import com.example.xseries.Response.TrailerResponse;
import com.example.xseries.Retrofit.RetrofitInstance;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetails extends AppCompatActivity {

    List<CastModel> castModelList;
    List<MoviesModel> recommendedMoviesList;
    List<ReviewsModel> reviewsModelList;
    List<MovieTVTrailerModel> movieTVTrailerModelList;
    List<PosterModel> imageResponseList;
    List<BackdropModel> backdropModelList;

    RecyclerView mediaRecyclerView, recomRecyclerView, reviewsRecyclerView, VideosRecyclerView, ImagesRecyclerView;

    LinearLayoutManager linearLayoutManagerRecom, linearLayoutManagerCast, linearLayoutManagerReviews, linearLayoutManagerMedia, linearLayoutManagerImg;

    private CastItemAdapter castItemAdapter;
    private RecommendedItemAdapter recommendedItemAdapter;
    private ReviewItemAdapter reviewItemAdapter;
    private MediaAdapter latestTrailerAdapter;
    private ImagesAdapter imagesAdapter;

    ImageView detailsSearchButton, detailsCloseButton, detailsIV, cardImage, playIV, navigationMovieDetails,accountMovieDetails;
    static EditText detailsSearchET;
    TextView titleTv, dateTv, fullDateTv, tvReleaseRuntime, taglineTv, overviewListTv, statusResultTv, originalLangResultTv, budgetResultTv, revenueResultTv, genreTv, noReview, noOverview;
    TextView videoTv, postersTv, backdropTv;
    Button detailsSearchTextButton;

    ProgressBar detailsRatingProgress;
    TextView detailsRatingPercentage, detailsPercentageSign;

    boolean selection = false;

    String vkey;
    int i;
    int vote_final;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        if (ChildItemAdapter.which_movie_item) {
            i = ChildItemAdapter.mid;
//            Log.d("myid", "movie id : " + i);
        }
        if (!ChildItemAdapter.which_movie_item) {
            i = ChildItemSearchAdapter.mids;
//            Log.d("Item log", "onCreate: " + i);
        }

        init();
        getMovieDetails(i);
        getMovieTrailer(i);
        getMovieReviews(i);
        getRecomMovies(i);
        getPopMovies1(i);
        getMovieImages(i);



            navigationMovieDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MovieDetails.this, "This navigation bar is in Progress!!", Toast.LENGTH_SHORT).show();
                }
            });


            accountMovieDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MovieDetails.this, "This feature is in Progress!!", Toast.LENGTH_SHORT).show();
                }
            });




        postersTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MovieDetails.this, "The posters are coming very soon!!",Toast.LENGTH_SHORT).show();
            }
        });


        videoTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selection = true;
                Log.d("TAG", "init: " + selection);
                ImagesRecyclerView.setVisibility(View.GONE);
                VideosRecyclerView.setVisibility(View.VISIBLE);
            }
        });

        backdropTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selection = false;
                Log.d("TAG", "init: " + selection);
                VideosRecyclerView.setVisibility(View.GONE);
                ImagesRecyclerView.setVisibility(View.VISIBLE);
            }
        });


        detailsSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MovieDetails.this, "This searching feature is in Progress!!", Toast.LENGTH_SHORT).show();
            }
        });

/*
        detailsSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailsSearchButton.setVisibility(View.GONE);
                detailsCloseButton.setVisibility(View.VISIBLE);
                detailsSearchET.setVisibility(View.VISIBLE);
                detailsSearchTextButton.setVisibility(View.VISIBLE);

                detailsSearchTextButton.setOnClickListener(view -> {

                    if (TextUtils.isEmpty(detailsSearchET.getText().toString())) {
                        detailsSearchET.setError("Please enter the query");
                    } else {
                        DetailsClicked = true;
                        Intent intent = new Intent(view.getContext(), SearchActivity.class);
                        intent.putExtra("query", detailsSearchET.toString());
                        startActivity(intent);
                        Toast.makeText(view.getContext(), "" + detailsSearchET.getText().toString(), Toast.LENGTH_SHORT).show();
                    }

                });

            }
        });

        detailsCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailsSearchButton.setVisibility(View.VISIBLE);
                detailsCloseButton.setVisibility(View.GONE);
                detailsSearchET.setVisibility(View.GONE);
                detailsSearchTextButton.setVisibility(View.GONE);

            }
        });
*/
    }

    public static String getDetailsMainEditText() {
        return detailsSearchET.getText().toString();
    }

    public void init() {
        detailsSearchButton = findViewById(R.id.detailsSearchButton);
        detailsCloseButton = findViewById(R.id.detailsCloseButton);
        detailsSearchET = findViewById(R.id.detailsSearchEditText);
        detailsIV = findViewById(R.id.detailsIV);

        titleTv = findViewById(R.id.titleTV);
        dateTv = findViewById(R.id.dateTV);
        fullDateTv = findViewById(R.id.fullDateTV);
        tvReleaseRuntime = findViewById(R.id.fullTimeTV);
        taglineTv = findViewById(R.id.taglineTV);
        overviewListTv = findViewById(R.id.overviewListTV);
        statusResultTv = findViewById(R.id.statusResultTV);
        originalLangResultTv = findViewById(R.id.originalLangResultTV);
        budgetResultTv = findViewById(R.id.budgetResultTV);
        revenueResultTv = findViewById(R.id.revenueResultTV);
        cardImage = findViewById(R.id.cardImage);
        detailsRatingProgress = findViewById(R.id.detailsRatingProgress);
        detailsRatingPercentage = findViewById(R.id.detailsRatingPercentage);
        detailsPercentageSign = findViewById(R.id.detailsPercentSign);
        genreTv = findViewById(R.id.genreTV);
        videoTv = findViewById(R.id.videosTV);
        postersTv = findViewById(R.id.postersTV);
        backdropTv = findViewById(R.id.backdropsTV);
        playIV = findViewById(R.id.playImageView);
        detailsSearchTextButton = findViewById(R.id.detailsSearchTextButton);
        noReview = findViewById(R.id.noReview);
        noOverview = findViewById(R.id.noOverview);
        navigationMovieDetails = findViewById(R.id.navigationMovieDetails);
        accountMovieDetails = findViewById(R.id.accountMovieDetails);

        castModelList = new ArrayList<>();
        recommendedMoviesList = new ArrayList<>();
        reviewsModelList = new ArrayList<>();
        movieTVTrailerModelList = new ArrayList<>();
        imageResponseList = new ArrayList<>();
        backdropModelList = new ArrayList<>();

        mediaRecyclerView = findViewById(R.id.topCastRV);
        recomRecyclerView = findViewById(R.id.recommendationsRV);
        reviewsRecyclerView = findViewById(R.id.reviewsRV);
        VideosRecyclerView = findViewById(R.id.mediaVidRV);
        ImagesRecyclerView = findViewById(R.id.mediaImgRV);

        linearLayoutManagerRecom = new LinearLayoutManager(MovieDetails.this);
        linearLayoutManagerRecom.setOrientation(RecyclerView.HORIZONTAL);

        linearLayoutManagerCast = new LinearLayoutManager(MovieDetails.this);
        linearLayoutManagerCast.setOrientation(RecyclerView.HORIZONTAL);

        linearLayoutManagerReviews = new LinearLayoutManager(MovieDetails.this);
        linearLayoutManagerReviews.setOrientation(RecyclerView.VERTICAL);

        linearLayoutManagerMedia = new LinearLayoutManager(MovieDetails.this);
        linearLayoutManagerMedia.setOrientation(RecyclerView.HORIZONTAL);

        linearLayoutManagerImg = new LinearLayoutManager(MovieDetails.this);
        linearLayoutManagerImg.setOrientation(RecyclerView.HORIZONTAL);

        mediaRecyclerView.setLayoutManager(linearLayoutManagerCast);
        recomRecyclerView.setLayoutManager(linearLayoutManagerRecom);
        reviewsRecyclerView.setLayoutManager(linearLayoutManagerReviews);
        VideosRecyclerView.setLayoutManager(linearLayoutManagerMedia);
        ImagesRecyclerView.setLayoutManager(linearLayoutManagerImg);

        recommendedItemAdapter = new RecommendedItemAdapter(getApplicationContext(), recommendedMoviesList);
        recomRecyclerView.setAdapter(recommendedItemAdapter);

        castItemAdapter = new CastItemAdapter(getApplicationContext(), castModelList);
        mediaRecyclerView.setAdapter(castItemAdapter);

        reviewItemAdapter = new ReviewItemAdapter(getApplicationContext(), reviewsModelList);
        reviewsRecyclerView.setAdapter(reviewItemAdapter);

        latestTrailerAdapter = new MediaAdapter(getApplicationContext(), movieTVTrailerModelList);
        VideosRecyclerView.setAdapter(latestTrailerAdapter);

        imagesAdapter = new ImagesAdapter(getApplicationContext(), imageResponseList, backdropModelList);
        ImagesRecyclerView.setAdapter(imagesAdapter);


    }

    private void getMovieImages(int id) {
        Call<ImageResponse> data = RetrofitInstance.getRetrofitInstance().getMoviesImages(id);
        data.enqueue(new Callback<ImageResponse>() {
            @Override
            public void onResponse(Call<ImageResponse> call, Response<ImageResponse> response) {
                if (response.isSuccessful() && response.body() != null) {

                    TextView noMedia = findViewById(R.id.noMedia);

                    if (response.body().getPosters().size() == 0 || response.body().getBackdrops().size() == 0) {
                        noMedia.setVisibility(View.VISIBLE);
                    }

                    imageResponseList.addAll(response.body().getPosters());
                    backdropModelList.addAll(response.body().getBackdrops());
                    imagesAdapter.notifyDataSetChanged();
//                    Log.d("dhally", "onResponse: " + response.body().getPosters());
                }
            }

            @Override
            public void onFailure(Call<ImageResponse> call, Throwable t) {

            }
        });
    }

    private void getMovieTrailer(int id) {

        Call<TrailerResponse> data = RetrofitInstance.getRetrofitInstance().getMoviesTrailer(id);
        data.enqueue(new Callback<TrailerResponse>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<TrailerResponse> call, @NonNull Response<TrailerResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getMovieTVTrailerModelList().size() != 0) {

                    TextView noMedia = findViewById(R.id.noMedia);

                    if (response.body().getMovieTVTrailerModelList().size() == 0) {
                        noMedia.setVisibility(View.VISIBLE);
                    }

                    vkey = response.body().getMovieTVTrailerModelList().get(0).getKey();
                    movieTVTrailerModelList.addAll(response.body().getMovieTVTrailerModelList());
//                    latestTrailerAdapter.notifyDataSetChanged();
                } else {
                    vkey = null;
                }

            }

            @Override
            public void onFailure(@NonNull Call<TrailerResponse> call, @NonNull Throwable t) {

            }
        });

    }

    public void getMovieDetails(int id) {

        Call<MoviesModel> data = RetrofitInstance.getRetrofitInstance().getMovieDetails(id);
        data.enqueue(new Callback<MoviesModel>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @SuppressLint({"DefaultLocale", "SetTextI18n"})
            @Override
            public void onResponse(@NonNull Call<MoviesModel> call, @NonNull Response<MoviesModel> response) {
                if (response.isSuccessful() && response.body() != null) {

                    playIV.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (vkey != null) {

                                AlertDialog.Builder builder = new AlertDialog.Builder(MovieDetails.this);
                                ViewGroup viewGroup = findViewById(android.R.id.content);
                                View dialogView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.new_media_items, viewGroup, false);
                                YouTubePlayerView youTubePlayerView = dialogView.findViewById(R.id.new_youtube_player_view);
                                youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                                    @Override
                                    public void onReady(YouTubePlayer youTubePlayer) {
                                        youTubePlayer.loadVideo(vkey, 0);
                                    }
                                });

                                builder.setView(dialogView);
                                AlertDialog alertDialog = builder.create();
                                alertDialog.show();
                            } else {
                                Toast.makeText(MovieDetails.this, "It's trailer is not available yet!!", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                    Glide.with(MovieDetails.this).load(response.body().getBackdrop_path()).into(detailsIV);
                    titleTv.setText(response.body().getTitle());
                    String a = response.body().getRelease_date();
                    String[] strings = a.split("-");
                    dateTv.setText("(" + strings[0] + ")");

                    @SuppressLint("SimpleDateFormat") String date_format = ChildItemAdapter.parseDate(a,
                            new SimpleDateFormat("yyyy-MM-dd"),
                            new SimpleDateFormat("dd/MM/yyyy"));
                    fullDateTv.setText(date_format);

                    int b = response.body().getRuntime();

                    int h = b / 60;
                    int m = b % 60;

                    tvReleaseRuntime.setText(String.format("%dh %dm", h, m));

                    taglineTv.setText(response.body().getTagline());

                    if (response.body().getOverview().equals("")) {
                        noOverview.setVisibility(View.VISIBLE);
                    }

                    overviewListTv.setText(response.body().getOverview());
                    statusResultTv.setText(response.body().getStatus());
                    originalLangResultTv.setText(response.body().getOriginal_language());

                    Locale usa = new Locale("en", "US");
//                    Currency dollars = Currency.getInstance(usa);
                    NumberFormat dollarFormat = NumberFormat.getCurrencyInstance(usa);

                    String bud = dollarFormat.format(Integer.parseInt(response.body().getBudget()));
                    String rev = dollarFormat.format(Long.parseLong(response.body().getRevenue()));

                    budgetResultTv.setText(bud);
                    revenueResultTv.setText(rev);

                    Glide.with(MovieDetails.this).load(response.body().getPoster_path()).into(cardImage);

                    String genreVal = "";

                    for (int i = 0; i < response.body().getGenres().size(); i++) {
                        int val = response.body().getGenres().size();
                        genreVal += response.body().getGenres().get(i).getName();

                        if (i < val - 1) {
                            genreVal += ", ";
                        }
                    }
                    genreTv.setText(genreVal);

                }
            }

            @Override
            public void onFailure(@NonNull Call<MoviesModel> call, @NonNull Throwable t) {
            }
        });

    }

    private void getMovieReviews(int id) {
        Call<ReviewsResponse> data = RetrofitInstance.getRetrofitInstance().getMovieReviews(id);
        data.enqueue(new Callback<ReviewsResponse>() {
            @Override
            public void onResponse(Call<ReviewsResponse> call, Response<ReviewsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getResults().size() == 0) {
                        noReview.setVisibility(View.VISIBLE);
                    }
                    reviewsModelList.addAll(response.body().getResults());
                    reviewItemAdapter.notifyDataSetChanged();

//                  String ava = response.body().getResults().get(1).getAuthorDetails().getAvatarPath();
//
//                    String ava1 = ava.substring(1);
//
//                    Log.d("Dhag", "onResponse: "+ava1);
                }
            }

            @Override
            public void onFailure(Call<ReviewsResponse> call, Throwable t) {
                Log.d("Dhag", "onFail: Call: " + call.toString() + "\nt: " + t.toString());
            }
        });
    }

    private void getRecomMovies(int id) {

        Call<MoviesResponse> data = RetrofitInstance.getRetrofitInstance().getRecommendedMovies(id);
        data.enqueue(new Callback<MoviesResponse>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<MoviesResponse> call, @NonNull Response<MoviesResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
//                    progressBar.setVisibility(View.GONE);
                    recommendedMoviesList.addAll(response.body().getResults());
                    recommendedItemAdapter.notifyDataSetChanged();

                    TextView noRecommndation = findViewById(R.id.noRecommendation);

                    if (response.body().getResults().size() == 0) {
                        noRecommndation.setVisibility(View.VISIBLE);
                    }

                    if (ChildItemAdapter.which_movie_item) {
                        vote_final = ChildItemAdapter.mpos;
                    }
                    if (!ChildItemAdapter.which_movie_item) {
                        vote_final = ChildItemSearchAdapter.mposs;

                    }

                    String votePercentage = String.valueOf(vote_final);
                    Log.d("TAG", "onResponse: " + votePercentage);
                    if (vote_final > 69) {

                        detailsRatingPercentage.setText(votePercentage);
                        Drawable drawable = ContextCompat.getDrawable(MovieDetails.this, R.drawable.circle_green);
                        detailsRatingProgress.setProgressDrawable(drawable);

                        detailsRatingProgress.setProgress(vote_final);
                    }

                    if (vote_final < 70) {
                        detailsRatingPercentage.setText(votePercentage);
                        Drawable drawable = ContextCompat.getDrawable(MovieDetails.this, R.drawable.circle_yellow);
                        detailsRatingProgress.setProgressDrawable(drawable);

                        detailsRatingProgress.setProgress(vote_final);
                    }

                    if (vote_final == 0 && votePercentage.equals("0")) {
                        detailsRatingPercentage.setText("NR");
                        detailsPercentageSign.setVisibility(View.GONE);
//                        Drawable drawable = ContextCompat.getDrawable(MovieDetails.this, R.drawable.circle_grey);
//                        detailsRatingProgress.setProgressDrawable(drawable);

                        detailsRatingProgress.getProgressDrawable().setColorFilter(Color.GRAY, android.graphics.PorterDuff.Mode.SRC_IN);

                        int paddingDp = 1;
                        float density = MovieDetails.this.getResources().getDisplayMetrics().density;
                        int paddingPixel = (int) (paddingDp * density);
                        detailsRatingPercentage.setPadding(paddingPixel, 0, 0, 0);
                        detailsRatingProgress.setProgress(0);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<MoviesResponse> call, @NonNull Throwable t) {

            }
        });
    }


    private void getPopMovies1(int idC) {

        Call<MoviesModel> data = RetrofitInstance.getRetrofitInstance().getCastDetails(idC);
        data.enqueue(new Callback<MoviesModel>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<MoviesModel> call, @NonNull Response<MoviesModel> response) {
                if (response.isSuccessful() && response.body() != null) {

                    TextView noCast = findViewById(R.id.noCast);

                    if (response.body().getCredits().getCast().size() == 0) {
                        noCast.setVisibility(View.VISIBLE);
                    }
//                    progressBar.setVisibility(View.GONE);
//                    Toast.makeText(MovieDetails.this, ""+response.body().getCredits().getCast().get(0).getName(), Toast.LENGTH_SHORT).show();
//                    castModelList.addAll();
                    castModelList.addAll(response.body().getCredits().getCast());

                    castItemAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(@NonNull Call<MoviesModel> call, @NonNull Throwable t) {

            }
        });
    }


}