package com.example.raphaelkawabata.popularmovies;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieInformation {

    @SerializedName("vote_count")
    private Integer voteCount = 0;

    @SerializedName("id")
    private Integer id = 0;

    @SerializedName("video")
    private boolean video;

    @SerializedName("vote_average")
    private float voteAverage = 0;

    @SerializedName("title")
    private String title;

    @SerializedName("popularity")
    private float popularity = 0;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("original_language")
    private String originalLanguage;

    @SerializedName("original_title")
    private String originalTitle;

    @SerializedName("genre_id")
    private List<Integer> genreIds;

    @SerializedName("backdrop_path")
    private String backdropPath;

    @SerializedName("adult")
    private boolean isAdult = false;

    @SerializedName("overview")
    private String overview;

    @SerializedName("release_date")
    private String releaseDate;

    public MovieInformation(Integer voteCount, Integer id, boolean video, float voteAverage,
                            String title, float popularity, String posterPath, String originalLanguage,
                            String originalTitle, List<Integer> genreIds, String backdropPath, boolean isAdult,
                            String overview, String releaseDate) {
        this.setVoteCount(voteCount);
        this.setId(id);
        this.setVoteAverage(voteAverage);
        this.setVideo(video);
        this.setTitle(title);
        this.setPopularity(popularity);
        this.setPosterPath(posterPath);
        this.setOriginalLanguage(originalLanguage);
        this.setOriginalTitle(originalTitle);
        this.setIds(genreIds);
        this.setBackdropPath(backdropPath);
        this.setAdult(isAdult);
        this.setOverview(overview);
        this.setReleaseDate(releaseDate);
    }

    public boolean getVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public float getPopularity() {
        return popularity;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public List<Integer> getIds() {
        return genreIds;
    }

    public void setIds(List<Integer> ids) {
        this.genreIds = ids;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public boolean isAdult() {
        return isAdult;
    }

    public void setAdult(boolean adult) {
        isAdult = adult;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

}
