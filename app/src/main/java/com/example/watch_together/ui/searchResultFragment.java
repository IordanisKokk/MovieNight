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
import android.widget.TextView;

import com.example.watch_together.Adapters.MovieListAdapter;
import com.example.watch_together.R;
import com.example.watch_together.Utills.SearchUtil;
import com.example.watch_together.models.MovieModel;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link searchResultFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 * This fragment contains the results for the movie search of the user.
 *
 */
public class searchResultFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String titleSearch;
    private String genres;

    TextView searchTitle;
    RecyclerView recyclerView;
    MovieListAdapter adapter;

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
            titleSearch = getArguments().getString("title");
            genres = getArguments().getString("genres");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_result, container, false);

        Log.d("de", "Searching for title: " + titleSearch + " and genres:" + genres);
        searchTitle = (TextView) view.findViewById(R.id.searchTitle);
        searchTitle.setText(titleSearch.toString());
        recyclerView = (RecyclerView) view.findViewById(R.id.movieCards);
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        findMovie(container.getContext());

        return view;
    }

    public void findMovie(Context context){

        String[] genresSplit = genres.split(",");
        ArrayList<String> genresList = new ArrayList<>(Arrays.asList(genresSplit));

        for (String genre : genresList) {
            Log.d("de", genre);
        }
        ArrayList<MovieModel> movies;
        movies = new SearchUtil(titleSearch, genresList).searchForMovies(getActivity());
        if(movies != null){
            adapter = new MovieListAdapter(context, movies);
            recyclerView.setAdapter(adapter);
        }

    }

}