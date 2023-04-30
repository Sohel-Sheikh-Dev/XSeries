package com.example.xseries.Model;

import com.example.xseries.Response.CreditsResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MoviesModel {

    @SerializedName("id")
    public int movie_id;

    public String title, overview, release_date, tagline, backdrop_path, poster_path, name, status, original_language, budget, revenue;

    @SerializedName("first_air_date")
    public String air_date;

    public int runtime;

    public String author;

    //    public GenreResponse genres;
    public List<GenreModel> genres;

    @SerializedName("episode_run_time")
    @Expose
    public List<Integer> episodeRunTime = null;

//    public int[] episode_run_time;

    public CreditsResponse credits;

    public float vote_average;

    public MoviesModel(int movie_id, String title, String overview, String release_date, String tagline, String backdrop_path, String poster_path, String name, String status, String original_language, String budget, String revenue, String air_date, int runtime, String author, List<GenreModel> genres, List<Integer> episodeRunTime, CreditsResponse credits, float vote_average) {
        this.movie_id = movie_id;
        this.title = title;
        this.overview = overview;
        this.release_date = release_date;
        this.tagline = tagline;
        this.backdrop_path = backdrop_path;
        this.poster_path = poster_path;
        this.name = name;
        this.status = status;
        this.original_language = original_language;
        this.budget = budget;
        this.revenue = revenue;
        this.air_date = air_date;
        this.runtime = runtime;
        this.author = author;
        this.genres = genres;
        this.episodeRunTime = episodeRunTime;
        this.credits = credits;
        this.vote_average = vote_average;
    }

    /*
    public MoviesModel(int movie_id, String title, String overview, String release_date, String tagline, String backdrop_path, String poster_path, String name, String status, String original_language, String budget, String revenue, String air_date, int runtime, String author, List<GenreModel> genres, int[] episode_run_time, CreditsResponse credits, float vote_average) {
        this.movie_id = movie_id;
        this.title = title;
        this.overview = overview;
        this.release_date = release_date;
        this.tagline = tagline;
        this.backdrop_path = backdrop_path;
        this.poster_path = poster_path;
        this.name = name;
        this.status = status;
        this.original_language = original_language;
        this.budget = budget;
        this.revenue = revenue;
        this.air_date = air_date;
        this.runtime = runtime;
        this.author = author;
        this.genres = genres;
        this.episode_run_time = episode_run_time;
        this.credits = credits;
        this.vote_average = vote_average;
    }
*/

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<GenreModel> getGenres() {
        return genres;
    }

    public void setGenres(List<GenreModel> genres) {
        this.genres = genres;
    }


//    public GenreResponse getGenres() {
//        return genres;
//    }
//
//    public void setGenres(GenreResponse genres) {
//        this.genres = genres;
//    }

    public CreditsResponse getCredits() {
        return credits;
    }

    public void setCredits(CreditsResponse credits) {
        this.credits = credits;
    }

    public int getMovie_id() {
        return movie_id;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getRevenue() {
        return revenue;
    }

    public void setRevenue(String revenue) {
        this.revenue = revenue;
    }

    public List<Integer> getEpisodeRunTime() {
        return episodeRunTime;
    }

    public void setEpisodeRunTime(List<Integer> episodeRunTime) {
        this.episodeRunTime = episodeRunTime;
    }

/*
    public int getEpisode_run_time() {
        return episode_run_time[0];
    }

    public void setEpisode_run_time(int[] episode_run_time) {
        this.episode_run_time = episode_run_time;
    }
*/
    public String getAir_date() {
        return air_date;
    }

    public void setAir_date(String air_date) {
        this.air_date = air_date;
    }

    public int getMovieOrTV_id() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }

    public String getTitle() {
        return title;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getBackdrop_path() {
            return "https://www.themoviedb.org/t/p/original/" + backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getPoster_path() {
        return "https://www.themoviedb.org/t/p/original/" + poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public float getVote_average() {
        return vote_average;
    }

    public void setVote_average(float vote_average) {
        this.vote_average = vote_average;
    }
}
