package com.example.watch_together.Utills;

import android.content.Context;
import android.util.Log;

import com.example.watch_together.models.MovieModel;

import java.util.ArrayList;


/**
 * This is a utility class that handles the input that the user puts in the searchFragment.
 * This class contains an object of the DbHandler class, so that the database can be accessed.
 * Depending on the input from the user, this class calls the appropriate method in the DbHandler
 * class to execute the appropriate query.
 *
 */
public class SearchUtil {

    private String titleSearch;
    private ArrayList<String> genreSearch;
    DbHandler databaseHandler;
    ArrayList<MovieModel> movies;

    public SearchUtil(String titleSearch, ArrayList<String> genreSearch) {
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
