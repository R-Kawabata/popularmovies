package com.example.raphaelkawabata.popularmovies;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.raphaelkawabata.popularmovies.Adapter.RecyclerViewAdapter;
import com.example.raphaelkawabata.popularmovies.Models.MovieInformation;
import com.example.raphaelkawabata.popularmovies.Network.InternetConnection;
import com.example.raphaelkawabata.popularmovies.databinding.ActivityMainBinding;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.ListItemCLickListener {

    public List<MovieInformation> movieList = new ArrayList<>(20);
    public ArrayList<String> posterPathList = new ArrayList<>();
    public int currentPage = 1;
    public int index = 0;
    ActivityMainBinding binding;
    String completeUrlMainPage;
    VolleyInterface mVolleyCallback = null;
    InternetConnection mInternetConnection;
    String savedUrlCategory = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            savedUrlCategory = savedInstanceState.getString("savedUrl");
            completeUrlMainPage = updateUrl(savedUrlCategory, 1);
        } else {
            completeUrlMainPage = updateUrl(null, 1);
        }
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initVolleyCallback();
        mInternetConnection = new InternetConnection(mVolleyCallback, this);
        mInternetConnection.requestJsonObject(this, completeUrlMainPage);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        int itemSelected = item.getItemId();
        String urlCategory;
        if (itemSelected == R.id.sort_popular) {
            urlCategory = "popular";
            Log.i("MainActivity", "onOptionsItemSelected: change urlCategory to " + urlCategory);
            completeUrlMainPage = updateUrl(urlCategory, 1);
            movieList.clear();
            posterPathList.clear();
            mInternetConnection = new InternetConnection(mVolleyCallback, this);
            mInternetConnection.requestJsonObject(this, completeUrlMainPage);
            setTitle("Popular");
            return true;
        } else if (itemSelected == R.id.sort_top_rated) {
            urlCategory = "top_rated";
            Log.i("MainActivity", "onOptionsItemSelected: change urlCategory to " + urlCategory);
            completeUrlMainPage = updateUrl(urlCategory, 1);
            movieList.clear();
            posterPathList.clear();
            mInternetConnection = new InternetConnection(mVolleyCallback, this);
            mInternetConnection.requestJsonObject(this, completeUrlMainPage);
            setTitle("Top Rated");
            return true;
        } else if (itemSelected == R.id.favorite_movie) {
            Intent intentStartFavoriteActivity = new Intent(this, FavoriteMovieActivity.class);
            startActivity(intentStartFavoriteActivity);

        }

        return super.onOptionsItemSelected(item);
    }

    private void initView(ArrayList<String> posterPath) {

        if (posterPath.get(0) != null) {
            LinearLayoutManager mLayoutManager;
            posterPathList.addAll(posterPath);
            RecyclerView recyclerView = findViewById(R.id.grid_recyclerview);
            recyclerView.setHasFixedSize(true);
            int orientation = getResources().getConfiguration().orientation;
            if (orientation == 1) {
                mLayoutManager = new GridLayoutManager(getApplicationContext(), 2, LinearLayoutManager.VERTICAL, false);
            } else {
                mLayoutManager = new GridLayoutManager(getApplicationContext(), 4, LinearLayoutManager.VERTICAL, false);
            }
            RecyclerViewAdapter adapter =
                    new RecyclerViewAdapter(getApplicationContext(), posterPath, this);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setAdapter(adapter);
        } else {
            Log.e("MainActivity", "initView: NULL Poster Path!");
        }
    }

    public String updateUrl(String category, int currentPage) {

        String updatedUrl;
        currentPage = 1;
        String apiKey = "&api_key=";
        String page = "?page=";
        String url = "https://api.themoviedb.org/3/movie/";
        if (category == null) {
            category = "popular";
        }
        if (category == "popular") {
            setTitle("Popular");
        } else if (category == "top_hated") {
            setTitle("Top Rated");
        }
        updatedUrl = url + category + page + currentPage + apiKey;
        savedUrlCategory = category;
        return updatedUrl;
    }

    @Override
    public void onListItemClick(int itemIndex) {
        index = itemIndex;
        MovieInformation movieInfo = movieList.get(index);
        Gson gson = new Gson();
        String jsonMovieInfo = gson.toJson(movieInfo);
        Intent intentStartMovieDetailActivity = new Intent(MainActivity.this, MovieDetailsActivity.class);
        intentStartMovieDetailActivity.putExtra(Intent.EXTRA_TEXT, jsonMovieInfo);
        startActivity(intentStartMovieDetailActivity);
    }

    void initVolleyCallback() {
        mVolleyCallback = new VolleyInterface() {
            @Override
            public void onSuccess(JSONObject movieJsonObject) {
                Gson gson = new Gson();
                ArrayList<String> poster_path = new ArrayList<>();
                JSONArray jsonArray = null;
                try {
                    jsonArray = movieJsonObject.getJSONArray("results");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        movieJsonObject = jsonArray.getJSONObject(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    MovieInformation jsonObjectString = gson.fromJson(movieJsonObject.toString(), MovieInformation.class);
                    movieList.add(jsonObjectString);
                    poster_path.add(movieList.get(i).getPosterPath());
                }
                initView(poster_path);
            }

            @Override
            public void onFail(String msg) {

            }
        };
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("savedUrl", savedUrlCategory);
    }
}
