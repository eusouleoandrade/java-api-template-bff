package com.project.bff.application.dtos.responses;

public class BrasilApiCepClientLocationResponse {

    private String type;

    private BrasilApiCepClientCordinateResponse coordinates;

    // Type
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    // Coordinates
    public BrasilApiCepClientCordinateResponse getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(BrasilApiCepClientCordinateResponse coordinates) {
        this.coordinates = coordinates;
    }
}
