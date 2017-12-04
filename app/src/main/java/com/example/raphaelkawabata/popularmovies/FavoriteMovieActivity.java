package com.example.raphaelkawabata.popularmovies;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;

import com.example.raphaelkawabata.popularmovies.Adapter.FavoriteAdapter;
import com.example.raphaelkawabata.popularmovies.Data.FavoriteDbHelper;
import com.example.raphaelkawabata.popularmovies.Models.MovieInformation;
import com.example.raphaelkawabata.popularmovies.databinding.ActivityFavoriteMovieBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raphael.kawabata on 24/11/2017.
 */

public class FavoriteMovieActivity extends AppCompatActivity {

    public FavoriteDbHelper dbHelper;
    ActivityFavoriteMovieBinding favoriteMovieBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        favoriteMovieBinding = DataBindingUtil.setContentView(this, R.layout.activity_favorite_movie);
        List<MovieInformation> movieInformation = new ArrayList<>();
        dbHelper = new FavoriteDbHelper(this);
        dbHelper.getReadableDatabase();
        movieInformation.addAll(dbHelper.getAllFavorite());
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(this, 1, LinearLayoutManager.VERTICAL, false);
        FavoriteAdapter favoriteAdapter = new FavoriteAdapter(movieInformation);
        favoriteMovieBinding.rvFavorite.setLayoutManager(linearLayoutManager);
        favoriteMovieBinding.rvFavorite.setAdapter(favoriteAdapter);
        favoriteMovieBinding.rvFavorite.setFocusable(false);
    }
}
