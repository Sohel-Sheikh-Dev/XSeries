package com.example.xseries.Response;

import com.example.xseries.Model.ReviewsModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReviewsResponse {

    @SerializedName("results")
    @Expose
    private List<ReviewsModel> results;

    public List<ReviewsModel> getResults() {
        return results;
    }

    public void setResults(List<ReviewsModel> results) {
        this.results = results;
    }

}
