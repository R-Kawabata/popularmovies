package com.example.raphaelkawabata.popularmovies.Adapter;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.raphaelkawabata.popularmovies.Models.Trailer;
import com.example.raphaelkawabata.popularmovies.R;

import java.util.List;

/**
 * Created by raphael.kawabata on 22/11/2017.
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerHolder> {

    public List<Trailer> mTrailers;
    public TrailerListClickListener mListener;

    public TrailerAdapter(TrailerListClickListener listClickListener, List<Trailer> trailers) {
        mTrailers = trailers;
        mListener = listClickListener;
    }

    @Override
    public TrailerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.trailer_list, parent, false);
        return new TrailerHolder(v);
    }

    @Override
    public void onBindViewHolder(TrailerHolder holder, int position) {
        holder.trailerName.setText(mTrailers.get(position).getTrailerName());
    }

    @Override
    public int getItemCount() {
        return mTrailers.size();
    }

    public void watchYoutubeVideo(Context context, String id) {
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + id));
        try {
            context.startActivities(new Intent[]{appIntent});
        } catch (ActivityNotFoundException e) {
            context.startActivities(new Intent[]{webIntent});
        }
    }

    public interface TrailerListClickListener {
        void onClick(int position);
    }

    public class TrailerHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView trailerName;

        public TrailerHolder(View itemView) {
            super(itemView);
            trailerName = itemView.findViewById(R.id.tv_trailer_content);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mListener.onClick(clickedPosition);
            watchYoutubeVideo(v.getContext(), mTrailers.get(clickedPosition).getTrailer());
        }
    }
}
