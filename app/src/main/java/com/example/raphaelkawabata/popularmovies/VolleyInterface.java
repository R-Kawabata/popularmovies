package com.example.raphaelkawabata.popularmovies;

import org.json.JSONObject;

/**
 * Created by raphael.kawabata on 08/11/2017.
 */

public interface VolleyInterface {
    void onSuccess(JSONObject movieJsonObject);

    void onFail(String msg);
}
