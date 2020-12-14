package com.example.softec;

public class MapMarkerData {

    private String longtitude;
    private String latitude;
    private String snippet;
    private String title;

    public MapMarkerData(String longtitude, String latitude, String snippet, String title) {
        this.longtitude = longtitude;
        this.latitude = latitude;
        this.snippet = snippet;
        this.title = title;
    }

    public String getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(String longtitude) {
        this.longtitude = longtitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
