package com.project.bff.application.dtos.responses;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BrasilApiCepClientResponseTest {

    @DisplayName("Test should store and retrieve all simple fields and nested location")
    @Test
    public void testSetAndGetAllFieldsAndLocation() {

        // Arrange
        BrasilApiCepClientResponse dto = new BrasilApiCepClientResponse();
        String cep = "01001-000";
        String state = "SP";
        String city = "Sao Paulo";
        String neighborhood = "Centro";
        String street = "Praça da Sé";
        String service = "brasilapi";

        BrasilApiCepClientLocationResponse location = new BrasilApiCepClientLocationResponse();
        location.setType("Point");
        BrasilApiCepClientCordinateResponse coords = new BrasilApiCepClientCordinateResponse();
        coords.setLatitude("-23.55052");
        coords.setLongitude("-46.633308");
        location.setCoordinates(coords);

        // Act
        dto.setCep(cep);
        dto.setState(state);
        dto.setCity(city);
        dto.setNeighborhood(neighborhood);
        dto.setStreet(street);
        dto.setService(service);
        dto.setLocation(location);

        // Assert
        assertThat(dto.getCep()).isEqualTo(cep);
        assertThat(dto.getState()).isEqualTo(state);
        assertThat(dto.getCity()).isEqualTo(city);
        assertThat(dto.getNeighborhood()).isEqualTo(neighborhood);
        assertThat(dto.getStreet()).isEqualTo(street);
        assertThat(dto.getService()).isEqualTo(service);

        assertThat(dto.getLocation()).isNotNull();
        assertThat(dto.getLocation().getType()).isEqualTo("Point");
        assertThat(dto.getLocation().getCoordinates()).isNotNull();
        assertThat(dto.getLocation().getCoordinates().getLatitude()).isEqualTo("-23.55052");
        assertThat(dto.getLocation().getCoordinates().getLongitude()).isEqualTo("-46.633308");
    }

    @DisplayName("Test new instance should have null simple fields and null location")
    @Test
    public void testNewInstanceHasNullFields() {

        // Arrange
        BrasilApiCepClientResponse dto = new BrasilApiCepClientResponse();

        // Act & Assert
        assertThat(dto.getCep()).isNull();
        assertThat(dto.getState()).isNull();
        assertThat(dto.getCity()).isNull();
        assertThat(dto.getNeighborhood()).isNull();
        assertThat(dto.getStreet()).isNull();
        assertThat(dto.getService()).isNull();
        assertThat(dto.getLocation()).isNull();
    }
}

