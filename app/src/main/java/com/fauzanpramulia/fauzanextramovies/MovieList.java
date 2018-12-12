package com.fauzanpramulia.fauzanextramovies;

import com.fauzanpramulia.fauzanextramovies.model.MovieItems;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MovieList {
    @SerializedName("results")
    public List<MovieItems> results;

    public List<MovieItems> getResults() {
        return results;
    }

    public void setResults(List<MovieItems> results) {
        this.results = results;
    }
}
