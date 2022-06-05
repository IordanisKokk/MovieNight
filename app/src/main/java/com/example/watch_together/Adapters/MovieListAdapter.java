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

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieViewHolder> {
    private ArrayList<MovieModel> movies = new ArrayList<>();
    private Context context;
    private String userID;

    public MovieListAdapter(Context context, ArrayList<MovieModel> movies) {
        this.context = context;
        this.movies = movies;
        userID = "";
    }

    public MovieListAdapter(Context context, ArrayList<MovieModel> movies, String userID) {
        this(context, movies);
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
        holder.bindMovie(movies.get(position));
        holder.addButtonListeners(position);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

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

        public void bindMovie(MovieModel movie) {
            movieID = movie.getMovieID();
            poster.setImageDrawable(Drawable.createFromPath(movie.getPosterPath()));
            title.setText(movie.getTitle());
            year.setText(context.getResources().getString(R.string.release_date, movie.getReleaseDate()));
            rating.setText(context.getResources().getString(R.string.rating, String.valueOf(movie.getVoteAverage())));
            director.setText(context.getResources().getString(R.string.director, movie.getTitle()));
            plot.setText(context.getResources().getString(R.string.plot_summary, movie.getMovieOverview()));
            if (new DisFavUtil().isFavouriteMovie(context, userID, movieID)) {
                favouriteButton.setText("Unfavourite");
            }
            ArrayList<String> genres = movie.getGenres();

            ArrayList<Integer> genreIDs = new ArrayList<>();
            genreIDs.add(R.id.genre1);
            genreIDs.add(R.id.genre2);

            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(categories);
            TextView previousCategory = null;

            /* https://spin.atomicobject.com/2019/04/08/constraintlayout-chaining-views-programmatically/ */
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

        public void addButtonListeners(int position) {
            infoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("de", "movie at " + position + " shows more info.");
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setData(Uri.parse("http://www.imdb.com"));
                    context.startActivity(intent);
                }
            });

            favouriteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!Objects.equals(userID, "")) {
                        if (new DisFavUtil().isFavouriteMovie(context, userID, movieID)) {
                            new DisFavUtil().favouriteMovie(context, userID, movieID);
                        }
                        else {
                            new DisFavUtil().unfavouriteMovie(context, userID, movieID);
                            movies.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, movies.size());
                        }
                    }
                    Log.d("de", "movie at " + position + " added to favorites.");
                }
            });

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
