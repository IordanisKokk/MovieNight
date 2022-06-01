package com.example.watch_together.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.watch_together.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link movieCardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class movieCardFragment extends Fragment {

    View view;

    private ImageView poster;
    private TextView title;
    private TextView year;
    private TextView rating;
    private TextView director;
    private TextView plot;
    private Button infoButton;
    private Button favouriteButton;
    private Button dismissButton;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public movieCardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment movieCardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static movieCardFragment newInstance(String param1, String param2) {
        movieCardFragment fragment = new movieCardFragment();
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
        view = inflater.inflate(R.layout.fragment_movie_card, container, false);
        poster = (ImageView) view.findViewById(R.id.poster);
        title = (TextView) view.findViewById(R.id.title);
        year = (TextView) view.findViewById(R.id.year);
        rating = (TextView) view.findViewById(R.id.rating);
        director = (TextView) view.findViewById(R.id.director);
        plot = (TextView) view.findViewById(R.id.plot);
        infoButton = (Button) view.findViewById(R.id.infoBtn);
        favouriteButton = (Button) view.findViewById(R.id.favouriteBtn);
        dismissButton = (Button) view.findViewById(R.id.dismissBtn);
        return view;
    }
}