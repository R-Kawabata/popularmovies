package com.example.raphaelkawabata.popularmovies.Data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by raphael.kawabata on 10/11/2017.
 */

public class FavoriteContentProvider extends ContentProvider {

    public static final int MOVIE = 100;
    public static final int MOVIE_ID = 101;

    private static final UriMatcher uriMatcher = buildUriMatcher();
    private FavoriteDbHelper favoriteDbHelper;

    private static UriMatcher buildUriMatcher() {
        String content = FavoritedMovieContract.CONTENT_AUTHORITY;

        UriMatcher matcher = new UriMatcher(uriMatcher.NO_MATCH);
        matcher.addURI(content, FavoritedMovieContract.PATH_MOVIE, MOVIE);
        matcher.addURI(content, FavoritedMovieContract.PATH_MOVIE + "/#", MOVIE_ID);

        return matcher;
    }


    @Override
    public boolean onCreate() {
        favoriteDbHelper = new FavoriteDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final SQLiteDatabase db = favoriteDbHelper.getWritableDatabase();
        Cursor cursor;
        switch (uriMatcher.match(uri)) {
            case MOVIE:
                cursor = db.query(
                        FavoritedMovieContract.FavoritesEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case MOVIE_ID:
                long _id = ContentUris.parseId(uri);
                cursor = db.query(
                        FavoritedMovieContract.FavoritesEntry.TABLE_NAME,
                        projection,
                        FavoritedMovieContract.FavoritesEntry._ID + " = ?",
                        new String[]{String.valueOf(_id)},
                        null,
                        null,
                        sortOrder
                );
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)) {
            case MOVIE:
                return FavoritedMovieContract.FavoritesEntry.CONTENT_TYPE;
            case MOVIE_ID:
                return FavoritedMovieContract.FavoritesEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = favoriteDbHelper.getWritableDatabase();
        long _id;
        Uri returnUri;

        switch (uriMatcher.match(uri)) {
            case MOVIE:
                _id = db.insert(FavoritedMovieContract.FavoritesEntry.TABLE_NAME, null, values);
                if (_id > 0) {
                    returnUri = FavoritedMovieContract.FavoritesEntry.buildMovieUri(_id);
                } else {
                    throw new UnsupportedOperationException("Unable to insert rows into: " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        db.close();
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = favoriteDbHelper.getWritableDatabase();
        int rows;

        switch (uriMatcher.match(uri)) {
            case MOVIE:
                rows = db.delete(FavoritedMovieContract.FavoritesEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unable to insert rows into: " + uri);
        }
        if (selection == null || rows != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rows;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = favoriteDbHelper.getWritableDatabase();
        int rows;

        switch (uriMatcher.match(uri)) {
            case MOVIE:
                rows = db.update(FavoritedMovieContract.FavoritesEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unable to insert rows into: " + uri);
        }
        if (rows != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rows;
    }
}
