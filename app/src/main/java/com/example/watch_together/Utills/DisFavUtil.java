package com.example.watch_together.Utills;

import android.content.Context;

import com.example.watch_together.models.MovieModel;

import java.util.ArrayList;


/**
 * This is a utility class that handles favourite and dismiss requests.
 * This class contains an object of the DbHandler class, so that the database can be accessed.
 * Depending on the input from the user, this class calls the appropriate method in the DbHandler
 * class to execute the appropriate query.
 *
 */
public class DisFavUtil {

    DbHandler databaseHandler;

    public DisFavUtil() {
    }

    public ArrayList<MovieModel> getFavorites(Context context, String userID){
        databaseHandler = new DbHandler(context, null, null, 1);
        return databaseHandler.findFavouritesByUserID(userID);
    }

    public void favouriteMovie(Context context, String userID, String movieID) {
        databaseHandler = new DbHandler(context, null, null, 1);
        databaseHandler.favouriteMovieByID(userID, movieID);
    }

    public void unfavouriteMovie(Context context, String userID, String movieID) {
        databaseHandler = new DbHandler(context, null, null, 1);
        databaseHandler.unfavouriteMovieByID(userID, movieID);
    }

    public void resetFavourites(Context context, String userID) {
        databaseHandler = new DbHandler(context, null, null, 1);
        databaseHandler.resetFavouritesByUserID(userID);
    }

    public void dismissMovie(Context context, String userID, String movieID) {
        databaseHandler = new DbHandler(context, null, null, 1);
        databaseHandler.dismissMovieByID(userID, movieID);
    }

    public void resetDismissed(Context context, String userID) {
        databaseHandler = new DbHandler(context, null, null, 1);
        databaseHandler.resetDismissedByUserID(userID);
    }
}
