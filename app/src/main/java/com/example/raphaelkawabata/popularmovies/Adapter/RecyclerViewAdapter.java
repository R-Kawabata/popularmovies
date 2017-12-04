package com.example.raphaelkawabata.popularmovies.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.raphaelkawabata.popularmovies.R;

import java.util.ArrayList;

/**
 * Created by raphael.kawabata on 18/09/2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MovieViewHolder> {

    private final Context context;
    final private ListItemCLickListener mOnClickListener;
    private ArrayList<String> movieInformation;
    private ProgressBar mLoadingIndicator;

    public RecyclerViewAdapter(Context context, ArrayList<String> movieInformation, ListItemCLickListener listener) {
        this.movieInformation = movieInformation;
        this.context = context;
        mOnClickListener = listener;
    }

    public MovieViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutRow = R.layout.row_layout;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutRow, viewGroup, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder viewHolder, int position) {
        String posterPath = movieInformation.get(position);
        mLoadingIndicator.setVisibility(View.VISIBLE);
        Glide.with(this.context)
                .load("https://image.tmdb.org/t/p/w185/" + posterPath)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        mLoadingIndicator.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        mLoadingIndicator.setVisibility(View.GONE);
                        return false;
                    }
                })
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade(700)
                .error(R.mipmap.ic_warning_white_24dp)
                .into(viewHolder.listItemView);
    }

    @Override
    public int getItemCount() {
        return movieInformation.size();
    }

    public interface ListItemCLickListener {
        void onListItemClick(int itemIndex);
    }

    class MovieViewHolder extends RecyclerView.ViewHolder implements OnClickListener {

        public final ImageView listItemView;

        public MovieViewHolder(View itemView) {
            super(itemView);
            listItemView = itemView.findViewById(R.id.iv_movie_poster);
            mLoadingIndicator = itemView.findViewById(R.id.pb_loading);
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            itemView.setOnClickListener(this);

        }

        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }
    }
}
