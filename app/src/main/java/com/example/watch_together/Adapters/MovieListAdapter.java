package com.example.watch_together.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.watch_together.R;
import com.example.watch_together.Utills.DisFavUtil;
import com.example.watch_together.Utills.WatchTogether;
import com.example.watch_together.models.MovieModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 * Adapter class for the RecyclerView view. It takes an Arraylist of MovieModel objects
 * and displays them in the RecyclerView as cards.
 */
public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieViewHolder> {
    private ArrayList<MovieModel> movies = new ArrayList<>(); // The Arraylist of movies.
    private Context context; // The current context.
    private boolean favouriteRemoves; // Bool variable to check if the favourite button should remove the movie from the displayed list.
    private String userID; // The ID of the user performing actions on the list of movies.

    /**
     * Class constructor that sets the ID of the user to the empty string.
     * @param context the current context.
     * @param movies the Arraylist of MovieModel objects that holds the movies.
     * @param favouriteRemoves true if you want the favourite button to remove from the list of displayed movies.
     */
    public MovieListAdapter(Context context, ArrayList<MovieModel> movies, boolean favouriteRemoves) {
        this.context = context;
        this.movies = movies;
        this.favouriteRemoves = favouriteRemoves;
        this.userID = "";
    }

    /**
     * Full class constructor.
     * @param context the current context.
     * @param movies the Arraylist of MovieModel objects that holds the movies.
     * @param favouriteRemoves true if you want the favourite button to remove from the list of displayed movies.
     * @param userID the ID of the user performing actions on the list of movies.
     */
    public MovieListAdapter(Context context, ArrayList<MovieModel> movies, boolean favouriteRemoves, String userID) {
        this(context, movies, favouriteRemoves);
        this.userID = userID;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_movie_card, parent, false);
        MovieViewHolder viewHolder = new MovieViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        holder.bindMovie(movies.get(position)); // Bind the movie in the given position.
        holder.addButtonListeners(position); // Add listeners to the buttons of the movie in the given position.
    }

    @Override
    /**
     * Returns the amount of movies the adapter currently holds.
     */
    public int getItemCount() {
        return movies.size();
    }

    /**
     * Class that binds the given movie to the given view.
     */
    public class MovieViewHolder extends RecyclerView.ViewHolder {
        private Context context;

        private String movieID;
        private ImageView poster;
        private TextView title;
        private TextView year;
        private TextView rating;
        private TextView director;
        private TextView plot;
        private ConstraintLayout categories;
        private Button infoButton;
        private Button favouriteButton;
        private Button dismissButton;

        /**
         * Class constructor that saves references to the items of the view that the movie will be bound to.
         * @param movieView
         */
        private MovieViewHolder(View movieView) {
            super(movieView);
            context = movieView.getContext();

            poster = (ImageView) movieView.findViewById(R.id.poster);
            title = (TextView) movieView.findViewById(R.id.title);
            year = (TextView) movieView.findViewById(R.id.year);
            rating = (TextView) movieView.findViewById(R.id.rating);
            director = (TextView) movieView.findViewById(R.id.director);
            plot = (TextView) movieView.findViewById(R.id.plot);
            categories = (ConstraintLayout)movieView.findViewById(R.id.categories);
            infoButton = (Button) movieView.findViewById(R.id.infoBtn);
            favouriteButton = (Button) movieView.findViewById(R.id.favouriteBtn);
            dismissButton = (Button) movieView.findViewById(R.id.dismissBtn);
        }

        /**
         * Method that binds the given movie to the view passed as a parameter in the class constructor.
         * @param movie the movie to be bound to the view passed as a parameter in the class constructor.
         */
        public void bindMovie(MovieModel movie) {
            // Set all the easy properties of the view.
            movieID = movie.getMovieID();
            poster.setImageDrawable(context.getResources().getDrawable(context.getResources().getIdentifier(movie.getPosterPath(), "drawable", context.getPackageName())));
            title.setText(movie.getTitle());
            year.setText(context.getResources().getString(R.string.release_date, movie.getReleaseDate()));
            rating.setText(context.getResources().getString(R.string.rating, String.valueOf(movie.getVoteAverage())));
            director.setText(context.getResources().getString(R.string.director, movie.getTitle()));
            plot.setText(context.getResources().getString(R.string.plot_summary, movie.getMovieOverview()));
            if (new DisFavUtil().isFavouriteMovie(context, userID, movieID)) {
                favouriteButton.setText("Unfavourite");
            }

            // Create an Arraylist with the IDs of each of the genres declared in the values/ids.xml file.
            ArrayList<String> genres = movie.getGenres();
            ArrayList<Integer> genreIDs = new ArrayList<>();
            genreIDs.add(R.id.genre1);
            genreIDs.add(R.id.genre2);

            // Create a ConstraintSet that is a copy of the current ConstraintSet of the categories ConstraintLayout.
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(categories);
            TextView previousCategory = null;

            /* For each of the genres of the movie add a new TextView to the categories ConstraintLayout that shows the
            * genre and set its properties programmatically. */
            for (int i = 0 ; i < genres.size() ; i++) {

                TextView category = new TextView(context);
                if (i < genreIDs.size()) {
                    category.setId(genreIDs.get(i));
                }

                constraintSet.constrainWidth(category.getId(), ConstraintSet.WRAP_CONTENT);
                constraintSet.constrainHeight(category.getId(), ConstraintSet.WRAP_CONTENT);

                category.setBackground(ContextCompat.getDrawable(context, R.drawable.rounded_border));

                category.setText(genres.get(i));
                category.setTextColor(ContextCompat.getColor(context, R.color.black));
                category.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);

                constraintSet.connect(category.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);

                categories.addView(category);

                boolean lastCategory = i == genres.size() - 1;
                if (previousCategory == null) {
                    constraintSet.connect(category.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START);
                }
                else {
                    constraintSet.setMargin(category.getId(), ConstraintSet.START, (int)(8 * context.getResources().getDisplayMetrics().density));
                    constraintSet.connect(previousCategory.getId(), ConstraintSet.END, category.getId(), ConstraintSet.START);
                    constraintSet.connect(category.getId(), ConstraintSet.START, previousCategory.getId(), ConstraintSet.END);
                    if (lastCategory) {
                        constraintSet.connect(category.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END);
                    }
                }

                previousCategory = category;
            }
            constraintSet.applyTo(categories);
        }

        /**
         * Method that adds OnClickListeners to the "More Info", "Favourite/Unfavourite" and "Dismiss" buttons of the movie cart.
         * @param position the index of the movie in the movies ArrayList.
         */
        public void addButtonListeners(int position) {
            // Listener that opens a browser page that leads to the movie's imdb page.
            infoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("de", "movie at " + position + " shows more info.");
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setData(Uri.parse("https://www.imdb.com/title/" + movieID + "/"));
                    context.startActivity(intent);
                }
            });

            // Listener that either adds the movie to the list of the user's favourites, or removes it from that list.
            favouriteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!Objects.equals(userID, "")) {
                        if (!new DisFavUtil().isFavouriteMovie(context, userID, movieID)) {
                            new DisFavUtil().favouriteMovie(context, userID, movieID);
                            if (!favouriteRemoves) {
                                favouriteButton.setText("Unfavourite");
                            }
                        }
                        else {
                            new DisFavUtil().unfavouriteMovie(context, userID, movieID);
                            if (favouriteRemoves) {
                                movies.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, movies.size());
                            }
                            else {
                                favouriteButton.setText("Favourite");
                            }
                        }
                    }
                    Log.d("de", "movie " + movieID + " added to favorites.");
                }
            });

            // Listener that adds the movie to the list of the user's dismissed movies.
            dismissButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!Objects.equals(userID, "")) {
                        new DisFavUtil().dismissMovie(context, userID, movieID);
                        movies.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, movies.size());
                    }
                    Log.d("de", "movie at " + position + " dismissed.");
                }
            });
        }
    }
}
