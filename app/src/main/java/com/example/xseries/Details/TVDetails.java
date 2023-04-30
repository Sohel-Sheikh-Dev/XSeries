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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TVDetails extends AppCompatActivity {

    List<CastModel> TeleVcastModelList;
    List<MoviesModel> TeleVrecommendedMoviesList;
    List<ReviewsModel> TeleVreviewsModelList;
    List<MovieTVTrailerModel> TeleVmovieTVTrailerModelList;
    List<PosterModel> TeleVimageResponseList;
    List<BackdropModel> TeleVbackdropModelList;

    RecyclerView TeleVmediaRecyclerView, TeleVrecomRecyclerView, TeleVreviewsRecyclerView, TeleVimgVidRecyclerView, TeleVmediaImgRecyclerView;

    LinearLayoutManager TeleVlinearLayoutManagerRecom, TeleVlinearLayoutManagerCast, TeleVlinearLayoutManagerReviews, TeleVlinearLayoutManagerMedia, TeleVlinearLayoutManagerImg;

    private CastItemAdapter TeleVcastItemAdapter;
    private RecommendedItemAdapter TeleVrecommendedItemAdapter;
    private ReviewItemAdapter TeleVreviewItemAdapter;
    private MediaAdapter TeleVlatestTrailerAdapter;
    private ImagesAdapter TeleVimagesAdapter;

    ImageView TeleVdetailsSearchButton, TeleVdetailsCloseButton, TeleVdetailsIV, TeleVcardImage, TeleVplayIV, TeleVnavigationMovieDetails,TeleVaccountMovieDetails;
    static EditText TeleVdetailsSearchET;
    TextView TeleVnameTv, TeleVdateTv, TeleVfullDateTv, TeleVtvReleaseRuntime, TeleVtaglineTv, TeleVoverviewListTv, TeleVstatusResultTv, TeleVoriginalLangResultTv, TeleVbudgetResultTv, TeleVrevenueResultTv, TeleVbudgetTv, TeleVrevenueTv, TeleVgenreTv, TeleVnoReview, TeleVnoOverView;
    TextView TeleVpostersTv, TeleVvideoTv, TeleVbackdropTv;
    Button TeleVdetailsSearchTextButton;

    ProgressBar TeleVdetailsRatingProgress;
    TextView TeleVdetailsRatingPercentage, TeleVdetailsPercentageSign;

    boolean selection = false;

    String TeleVvkey;
    int i;
    int TeleVvote_final;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        if (ChildItemAdapter.which_tv_item) {
            i = ChildItemAdapter.mid;
        }
        if (!ChildItemAdapter.which_tv_item) {
            i = ChildItemSearchAdapter.mids;
        }

        init();
        getTVDetails(i);
        getTVTrailer(i);
        getTVReviews(i);
        getRecomTV(i);
        getTVCast(i);
        getTVImages(i);


        TeleVnavigationMovieDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TVDetails.this, "This navigation bar is in Progress!!", Toast.LENGTH_SHORT).show();
            }
        });


        TeleVaccountMovieDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TVDetails.this, "This feature is in Progress!!", Toast.LENGTH_SHORT).show();
            }
        });


        TeleVpostersTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TVDetails.this, "The posters are coming very soon!!",Toast.LENGTH_SHORT).show();
            }
        });

        TeleVvideoTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selection = true;
                Log.d("TAG", "init: " + selection);
                TeleVmediaImgRecyclerView.setVisibility(View.GONE);
                TeleVimgVidRecyclerView.setVisibility(View.VISIBLE);
            }
        });

        TeleVbackdropTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selection = false;
                Log.d("TAG", "init: " + selection);
                TeleVimgVidRecyclerView.setVisibility(View.GONE);
                TeleVmediaImgRecyclerView.setVisibility(View.VISIBLE);
            }
        });
        TeleVdetailsSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TVDetails.this, "This searching feature is in Progress!!", Toast.LENGTH_SHORT).show();
            }
        });

