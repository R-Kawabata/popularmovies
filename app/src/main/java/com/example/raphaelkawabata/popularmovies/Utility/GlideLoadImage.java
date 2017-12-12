package com.example.raphaelkawabata.popularmovies.Utility;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.raphaelkawabata.popularmovies.R;

/**
 * Created by raphael.kawabata on 08/12/2017.
 */

public class GlideLoadImage {

    private ProgressBar pb;
    private ImageView imageView;

    public void loadPoster(Context context, String posterUrlQuality, String posterUrl, ImageView ivMoviePoster, ProgressBar progressBar) {

        pb = progressBar;
        imageView = ivMoviePoster;

        Glide.with(context)
                .load("https://image.tmdb.org/t/p" + posterUrlQuality + posterUrl)
                .crossFade(400)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        pb.setVisibility(View.GONE);
                        return false;
                    }
                })
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .error(R.mipmap.ic_warning_white_24dp)
                .fitCenter()
                .into(imageView);
    }
}
