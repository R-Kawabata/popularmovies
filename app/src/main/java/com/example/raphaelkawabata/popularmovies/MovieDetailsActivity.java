package com.example.raphaelkawabata.popularmovies;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.raphaelkawabata.popularmovies.Adapter.ReviewAdapter;
import com.example.raphaelkawabata.popularmovies.Adapter.TrailerAdapter;
import com.example.raphaelkawabata.popularmovies.Data.FavoriteDbHelper;
import com.example.raphaelkawabata.popularmovies.Models.MovieInformation;
import com.example.raphaelkawabata.popularmovies.Models.Review;
import com.example.raphaelkawabata.popularmovies.Models.Trailer;
import com.example.raphaelkawabata.popularmovies.Network.InternetConnection;
import com.example.raphaelkawabata.popularmovies.Utility.DateParser;
import com.example.raphaelkawabata.popularmovies.Utility.GlideLoadImage;
import com.example.raphaelkawabata.popularmovies.databinding.ActivityMovieDetailsBinding;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MovieDetailsActivity extends AppCompatActivity implements TrailerAdapter.TrailerListClickListener {

    public MovieInformation movieDetails;
    public List<Review> reviewList = new ArrayList<>();
    public List<Trailer> trailerList = new ArrayList<>();
    ActivityMovieDetailsBinding detailsBinding;
    VolleyInterface mVolleyCallback = null;
    InternetConnection mInternetConnection;
    FavoriteDbHelper favoriteDbHelper;
    GlideLoadImage glideLoadImage = new GlideLoadImage();
    private Review review;
    private Trailer trailer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        detailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_details);
        Intent startActivity = getIntent();
        String jsonMovieInfo = getIntent().getStringExtra(Intent.EXTRA_TEXT);
        String backdropPath;
        String posterUrlQuality = "/w500/";
        if (startActivity.hasExtra(Intent.EXTRA_TEXT)) {
            Gson gson = new Gson();
            movieDetails = gson.fromJson(jsonMovieInfo, MovieInformation.class);
            int screenOrientation = getResources().getConfiguration().orientation;
            if (screenOrientation == 1) {
                backdropPath = movieDetails.getBackdropPath();
            } else {
                backdropPath = movieDetails.getPosterPath();
                posterUrlQuality = "/w300/";
            }
            final String movieTitle = movieDetails.getOriginalTitle();
            detailsBinding.tvMdTitle.setText(movieTitle);

            if (backdropPath != null) {
                detailsBinding.pgMovieDetail.setVisibility(View.VISIBLE);
                glideLoadImage.loadPoster(this, posterUrlQuality, backdropPath, detailsBinding.ivMovieDetailPoster, detailsBinding.pgMovieDetail);
            } else {
                Log.e("MovieActivity", "OnCreate: NULL Poster Path!");
            }
            detailsBinding.tvReleaseDate.setText(DateParser.parseReleaseDate(movieDetails.getReleaseDate()));
            detailsBinding.fabFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    favoriteDbHelper = new FavoriteDbHelper(getApplicationContext());
                    long row = favoriteDbHelper.addFavoriteMovie(movieDetails);
                    if (row < 0) {
                        favoriteDbHelper.deleteFavorite(movieDetails);
                        Toast.makeText(getApplicationContext(), "removed from Favorite", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Added to Favorite", Toast.LENGTH_SHORT).show();
                    }
                    favoriteDbHelper.getAllFavorite();
                }
            });
            detailsBinding.tvMdOverview.setText(movieDetails.getOverview());
            initRatingBar(movieDetails.getVoteAverage());
            detailsBinding.tvRating.setText(movieDetails.getVoteAverage() + "/10");
        }
        initTrailerVolleyCallback();
        mInternetConnection = new InternetConnection(mVolleyCallback, this);
        mInternetConnection.requestTrailerJson(this, movieDetails.getId());
        initReviewVolleyCallback();
        mInternetConnection = new InternetConnection(mVolleyCallback, this);
        mInternetConnection.requestReviewJson(this, movieDetails.getId());
    }

    void initReviewVolleyCallback() {
        mVolleyCallback = new VolleyInterface() {
            @Override
            public void onSuccess(JSONObject reviewJsonObject) {
                Gson gson = new Gson();
                JSONArray jsonArray = null;
                try {
                    jsonArray = reviewJsonObject.getJSONArray("results");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        reviewJsonObject = jsonArray.getJSONObject(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    review = gson.fromJson(reviewJsonObject.toString(), Review.class);
                    reviewList.add(review);
                }
                initReviewList(reviewList);
            }

            @Override
            public void onFail(String msg) {

            }
        };
    }

    void initTrailerVolleyCallback() {
        mVolleyCallback = new VolleyInterface() {
            @Override
            public void onSuccess(JSONObject trailerJsonObject) {
                Gson gson = new Gson();
                JSONArray jsonArray = null;
                try {
                    jsonArray = trailerJsonObject.getJSONArray("results");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        trailerJsonObject = jsonArray.getJSONObject(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    trailer = gson.fromJson(trailerJsonObject.toString(), Trailer.class);
                    trailerList.add(trailer);
                }
                initTrailerList(trailerList);
            }

            @Override
            public void onFail(String msg) {

            }
        };
    }

    void initReviewList(List<Review> reviewList) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        ReviewAdapter reviewAdapter = new ReviewAdapter(reviewList);
        detailsBinding.rvReviewList.setNestedScrollingEnabled(false);
        detailsBinding.rvReviewList.setLayoutManager(linearLayoutManager);
        detailsBinding.rvReviewList.setAdapter(reviewAdapter);
        detailsBinding.rvReviewList.setFocusable(false);
    }

    void initRatingBar(String rating) {
        detailsBinding.ratingBar.setVisibility(View.VISIBLE);
        detailsBinding.ratingBar.setEnabled(false);
        detailsBinding.ratingBar.setMax(10);
        detailsBinding.ratingBar.setStepSize(0.1f);
        detailsBinding.ratingBar.setRating(Float.valueOf(rating));
        detailsBinding.ratingBar.setFocusable(false);
    }

    void initTrailerList(List<Trailer> trailers) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        TrailerAdapter trailerAdapter = new TrailerAdapter(this, trailers);
        detailsBinding.rvTrailerList.setNestedScrollingEnabled(false);
        detailsBinding.rvTrailerList.setLayoutManager(linearLayoutManager);
        detailsBinding.rvTrailerList.setFocusable(false);
        detailsBinding.rvTrailerList.setAdapter(trailerAdapter);
    }

    @Override
    public void onClick(int position) {
        Toast.makeText(this, "Loading Trailer (^ - ^)", Toast.LENGTH_SHORT).show();
    }
}
