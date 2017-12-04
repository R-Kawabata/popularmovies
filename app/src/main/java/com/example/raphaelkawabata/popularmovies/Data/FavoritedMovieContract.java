package com.example.raphaelkawabata.popularmovies.Data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by raphael.kawabata on 10/11/2017.
 */

public class FavoritedMovieContract {

    public static final String CONTENT_AUTHORITY = "com.example.raphaelkawabata.popularmovies";
    public static final String PATH_MOVIE = "movie";
    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final class FavoritesEntry implements BaseColumns {
        public static final String TABLE_NAME = "favorite";
        public static final String COLUMN_MOVIE = "id";
        public static final String COLUMN_VOTE_AVERAGE = "voteAverage";
        public static final String COLUMN_TITLE = "original_title";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_BACKDROP = "backdrop_path";
        public static final String COLUMN_RELEASE_DATE = "releaseDate";
        public static final String[] COLUMNS = {COLUMN_MOVIE, COLUMN_VOTE_AVERAGE,
                COLUMN_TITLE, COLUMN_OVERVIEW, COLUMN_BACKDROP, COLUMN_RELEASE_DATE};

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_NAME;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_NAME;

        public static Uri buildMovieUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
