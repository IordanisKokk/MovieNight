package com.example.watch_together.Utills;

import android.content.Context;
import android.util.Log;

import com.example.watch_together.models.MovieModel;

import java.util.ArrayList;

public class SearchUtill {

    private String titleSearch;
    private ArrayList<String> genreSearch;
    DbHandler databaseHandler;
    ArrayList<MovieModel> movies;

    public SearchUtill(String titleSearch, ArrayList<String> genreSearch) {
        this.titleSearch = titleSearch;
        this.genreSearch = genreSearch;
    }
    public String getTitleSearch() {
        return titleSearch;
    }

    public ArrayList<String> getGenreSearch() {
        return genreSearch;
    }

    public ArrayList<MovieModel> searchForMovies(Context context){
        databaseHandler = new DbHandler(context, null, null, 1);
        ArrayList<MovieModel> movies = new ArrayList<>();

        if(titleSearch == null && genreSearch.get(0).equals("")){
            return null;
        }else if(genreSearch.get(0).equals("")){
            movies = databaseHandler.findMovieByTitle(titleSearch);
        }else{
            //TODO
            StringBuilder genresSearchStringBuilder = new StringBuilder();
            for (String genre : genreSearch) {
                genresSearchStringBuilder.append("'" + genre.toString() + "',");
            }

            genresSearchStringBuilder.deleteCharAt(genresSearchStringBuilder.length()-1);

            Log.d("de",genresSearchStringBuilder.toString());
            movies = databaseHandler.findMovieByTitleAndGenre(titleSearch, genresSearchStringBuilder.toString());
//            return null;
        }


        return movies;
    }

}
