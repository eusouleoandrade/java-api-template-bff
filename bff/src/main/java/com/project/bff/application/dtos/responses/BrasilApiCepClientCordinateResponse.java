package com.project.bff.application.dtos.responses;

public class BrasilApiCepClientCordinateResponse {

    private String latitude;
    private String longitude;

    // Latitude
    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    // Longitude
    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
