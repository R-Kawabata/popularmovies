package com.example.raphaelkawabata.popularmovies.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.raphaelkawabata.popularmovies.Models.Review;
import com.example.raphaelkawabata.popularmovies.R;

import java.util.List;

/**
 * Created by raphael.kawabata on 17/11/2017.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewHolder> {

    private final List<Review> mReview;

    public ReviewAdapter(List<Review> mReview) {
        this.mReview = mReview;
    }

    @Override
    public ReviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ReviewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.review_list, parent, false));
    }

    @Override
    public void onBindViewHolder(ReviewHolder holder, int position) {
        holder.author.setText(mReview.get(position).getReviewAuthor());
        holder.content.setText(mReview.get(position).getReviewContent());
    }

    @Override
    public int getItemCount() {
        return mReview.size();
    }

    public class ReviewHolder extends RecyclerView.ViewHolder {

        public TextView author;
        public TextView content;

        public ReviewHolder(View itemView) {
            super(itemView);
            author = itemView.findViewById(R.id.tv_review_author);
            content = itemView.findViewById(R.id.tv_review_content);
        }
    }
}