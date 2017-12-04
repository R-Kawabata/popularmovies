package com.example.raphaelkawabata.popularmovies.Models;

import android.databinding.BaseObservable;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by raphael.kawabata on 17/11/2017.
 */

public class Review extends BaseObservable implements Parcelable {

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        @Override
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };
    @SerializedName("id")
    private String reviewId;
    @SerializedName("author")
    private String reviewAuthor;
    @SerializedName("content")
    private String reviewContent;
    @SerializedName("url")
    private String reviewUrl;

    public Review(Parcel in) {
        reviewId = in.readString();
        reviewAuthor = in.readString();
        reviewContent = in.readString();
        reviewUrl = in.readString();
    }

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    public String getReviewAuthor() {
        return reviewAuthor;
    }

    public void setReviewAuthor(String reviewAuthor) {
        this.reviewAuthor = reviewAuthor;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public void setReviewContent(String review_content) {
        this.reviewContent = review_content;
    }

    public String getReviewUrl() {
        return reviewUrl;
    }

    public void setReviewUrl(String review_url) {
        this.reviewUrl = review_url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{
                this.reviewId,
                this.reviewAuthor,
                this.reviewContent,
                this.reviewUrl
        });
    }
}