/*
        TeleVdetailsSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TeleVdetailsSearchButton.setVisibility(View.GONE);
                TeleVdetailsCloseButton.setVisibility(View.VISIBLE);
                TeleVdetailsSearchET.setVisibility(View.VISIBLE);
                TeleVdetailsSearchTextButton.setVisibility(View.VISIBLE);

                TeleVdetailsSearchTextButton.setOnClickListener(view -> {

                    if (TextUtils.isEmpty(TeleVdetailsSearchET.getText().toString())) {
                        TeleVdetailsSearchET.setError("Please enter the query");
                    } else {
//                        MainActivity.mainClickedOrDetailsClicked = "TVDetails";
                        MainActivity.TeleVmainClickedOrDetailsClicked = false;
                        Intent intent = new Intent(view.getContext(), SearchActivity.class);
                        intent.putExtra("query", TeleVdetailsSearchET.toString());
                        startActivity(intent);
                        Toast.makeText(view.getContext(), "" + TeleVdetailsSearchET.getText().toString(), Toast.LENGTH_SHORT).show();
                    }

                });

            }
        });

        TeleVdetailsCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TeleVdetailsSearchButton.setVisibility(View.VISIBLE);
                TeleVdetailsCloseButton.setVisibility(View.GONE);
                TeleVdetailsSearchET.setVisibility(View.GONE);
                TeleVdetailsSearchTextButton.setVisibility(View.GONE);

            }
        });
*/
    }

    public static String TeleVgetDetailsMainEditText() {
        return TeleVdetailsSearchET.getText().toString();
    }

    public void init() {
        TeleVdetailsSearchButton = findViewById(R.id.detailsSearchButton);
        TeleVdetailsCloseButton = findViewById(R.id.detailsCloseButton);
        TeleVdetailsSearchET = findViewById(R.id.detailsSearchEditText);
        TeleVdetailsIV = findViewById(R.id.detailsIV);


        TeleVnameTv = findViewById(R.id.titleTV);
        TeleVdateTv = findViewById(R.id.dateTV);
        TeleVfullDateTv = findViewById(R.id.fullDateTV);
        TeleVtvReleaseRuntime = findViewById(R.id.fullTimeTV);
        TeleVtaglineTv = findViewById(R.id.taglineTV);
        TeleVoverviewListTv = findViewById(R.id.overviewListTV);
        TeleVstatusResultTv = findViewById(R.id.statusResultTV);
        TeleVoriginalLangResultTv = findViewById(R.id.originalLangResultTV);
        TeleVbudgetResultTv = findViewById(R.id.budgetResultTV);
        TeleVrevenueResultTv = findViewById(R.id.revenueResultTV);
        TeleVbudgetTv = findViewById(R.id.budgetTV);
        TeleVrevenueTv = findViewById(R.id.revenueTV);
        TeleVcardImage = findViewById(R.id.cardImage);
        TeleVdetailsRatingProgress = findViewById(R.id.detailsRatingProgress);
        TeleVdetailsRatingPercentage = findViewById(R.id.detailsRatingPercentage);
        TeleVdetailsPercentageSign = findViewById(R.id.detailsPercentSign);
        TeleVgenreTv = findViewById(R.id.genreTV);
        TeleVpostersTv = findViewById(R.id.postersTV);
        TeleVbackdropTv = findViewById(R.id.backdropsTV);
        TeleVplayIV = findViewById(R.id.playImageView);
        TeleVnoReview = findViewById(R.id.noReview);
        TeleVnoOverView = findViewById(R.id.noOverview);
        TeleVvideoTv = findViewById(R.id.videosTV);
        TeleVnavigationMovieDetails = findViewById(R.id.navigationMovieDetails);
        TeleVaccountMovieDetails = findViewById(R.id.accountMovieDetails);

        TeleVreviewsModelList = new ArrayList<>();
        TeleVcastModelList = new ArrayList<>();


        TeleVreviewsRecyclerView = findViewById(R.id.reviewsRV);
        TeleVmediaRecyclerView = findViewById(R.id.topCastRV);


        TeleVlinearLayoutManagerReviews = new LinearLayoutManager(TVDetails.this);
        TeleVlinearLayoutManagerReviews.setOrientation(RecyclerView.VERTICAL);

        TeleVlinearLayoutManagerCast = new LinearLayoutManager(TVDetails.this);
        TeleVlinearLayoutManagerCast.setOrientation(RecyclerView.HORIZONTAL);

        TeleVreviewsRecyclerView.setLayoutManager(TeleVlinearLayoutManagerReviews);

        TeleVmediaRecyclerView.setLayoutManager(TeleVlinearLayoutManagerCast);

        TeleVreviewItemAdapter = new ReviewItemAdapter(getApplicationContext(), TeleVreviewsModelList);
        TeleVreviewsRecyclerView.setAdapter(TeleVreviewItemAdapter);

        TeleVcastItemAdapter = new CastItemAdapter(getApplicationContext(), TeleVcastModelList);
        TeleVmediaRecyclerView.setAdapter(TeleVcastItemAdapter);


        TeleVrecommendedMoviesList = new ArrayList<>();
        TeleVmovieTVTrailerModelList = new ArrayList<>();
        TeleVimageResponseList = new ArrayList<>();
        TeleVbackdropModelList = new ArrayList<>();

        TeleVrecomRecyclerView = findViewById(R.id.recommendationsRV);
        TeleVimgVidRecyclerView = findViewById(R.id.mediaVidRV);
        TeleVmediaImgRecyclerView = findViewById(R.id.mediaImgRV);

        TeleVlinearLayoutManagerRecom = new LinearLayoutManager(TVDetails.this);
        TeleVlinearLayoutManagerRecom.setOrientation(RecyclerView.HORIZONTAL);


        TeleVlinearLayoutManagerMedia = new LinearLayoutManager(TVDetails.this);
        TeleVlinearLayoutManagerMedia.setOrientation(RecyclerView.HORIZONTAL);

        TeleVlinearLayoutManagerImg = new LinearLayoutManager(TVDetails.this);
        TeleVlinearLayoutManagerImg.setOrientation(RecyclerView.HORIZONTAL);

        TeleVrecomRecyclerView.setLayoutManager(TeleVlinearLayoutManagerRecom);
        TeleVimgVidRecyclerView.setLayoutManager(TeleVlinearLayoutManagerMedia);
        TeleVmediaImgRecyclerView.setLayoutManager(TeleVlinearLayoutManagerImg);

        TeleVrecommendedItemAdapter = new RecommendedItemAdapter(getApplicationContext(), TeleVrecommendedMoviesList);
        TeleVrecomRecyclerView.setAdapter(TeleVrecommendedItemAdapter);


        TeleVlatestTrailerAdapter = new MediaAdapter(getApplicationContext(), TeleVmovieTVTrailerModelList);
        TeleVimgVidRecyclerView.setAdapter(TeleVlatestTrailerAdapter);

        TeleVimagesAdapter = new ImagesAdapter(getApplicationContext(), TeleVimageResponseList, TeleVbackdropModelList);
        TeleVmediaImgRecyclerView.setAdapter(TeleVimagesAdapter);


        TeleVdetailsSearchTextButton = findViewById(R.id.detailsSearchTextButton);


    }

    private void getRecomTV(int id) {

        Call<MoviesResponse> data = RetrofitInstance.getRetrofitInstance().getRecommendedTV(id);
        data.enqueue(new Callback<MoviesResponse>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<MoviesResponse> call, @NonNull Response<MoviesResponse> response) {
                if (response.isSuccessful() && response.body() != null) {

                    TextView TeleVnoRecommndation = findViewById(R.id.noRecommendation);

                    if (response.body().getResults().size() == 0) {
                        TeleVnoRecommndation.setVisibility(View.VISIBLE);
                    }
//                    progressBar.setVisibility(View.GONE);
                    TeleVrecommendedMoviesList.addAll(response.body().getResults());
                    TeleVrecommendedItemAdapter.notifyDataSetChanged();


                    if (ChildItemAdapter.which_tv_item) {
                        TeleVvote_final = ChildItemAdapter.mpos;
                    }
                    if (!ChildItemAdapter.which_tv_item) {
                        TeleVvote_final = ChildItemSearchAdapter.mposs;

                    }

                    String votePercentage = String.valueOf(TeleVvote_final);
                    Log.d("TAG", "onResponse: " + votePercentage);
                    if (TeleVvote_final > 69) {

                        TeleVdetailsRatingPercentage.setText(votePercentage);
                        Drawable drawable = ContextCompat.getDrawable(TVDetails.this, R.drawable.circle_green);
                        TeleVdetailsRatingProgress.setProgressDrawable(drawable);

                        TeleVdetailsRatingProgress.setProgress(TeleVvote_final);
                    }

                    if (TeleVvote_final < 70) {
                        TeleVdetailsRatingPercentage.setText(votePercentage);
                        Drawable drawable = ContextCompat.getDrawable(TVDetails.this, R.drawable.circle_yellow);
                        TeleVdetailsRatingProgress.setProgressDrawable(drawable);

                        TeleVdetailsRatingProgress.setProgress(TeleVvote_final);
                    }

                    if (TeleVvote_final == 0 && votePercentage.equals("0")) {
                        TeleVdetailsRatingPercentage.setText("NR");
                        TeleVdetailsPercentageSign.setVisibility(View.GONE);
//                        Drawable drawable = ContextCompat.getDrawable(MovieDetails.this, R.drawable.circle_grey);
//                        detailsRatingProgress.setProgressDrawable(drawable);

                        TeleVdetailsRatingProgress.getProgressDrawable().setColorFilter(Color.GRAY, android.graphics.PorterDuff.Mode.SRC_IN);

                        int paddingDp = 1;
                        float density = TVDetails.this.getResources().getDisplayMetrics().density;
                        int paddingPixel = (int) (paddingDp * density);
                        TeleVdetailsRatingPercentage.setPadding(paddingPixel, 0, 0, 0);
                        TeleVdetailsRatingProgress.setProgress(0);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<MoviesResponse> call, @NonNull Throwable t) {

            }
        });
    }

    public void getTVDetails(int id) {

        Call<MoviesModel> data = RetrofitInstance.getRetrofitInstance().getTVDetails(id);
        data.enqueue(new Callback<MoviesModel>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @SuppressLint("DefaultLocale")
            @Override
            public void onResponse(@NonNull Call<MoviesModel> call, @NonNull Response<MoviesModel> response) {
                if (response.isSuccessful() && response.body() != null) {

                    TeleVplayIV.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (TeleVvkey != null) {

                                AlertDialog.Builder builder = new AlertDialog.Builder(TVDetails.this);
                                ViewGroup viewGroup = findViewById(android.R.id.content);
                                View dialogView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.new_media_items, viewGroup, false);
                                YouTubePlayerView youTubePlayerView = dialogView.findViewById(R.id.new_youtube_player_view);
                                youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                                    @Override
                                    public void onReady(YouTubePlayer youTubePlayer) {
                                        youTubePlayer.loadVideo(TeleVvkey, 0);
                                    }
                                });

                                builder.setView(dialogView);
                                AlertDialog alertDialog = builder.create();
                                alertDialog.show();
                            } else {
                                Toast.makeText(TVDetails.this, "It's trailer is not available yet!!", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                    Glide.with(TVDetails.this).load(response.body().getBackdrop_path()).into(TeleVdetailsIV);
                    TeleVnameTv.setText(response.body().getName());

                    if (response.body().getOverview() != null) {
                        TeleVoverviewListTv.setText(response.body().getOverview());
                    } else {
                        TeleVnoOverView.setVisibility(View.VISIBLE);
                    }


                    if (response.body().getAir_date() != null) {
                        String a = response.body().getAir_date();
                        String[] strings = a.split("-");
                        TeleVdateTv.setText("(" + strings[0] + ")");

                        @SuppressLint("SimpleDateFormat") String date_format = ChildItemAdapter.parseDate(a,
                                new SimpleDateFormat("yyyy-MM-dd"),
                                new SimpleDateFormat("dd/MM/yyyy"));
                        TeleVfullDateTv.setText(date_format);
                    }


                    if (response.body().getEpisodeRunTime().size() != 0) {
                        int b = response.body().getEpisodeRunTime().get(0);
                        int h = b / 60;
                        int m = b % 60;

                        TeleVtvReleaseRuntime.setText(String.format("%dm", m));
                    }


                    TeleVtaglineTv.setText(response.body().getTagline());


                    TeleVstatusResultTv.setText(response.body().getStatus());
                    TeleVoriginalLangResultTv.setText(response.body().getOriginal_language());

                    TeleVbudgetTv.setVisibility(View.GONE);
                    TeleVrevenueTv.setVisibility(View.GONE);

                    TeleVbudgetResultTv.setVisibility(View.GONE);
                    TeleVrevenueResultTv.setVisibility(View.GONE);

                    Glide.with(TVDetails.this).load(response.body().getPoster_path()).into(TeleVcardImage);

                    String genreVal = "";

                    for (int i = 0; i < response.body().getGenres().size(); i++) {
                        int val = response.body().getGenres().size();
                        genreVal += response.body().getGenres().get(i).getName();

                        if (i < val - 1) {
                            genreVal += ", ";
                        }
                    }
                    TeleVgenreTv.setText(genreVal);


                }

            }

            @Override
            public void onFailure(@NonNull Call<MoviesModel> call, @NonNull Throwable t) {
            }
        });

    }

    private void getTVImages(int id) {
        Call<ImageResponse> data = RetrofitInstance.getRetrofitInstance().getTVImages(id);
        data.enqueue(new Callback<ImageResponse>() {
            @Override
            public void onResponse(Call<ImageResponse> call, Response<ImageResponse> response) {
                if (response.isSuccessful() && response.body() != null) {

                    TextView TeleVnoMedia = findViewById(R.id.noMedia);

                    if (response.body().getPosters().size() == 0) {
                        TeleVnoMedia.setVisibility(View.VISIBLE);
                    }

                    TeleVimageResponseList.addAll(response.body().getPosters());
                    TeleVbackdropModelList.addAll(response.body().getBackdrops());
                    TeleVimagesAdapter.notifyDataSetChanged();
//                    Log.d("dhally", "onResponse: " + response.body().getPosters());
                }
            }

            @Override
            public void onFailure(Call<ImageResponse> call, Throwable t) {

            }
        });
    }

    private void getTVTrailer(int id) {

        Call<TrailerResponse> data = RetrofitInstance.getRetrofitInstance().getTVTrailer(id);
        data.enqueue(new Callback<TrailerResponse>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<TrailerResponse> call, @NonNull Response<TrailerResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getMovieTVTrailerModelList().size() != 0) {
                    TeleVvkey = response.body().getMovieTVTrailerModelList().get(0).getKey();
                    TeleVmovieTVTrailerModelList.addAll(response.body().getMovieTVTrailerModelList());
//                    TeleVlatestTrailerAdapter.notifyDataSetChanged();
                } else {
                    TeleVvkey = null;
                }

            }

            @Override
            public void onFailure(@NonNull Call<TrailerResponse> call, @NonNull Throwable t) {

            }
        });

    }


    private void getTVReviews(int id) {
        Call<ReviewsResponse> data = RetrofitInstance.getRetrofitInstance().getTVReviews(id);
        data.enqueue(new Callback<ReviewsResponse>() {
            @Override
            public void onResponse(Call<ReviewsResponse> call, Response<ReviewsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getResults().size() == 0) {
                        TeleVnoReview.setVisibility(View.VISIBLE);
                    }
                    TeleVreviewsModelList.addAll(response.body().getResults());
                    TeleVreviewItemAdapter.notifyDataSetChanged();

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

    private void getTVCast(int idC) {

        Call<MoviesModel> data = RetrofitInstance.getRetrofitInstance().getTVCastDetails(idC);
        data.enqueue(new Callback<MoviesModel>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<MoviesModel> call, @NonNull Response<MoviesModel> response) {
                if (response.isSuccessful() && response.body() != null) {

                    TextView TeleVnoCast = findViewById(R.id.noCast);

                    if (response.body().getCredits().getCast().size() == 0) {
                        TeleVnoCast.setVisibility(View.VISIBLE);
                    }
//                    progressBar.setVisibility(View.GONE);
//                    Toast.makeText(MovieDetails.this, ""+response.body().getCredits().getCast().get(0).getName(), Toast.LENGTH_SHORT).show();
//                    castModelList.addAll();
                    TeleVcastModelList.addAll(response.body().getCredits().getCast());

                    TeleVcastItemAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(@NonNull Call<MoviesModel> call, @NonNull Throwable t) {

            }
        });
    }


}