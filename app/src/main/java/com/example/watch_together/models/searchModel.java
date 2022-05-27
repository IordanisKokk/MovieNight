package com.example.watch_together.models;

import java.util.ArrayList;

public class searchModel {

    private String titleSearch;
    private ArrayList<String> genreSearch;

    public String getTitleSearch() {
        return titleSearch;
    }

    public ArrayList<String> getGenreSearch() {
        return genreSearch;
    }

    public searchModel(String titleSearch, ArrayList<String> genreSearch) {
        this.titleSearch = titleSearch;
        this.genreSearch = genreSearch;
    }
}
