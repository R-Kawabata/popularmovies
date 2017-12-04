package com.example.raphaelkawabata.popularmovies.Models;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class MovieInformation extends BaseObservable implements Parcelable {

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {

        @Override
        public MovieInformation createFromParcel(Parcel source) {
            return new MovieInformation(source);
        }

        @Override
        public MovieInformation[] newArray(int size) {
            return new MovieInformation[size];
        }
    };
    @SerializedName("vote_count")
    private String voteCount;
    @SerializedName("id")
    private String id;
    @SerializedName("video")
    private String video;
    @SerializedName("vote_average")
    private String voteAverage;
    @SerializedName("title")
    private String title;
    @SerializedName("popularity")
    private String popularity;
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("original_language")
    private String originalLanguage;
    @SerializedName("original_title")
    private String originalTitle;
    @SerializedName("genre_id")
    private String genreIds;
    @SerializedName("backdrop_path")
    private String backdropPath;
    @SerializedName("adult")
    private String isAdult;
    @SerializedName("overview")
    private String overview;
    @SerializedName("release_date")
    private String releaseDate;

    public MovieInformation(Parcel in) {
        String[] data = new String[14];
        in.readStringArray(data);
        this.voteCount = data[0];
        this.id = data[1];
        this.video = data[2];
        this.voteAverage = data[3];
        this.title = data[4];
        this.popularity = data[5];
        this.posterPath = data[6];
        this.originalLanguage = data[7];
        this.originalTitle = data[8];
        this.genreIds = data[9];
        this.backdropPath = data[10];
        this.isAdult = data[11];
        this.overview = data[12];
        this.releaseDate = data[13];
    }

    public MovieInformation() {
    }

    @Bindable
    public String getVideo() {
        return video;
    }

    @Bindable
    public void setVideo(String video) {
        this.video = video;
    }

    @Bindable
    public String getVoteAverage() {
        return voteAverage;
    }

    @Bindable
    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    @Bindable
    public void setTitle(String title) {
        this.title = title;
    }

    @Bindable
    public String getPosterPath() {
        return posterPath;
    }

    @Bindable
    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    @Bindable
    public String getOriginalTitle() {
        return originalTitle;
    }

    @Bindable
    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    @Bindable
    public String getOverview() {
        return overview;
    }

    @Bindable
    public void setOverview(String overview) {
        this.overview = overview;
    }

    @Bindable
    public String getVoteCount() {
        return voteCount;
    }

    @Bindable
    public void setVoteCount(String voteCount) {
        this.voteCount = voteCount;
    }

    @Bindable
    public String getId() {
        return id;
    }

    @Bindable
    public void setId(String id) {
        this.id = id;
    }

    @Bindable
    public String getPopularity() {
        return popularity;
    }

    @Bindable
    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    @Bindable
    public String getOriginalLanguage() {
        return originalLanguage;
    }

    @Bindable
    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    @Bindable
    public String getIds() {
        return genreIds;
    }

    @Bindable
    public void setIds(String ids) {
        this.genreIds = ids;
    }

    @Bindable
    public String getBackdropPath() {
        return backdropPath;
    }

    @Bindable
    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String isAdult() {
        return isAdult;
    }

    public void setAdult(String adult) {
        isAdult = adult;
    }

    @Bindable
    public String getReleaseDate() {
        return releaseDate;
    }

    @Bindable
    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{
                this.voteCount,
                this.id,
                this.video,
                this.voteAverage,
                this.title,
                this.popularity,
                this.posterPath,
                this.originalLanguage,
                this.originalTitle,
                this.genreIds,
                this.backdropPath,
                this.isAdult,
                this.overview,
                this.releaseDate
        });
    }
}
