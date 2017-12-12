package com.example.raphaelkawabata.popularmovies;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.raphaelkawabata.popularmovies.Adapter.RecyclerViewAdapter;
import com.example.raphaelkawabata.popularmovies.Data.FavoriteDbHelper;
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

    public List<MovieInformation> movieList = new ArrayList<>();
    public ArrayList<String> posterPathList = new ArrayList<>();
    public int currentPage = 1;
    public int index = 0;
    public FavoriteDbHelper dbHelper;
    ActivityMainBinding binding;
    String completeUrlMainPage;
    VolleyInterface mVolleyCallback = null;
    InternetConnection mInternetConnection;
    private String savedUrlCategory = null;
    private boolean menuFavoriteTab = false;

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
        dbHelper = new FavoriteDbHelper(this);
        dbHelper.getReadableDatabase();
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
            menuFavoriteTab = false;
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
            menuFavoriteTab = false;
            setTitle("Top Rated");
            return true;
        } else if (itemSelected == R.id.favorite_movie) {
            movieList.clear();
            posterPathList.clear();
            showFavorites();
            menuFavoriteTab = true;
            setTitle("Favorites");
        }

        return super.onOptionsItemSelected(item);
    }

    private void initView(ArrayList<String> posterPath) {

        if (posterPath.get(0) != null) {
            GridLayoutManager mLayoutManager;
            posterPathList.addAll(posterPath);
            binding.gridRecyclerview.setHasFixedSize(true);
            RecyclerViewAdapter adapter =
                    new RecyclerViewAdapter(getApplicationContext(), posterPath, this);
            int orientation = getResources().getConfiguration().orientation;
            if (orientation == 1) {
                mLayoutManager = new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false);
            } else {
                mLayoutManager = new GridLayoutManager(this, 4, LinearLayoutManager.VERTICAL, false);
            }
            binding.gridRecyclerview.setLayoutManager(mLayoutManager);
            binding.gridRecyclerview.setItemViewCacheSize(20);
            binding.gridRecyclerview.setDrawingCacheEnabled(true);
            binding.gridRecyclerview.setHasFixedSize(true);
            binding.gridRecyclerview.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
            binding.gridRecyclerview.setAdapter(adapter);
        } else {
            Log.e("MainActivity", "initView: NULL Poster Path!");
        }
    }

    public String updateUrl(String category, int currentPage) {

        String updatedUrl;
        currentPage = 1;
        String apiKey = "&api_key=" + BuildConfig.TMDB_KEY;
        String page = "?page=";
        String url = getResources().getString(R.string.default_url);
        if (category == null) {
            category = "popular";
        }
        if (category.equals("popular")) {
            setTitle("Popular");
        } else if (category.equals("top_hated")) {
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
        if (menuFavoriteTab) {
            Intent intentStartFavoriteMovieActivity = new Intent(MainActivity.this, FavoriteMovieActivity.class);
            Log.d("test", "onListItemClick: " + itemIndex);
            intentStartFavoriteMovieActivity.putExtra("clicked_index", itemIndex);
            startActivity(intentStartFavoriteMovieActivity);
        } else {
            Intent intentStartMovieDetailActivity = new Intent(MainActivity.this, MovieDetailsActivity.class);
            intentStartMovieDetailActivity.putExtra(Intent.EXTRA_TEXT, jsonMovieInfo);
            startActivity(intentStartMovieDetailActivity);
        }
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

    public void showFavorites() {
        GridLayoutManager mLayoutManager;
        movieList.addAll(dbHelper.getAllFavorite());
        for (int i = 0; i < movieList.size(); i++) {
            posterPathList.add(movieList.get(i).getPosterPath());
        }

        RecyclerViewAdapter adapter =
                new RecyclerViewAdapter(getApplicationContext(), posterPathList, this);

        int orientation = getResources().getConfiguration().orientation;

        if (orientation == 1) {
            mLayoutManager = new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false);
        } else {
            mLayoutManager = new GridLayoutManager(this, 4, LinearLayoutManager.VERTICAL, false);
        }
        binding.gridRecyclerview.setLayoutManager(mLayoutManager);
        binding.gridRecyclerview.setItemViewCacheSize(20);
        binding.gridRecyclerview.setDrawingCacheEnabled(true);
        binding.gridRecyclerview.setHasFixedSize(true);
        binding.gridRecyclerview.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        binding.gridRecyclerview.setAdapter(adapter);
    }

}
