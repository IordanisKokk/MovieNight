package com.example.watch_together.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
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
        holder.addButtonListeners(position);
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
            title.setText(movie.getTitle());
            year.setText(context.getResources().getString(R.string.release_date, movie.getReleaseDate()));
            rating.setText(context.getResources().getString(R.string.rating, movie.getVoteAverage()));
            director.setText(context.getResources().getString(R.string.director, movie.getTitle()));
            plot.setText(context.getResources().getString(R.string.plot_summary, movie.getMovieOverview()));
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
                    Log.d("de", "movie at " + position + " added to favorites.");
                }
            });

            dismissButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    movies.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, movies.size());
                    Log.d("de", "movie at " + position + " dismissed.");
                }
            });
        }
    }
}
