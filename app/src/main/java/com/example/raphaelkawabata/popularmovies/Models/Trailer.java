package com.example.raphaelkawabata.popularmovies.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by raphael.kawabata on 21/11/2017.
 */

public class Trailer {
    @SerializedName("key")
    private String trailer;

    @SerializedName("name")
    private String trailerName;

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public String getTrailerName() {
        return trailerName;
    }

    public void setTrailerName(String trailerName) {
        this.trailerName = trailerName;
    }
}
