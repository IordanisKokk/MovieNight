package com.example.watch_together.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.watch_together.R;
import com.example.watch_together.Utills.DbHandler;
import com.example.watch_together.models.MovieModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link searchResultFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class searchResultFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String titleSearch;
    private String mParam2;

    public searchResultFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment searchResultFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static searchResultFragment newInstance(String param1, String param2) {
        searchResultFragment fragment = new searchResultFragment();
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
            titleSearch = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_result, container, false);


        findMovie();

        return view;
    }

    public void findMovie(){
        //TODO
        DbHandler dBHandler = new DbHandler(getActivity(), null, null, 1);
        ArrayList<MovieModel> movies;
        movies = dBHandler.findMovieByTitle("The");
        if(movies != null){
            for (MovieModel movie: movies) {
                Log.d("de", "Movie: " + movie.getTitle() + " Rating: " + movie.getVoteAverage() + " Release Date: " + movie.getReleaseDate() + " Genre(s): " + movie.getGenres().toString());
            }
        }

    }

}