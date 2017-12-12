package com.example.raphaelkawabata.popularmovies;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.raphaelkawabata.popularmovies.Data.FavoriteDbHelper;
import com.example.raphaelkawabata.popularmovies.Models.MovieInformation;
import com.example.raphaelkawabata.popularmovies.Utility.DateParser;
import com.example.raphaelkawabata.popularmovies.Utility.GlideLoadImage;
import com.example.raphaelkawabata.popularmovies.databinding.ActivityFavoriteMovieBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raphael.kawabata on 24/11/2017.
 */

public class FavoriteMovieActivity extends AppCompatActivity {

    public FavoriteDbHelper dbHelper;
    ActivityFavoriteMovieBinding favoriteMovieBinding;
    private GlideLoadImage glideLoadImage = new GlideLoadImage();
    private String backdropPath, posterUrlQuality;
    private int indexInt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        favoriteMovieBinding = DataBindingUtil.setContentView(this, R.layout.activity_favorite_movie);
        List<MovieInformation> movieInformation = new ArrayList<>();
        dbHelper = new FavoriteDbHelper(this);
        dbHelper.getReadableDatabase();
        movieInformation.addAll(dbHelper.getAllFavorite());
        Intent startActivity = getIntent();
        if (startActivity.hasExtra("clicked_index")) {
            indexInt = getIntent().getIntExtra("clicked_index", 0);
            int screenOrientation = getResources().getConfiguration().orientation;
            if (screenOrientation == 1) {
                backdropPath = movieInformation.get(indexInt).getBackdropPath();
                posterUrlQuality = "/w300/";
            } else {
                backdropPath = movieInformation.get(indexInt).getPosterPath();
                posterUrlQuality = "/w300/";
            }
            favoriteMovieBinding.pgMovieDetail.setVisibility(View.VISIBLE);
            glideLoadImage.loadPoster(this, posterUrlQuality, backdropPath, favoriteMovieBinding.ivMovieDetailPoster, favoriteMovieBinding.pgMovieDetail);
            //movieInformation.get(indexInt).getTitle() ==> original title
            favoriteMovieBinding.tvMdTitleFavorite.setText(movieInformation.get(indexInt).getTitle());
            favoriteMovieBinding.tvReleaseDate.setText(DateParser.parseReleaseDate(movieInformation.get(indexInt).getReleaseDate()));
            favoriteMovieBinding.tvRating.setText(movieInformation.get(indexInt).getVoteAverage());
            favoriteMovieBinding.ratingBar.setVisibility(View.VISIBLE);
            favoriteMovieBinding.ratingBar.setEnabled(false);
            favoriteMovieBinding.ratingBar.setMax(10);
            favoriteMovieBinding.ratingBar.setStepSize(0.1f);
            favoriteMovieBinding.ratingBar.setRating(Float.valueOf(movieInformation.get(indexInt).getVoteAverage()));
            favoriteMovieBinding.ratingBar.setFocusable(false);
            favoriteMovieBinding.tvMdOverviewFavorite.setText(movieInformation.get(indexInt).getOverview());
        }
    }
}
