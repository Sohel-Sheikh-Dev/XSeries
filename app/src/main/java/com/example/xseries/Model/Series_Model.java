package com.example.xseries.Model;

public class Series_Model {

    String id,url,thumbnail,duration,title;

    public Series_Model(String id, String url, String thumbnail, String duration, String title) {
        this.id = id;
        this.url = url;
        this.thumbnail = thumbnail;
        this.duration = duration;
        this.title = title;
    }

    public Series_Model() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
