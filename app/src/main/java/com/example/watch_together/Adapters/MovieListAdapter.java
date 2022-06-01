package com.example.watch_together.Adapters;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.watch_together.R;
import com.example.watch_together.models.MovieModel;

import java.util.ArrayList;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieViewHolder> {
    private ArrayList<MovieModel> movies = new ArrayList<>();
    private Context context;

    public MovieListAdapter(Context context, ArrayList<MovieModel> movies) {
        this.context = context;
        this.movies = movies;
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
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        private Context context;

        private ImageView poster;
        private TextView title;
        private TextView year;
        private TextView rating;
        private TextView director;
        private TextView plot;
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
            infoButton = (Button) movieView.findViewById(R.id.infoBtn);
            favouriteButton = (Button) movieView.findViewById(R.id.favouriteBtn);
            dismissButton = (Button) movieView.findViewById(R.id.dismissBtn);
        }

        public void bindMovie(MovieModel movie) {
            poster.setImageDrawable(Drawable.createFromPath(movie.getPosterPath()));
            Log.d("de", movie.getPosterPath());
            title.setText(movie.getTitle());
            year.setText(movie.getReleaseDate());
            rating.setText(Float.toString(movie.getVoteAverage()));
            director.setText(movie.getTitle());
            plot.setText(movie.getMovieOverview());
        }
    }
}
