package com.example.raphaelkawabata.popularmovies.Network;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.raphaelkawabata.popularmovies.BuildConfig;
import com.example.raphaelkawabata.popularmovies.MainActivity;
import com.example.raphaelkawabata.popularmovies.VolleyInterface;

import org.json.JSONObject;

public class InternetConnection extends MainActivity {

    VolleyInterface mVolleyCallback = null;
    Context mContext;
    String apikey = BuildConfig.TMDB_KEY;

    public InternetConnection(VolleyInterface volleyCallback, Context context) {
        mVolleyCallback = volleyCallback;
        mContext = context;
    }

    public void requestJsonObject(final Context context, String url) {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(context);

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                if (mVolleyCallback != null) {
                                    mVolleyCallback.onSuccess(response);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if (mVolleyCallback != null) {
                                mVolleyCallback.onFail("FAIL to request JSONObject!");
                            }
                        }
                    });
            requestQueue.add(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void requestReviewJson(final Context context, String Movie_id) {
        String reviewUrl = "http://api.themoviedb.org/3/movie/" + Movie_id + "/reviews?api_key=" + apikey;
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(context);

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, reviewUrl, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                if (mVolleyCallback != null) {
                                    mVolleyCallback.onSuccess(response);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if (mVolleyCallback != null) {
                                mVolleyCallback.onFail("FAIL to request JSONObject!");
                            }
                        }
                    });
            requestQueue.add(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void requestTrailerJson(final Context context, String Movie_id) {
        String reviewUrl = "http://api.themoviedb.org/3/movie/" + Movie_id + "/videos?api_key=" + apikey;
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(context);

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, reviewUrl, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                if (mVolleyCallback != null) {
                                    mVolleyCallback.onSuccess(response);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if (mVolleyCallback != null) {
                                mVolleyCallback.onFail("FAIL to request JSONObject!");
                            }
                        }
                    });
            requestQueue.add(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
