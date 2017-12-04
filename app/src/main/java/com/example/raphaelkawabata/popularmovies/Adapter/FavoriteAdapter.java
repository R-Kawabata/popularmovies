package com.example.raphaelkawabata.popularmovies.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.raphaelkawabata.popularmovies.Models.MovieInformation;
import com.example.raphaelkawabata.popularmovies.R;

import java.util.List;

/**
 * Created by raphael.kawabata on 01/12/2017.
 */

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteHolder> {

    private final List<MovieInformation> mMovieInformation;

    public FavoriteAdapter(List<MovieInformation> movieInformation) {
        mMovieInformation = movieInformation;
    }

    @Override
    public FavoriteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FavoriteHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.favorite_list, parent, false));
    }

    @Override
    public void onBindViewHolder(FavoriteHolder holder, int position) {
        holder.favoriteMovieTitle.setText(mMovieInformation.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return mMovieInformation.size();
    }

    public class FavoriteHolder extends RecyclerView.ViewHolder {

        public TextView favoriteMovieTitle;

        public FavoriteHolder(View itemView) {
            super(itemView);
            favoriteMovieTitle = itemView.findViewById(R.id.tv_favorite_item_title);
        }
    }
}
