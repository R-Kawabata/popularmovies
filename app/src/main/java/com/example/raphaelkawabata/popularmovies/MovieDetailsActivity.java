package com.example.raphaelkawabata.popularmovies;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.raphaelkawabata.popularmovies.Utility.DateParser;
import com.example.raphaelkawabata.popularmovies.databinding.ActivityMovieDetailsBinding;
import com.google.gson.Gson;

public class MovieDetailsActivity extends AppCompatActivity {

    ActivityMovieDetailsBinding detailsBinding;

    private MovieInformation movieDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        detailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_details);

        Intent startActivity = getIntent();
        String jsonMovieInfo = getIntent().getStringExtra(Intent.EXTRA_TEXT);
        if (startActivity.hasExtra(Intent.EXTRA_TEXT)) {
            Gson gson = new Gson();

            movieDetails = gson.fromJson(jsonMovieInfo, MovieInformation.class);
            Log.d(this.toString(), "onCreate: " + movieDetails.getOriginalTitle());
            String posterPath = movieDetails.getPosterPath();
            String movieTitle = movieDetails.getOriginalTitle();
            detailsBinding.tvMdTitle.setText(movieTitle);

            if (posterPath != null) {
                Glide.with(this)
                        .load("https://image.tmdb.org/t/p/w500/" + posterPath)
                        .crossFade(700)
                        .listener(new RequestListener<String, GlideDrawable>() {
                            @Override
                            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                detailsBinding.pgMovieDetail.setVisibility(View.GONE);
                                return false;
                            }
                        })
                        .error(R.mipmap.ic_warning_white_24dp)
                        .into(detailsBinding.ivMovieDetailPoster);
            } else {
                Log.e("MovieActivity", "OnCreate: NULL Poster Path!");
            }
            detailsBinding.tvReleaseDate.setText(DateParser.parseReleaseDate(movieDetails.getReleaseDate()));
            detailsBinding.tvMdOverview.setText(movieDetails.getOverview());
            detailsBinding.ratingBar.setVisibility(View.VISIBLE);
            detailsBinding.ratingBar.setEnabled(false);
            detailsBinding.ratingBar.setMax(10);
            detailsBinding.ratingBar.setStepSize(0.1f);
            detailsBinding.ratingBar.setRating(Float.valueOf(movieDetails.getVoteAverage()));
            detailsBinding.tvRating.setText(movieDetails.getVoteAverage() + "/10");
        }
    }
}
