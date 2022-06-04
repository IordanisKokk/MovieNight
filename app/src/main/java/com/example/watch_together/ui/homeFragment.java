package com.example.watch_together.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.watch_together.Adapters.MovieListAdapter;
import com.example.watch_together.R;
import com.example.watch_together.Utills.DisFavUtil;
import com.example.watch_together.Utills.WatchTogether;
import com.example.watch_together.models.MovieModel;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link homeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class homeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView recyclerView;
    MovieListAdapter adapter;

    public homeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment homeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static homeFragment newInstance(String param1, String param2) {
        homeFragment fragment = new homeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.favouriteMovieCards);
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        findFavourites(container.getContext());

        return view;
    }

    public void findFavourites(Context context){
        ArrayList<MovieModel> movies;
        String userID = ((WatchTogether) getActivity().getApplication()).getUserID();
        movies = new DisFavUtil().getFavorites(context, userID);
        if(movies != null){
            adapter = new MovieListAdapter(context, movies, false, userID);
            recyclerView.setAdapter(adapter);
            for (MovieModel movie: movies) {
                Log.d("de", "Movie: " + movie.getTitle() + " Rating: " + movie.getVoteAverage() + " Release Date: " + movie.getReleaseDate() + " Genre(s): " + movie.getGenres().toString() + " Poster: " + movie.getPosterPath());
            }
        }

    }
}