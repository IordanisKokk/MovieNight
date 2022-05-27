package com.example.watch_together.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Model class for movies
 */
public class MovieModel {

    private int movieID;
    private String title;
    private String posterPath;
    private String releaseDate;
    private float voteAverage;
    private String movieOverview;

    public MovieModel(int movieID, String title, String posterPath, String releaseDate, float voteAverage, String movieOverview) {
        this.movieID = movieID;
        this.title = title;
        this.posterPath = posterPath;
        this.releaseDate = releaseDate;
        this.voteAverage = voteAverage;
        this.movieOverview = movieOverview;
    }

    public String getTitle() {
        return title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public int getMovieID() {
        return movieID;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public String getMovieOverview() {
        return movieOverview;
    }

}
