package com.project.bff.application.dtos.responses;

public class BrasilApiCepClientResponse {

    private String cep;
    private String state;
    private String city;
    private String neighborhood;
    private String street;
    private String service;
    private BrasilApiCepClientLocationResponse location;

    // Cep
    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    // State
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    // City
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    // Neighborhood
    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    // Street
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    // Service
    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    // Location
    public BrasilApiCepClientLocationResponse getLocation() {
        return location;
    }

    public void setLocation(BrasilApiCepClientLocationResponse location) {
        this.location = location;
    }
}