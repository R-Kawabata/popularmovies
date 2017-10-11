package com.example.raphaelkawabata.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.ListItemCLickListener {

    public List<MovieInformation> movieList = new ArrayList<>(20);
    public ArrayList<String> posterPathList = new ArrayList<>();
    public int currentPage = 1;
    public int index = 0;
    String completeUrlMainPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        completeUrlMainPage = updateUrl(null, currentPage);

        setContentView(R.layout.activity_main);

        requestJsonObject(this, completeUrlMainPage);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        int itemSelected = item.getItemId();
        String completeUrlMainPage;
        String urlCategory;
        if (itemSelected == R.id.sort_popular) {
            urlCategory = "popular";
            Log.i("MainActivity", "onOptionsItemSelected: change urlCategory to " + urlCategory);
            completeUrlMainPage = updateUrl(urlCategory, 1);
            requestJsonObject(this, completeUrlMainPage);
            return true;
        } else if (itemSelected == R.id.sort_top_rated) {
            urlCategory = "top_rated";
            Log.i("MainActivity", "onOptionsItemSelected: change urlCategory to " + urlCategory);
            completeUrlMainPage = updateUrl(urlCategory, 1);
            requestJsonObject(this, completeUrlMainPage);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void requestJsonObject(final Context context, String url) {

        RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());

        movieList.clear();
        posterPathList.clear();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Gson gson = new Gson();
                            ArrayList<String> poster_path = new ArrayList<>();
                            JSONArray jsonArray = response.getJSONArray("results");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject movieJsonObject = jsonArray.getJSONObject(i);
                                MovieInformation jsonObjectString = gson.fromJson(movieJsonObject.toString(), MovieInformation.class);
                                movieList.add(jsonObjectString);
                                poster_path.add(movieList.get(i).getPosterPath());
                            }
                            initView(poster_path);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
        requestQueue.add(request);
    }

    private void initView(ArrayList<String> posterPath) {

        if (posterPath.get(0) != null) {
            posterPathList.addAll(posterPath);
            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.grid_recyclerview);
            recyclerView.setHasFixedSize(true);
            LinearLayoutManager mLayoutManager;
            int orientation = getResources().getConfiguration().orientation;
            if (orientation == 1) {
                mLayoutManager = new GridLayoutManager(getApplicationContext(), 2, LinearLayoutManager.VERTICAL, false);
            } else {
                mLayoutManager = new GridLayoutManager(getApplicationContext(), 4, LinearLayoutManager.VERTICAL, false);
            }
            recyclerView.setLayoutManager(mLayoutManager);

            RecyclerViewAdapter adapter = new RecyclerViewAdapter(getApplicationContext(), posterPath, this);
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
        updatedUrl = url + category + page + currentPage + apiKey;
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

}
