package com.example.xseries.Items;

import com.example.xseries.Model.MovieTVTrailerModel;

import java.util.List;

public class ParentMediaItem {

    public String ParentItemTextView;

    public List<MovieTVTrailerModel> movieTVTrailerModelList;


    public ParentMediaItem(String parentItemTextView, List<MovieTVTrailerModel> movieTVTrailerModelList) {
        ParentItemTextView = parentItemTextView;
        this.movieTVTrailerModelList = movieTVTrailerModelList;
    }

    public String getParentItemTextView() {
        return ParentItemTextView;
    }

    public List<MovieTVTrailerModel> getMovieTVTrailerModelList() {
        return movieTVTrailerModelList;
    }

}
