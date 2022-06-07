package com.example.watch_together.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Model class for movies
 */
public class MovieModel {

    private String movieID;
    private String title;
    private String releaseDate;
    private String directors;
    private float voteAverage;
    private String movieOverview;
    private String posterPath;
    private ArrayList<String> genres = new ArrayList<>();

    public MovieModel(){}


    public MovieModel(String movieID, String title, String releaseDate, String directors, float voteAverage, String movieOverview, String posterPath, ArrayList<String> genres) {
        this.movieID = movieID;
        this.title = title;
        this.releaseDate = releaseDate;
        this.directors = directors;
        this.voteAverage = voteAverage;
        this.movieOverview = movieOverview;
        this.posterPath = posterPath;
        this.genres = genres;
    }

    public void setMovieID(String movieID) {
        this.movieID = movieID;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setDirectors(String directors) {
        this.directors = directors;
    }

    public void setVoteAverage(float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public void setMovieOverview(String movieOverview) {
        this.movieOverview = movieOverview;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public void setGenres(ArrayList<String> genres) {
        this.genres = genres;
    }

    public String getMovieID() {
        return movieID;
    }

    public String getTitle() {
        return title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getDirectors() {
        return directors;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public String getMovieOverview() {
        return movieOverview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public void addGenres(String genre){
        genres.add(genre);
    }

}
