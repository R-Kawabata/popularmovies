package com.example.raphaelkawabata.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.gson.Gson;

public class MovieDetailsActivity extends AppCompatActivity {

    public MovieInformation movieDetails;
    private ImageView moviePoster;
    private ProgressBar movieDetailsProgressBar;
    private TextView textviewTitle;
    private TextView textviewRelaseDate;
    private TextView textviewOverview;
    private RatingBar ratingBar;
    private TextView textviewRate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_movie_details);

        Intent startActivity = getIntent();
        textviewTitle = (TextView) findViewById(R.id.tv_md_title);
        moviePoster = (ImageView) findViewById(R.id.iv_movie_detail_poster);
        textviewRelaseDate = (TextView) findViewById(R.id.tv_release_date);
        textviewOverview = (TextView) findViewById(R.id.tv_md_overview);
        String jsonMovieInfo = getIntent().getStringExtra(Intent.EXTRA_TEXT);
        movieDetailsProgressBar = (ProgressBar) findViewById(R.id.pg_movie_detail);
        movieDetailsProgressBar.setVisibility(View.VISIBLE);
        ratingBar = (RatingBar) findViewById(R.id.rating_bar);
        textviewRate = (TextView) findViewById(R.id.tv_rating);

        if (startActivity.hasExtra(Intent.EXTRA_TEXT)) {
            Gson gson = new Gson();

            movieDetails = gson.fromJson(jsonMovieInfo, MovieInformation.class);

            String posterPath = movieDetails.getPosterPath();
            textviewTitle.setText(movieDetails.getOriginalTitle());

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
                                movieDetailsProgressBar.setVisibility(View.GONE);
                                return false;
                            }
                        })
                        .error(R.mipmap.ic_warning_white_24dp)
                        .into(moviePoster);
            } else {
                Log.e("MovieActivity", "OnCreate: NULL Poster Path!");
            }
            textviewRelaseDate.setText(parseReleaseDate(movieDetails.getReleaseDate()));
            textviewOverview.setText(movieDetails.getOverview());
            ratingBar.setVisibility(View.VISIBLE);
            ratingBar.setEnabled(false);
            ratingBar.setMax(10);
            ratingBar.setStepSize(0.1f);
            ratingBar.setRating(movieDetails.getVoteAverage());
            textviewRate.setText(String.valueOf(movieDetails.getVoteAverage()) + "/10");
        }
    }

    public String parseReleaseDate(String string) {
        int month, day;
        String dateArray[] = string.split("-");
        month = Integer.parseInt(dateArray[1]);
        day = Integer.parseInt(dateArray[2]);
        switch (month) {
            case 1:
                dateArray[1] = "January";
                break;
            case 2:
                dateArray[1] = "February";
                break;
            case 3:
                dateArray[1] = "March";
                break;
            case 4:
                dateArray[1] = "April";
                break;
            case 5:
                dateArray[1] = "May";
                break;
            case 6:
                dateArray[1] = "June";
                break;
            case 7:
                dateArray[1] = "July";
                break;
            case 8:
                dateArray[1] = "August";
                break;
            case 9:
                dateArray[1] = "September";
                break;
            case 10:
                dateArray[1] = "October";
                break;
            case 11:
                dateArray[1] = "November";
                break;
            case 12:
                dateArray[1] = "December";
                break;
        }
        if (day == 1 || day % 10 == 2) {
            dateArray[2] = day + "st";
        } else if (day == 2 || day % 10 == 2) {
            dateArray[2] = day + "nd";
        } else if (day == 3 || day % 10 == 3) {
            dateArray[2] = day + "rd";
        } else {
            dateArray[2] = day + "th";
        }
        string = dateArray[0] + "\n" + dateArray[1] + ", " + dateArray[2];
        return string;
    }
}
