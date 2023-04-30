package com.example.xseries.Response;

import com.example.xseries.Model.CastModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CreditsResponse {

        @SerializedName("cast")
        @Expose
        public List<CastModel> cast;

    public CreditsResponse(List<CastModel> cast) {
        this.cast = cast;
    }

    public List<CastModel> getCast() {
            return cast;
        }

        public void setCast(List<CastModel> cast) {
            this.cast = cast;
        }

}
