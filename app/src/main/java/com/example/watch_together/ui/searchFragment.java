package com.example.watch_together.ui;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.watch_together.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;

public class searchFragment extends Fragment {


    Button button;
    ChipGroup chipGroup;
    EditText searchField;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public searchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment searchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static searchFragment newInstance(String param1, String param2) {
        searchFragment fragment = new searchFragment();
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
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        chipGroup = view.findViewById(R.id.chipGroup);
        button = view.findViewById(R.id.searchButton);
        searchField = (EditText) view.findViewById(R.id.title_edit_text);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Integer> selectedChipIDs = chipGroup.getCheckedChipIds();
                final String searchInput = searchField.getText().toString();
                Log.d("de", "Searching");

                ArrayList<Chip> chips = new ArrayList<>();
                ArrayList<String> selectedChipTexts = new ArrayList<>();
                for (int i = 0; i < chipGroup.getChildCount(); i++) {
                    if(selectedChipIDs.contains(chipGroup.getChildAt(i).getId())){
                        chips.add((Chip) chipGroup.getChildAt(i));
                        selectedChipTexts.add(((Chip) chipGroup.getChildAt(i)).getText().toString());
                    }
                }

                Log.d("de", "Title: " + searchInput);
                Log.d("de", "Genres: ");
                for (String chip : selectedChipTexts) {
                    Log.d("de", "-" + chip);
                }
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, new searchResultFragment()).commit();
            }
        });

        return view;
    }



}

