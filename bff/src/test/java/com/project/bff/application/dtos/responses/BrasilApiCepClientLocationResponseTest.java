package com.project.bff.application.dtos.responses;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BrasilApiCepClientLocationResponseTest {

    @DisplayName("Test should store and retrieve type and coordinates")
    @Test
    public void testSetAndGetTypeAndCoordinates() {

        // Arrange
        BrasilApiCepClientLocationResponse dto = new BrasilApiCepClientLocationResponse();
        String type = "Point";
        BrasilApiCepClientCordinateResponse coordinates = new BrasilApiCepClientCordinateResponse();
        coordinates.setLatitude("-23.55052");
        coordinates.setLongitude("-46.633308");

        // Act
        dto.setType(type);
        dto.setCoordinates(coordinates);

        // Assert
        assertThat(dto.getType()).isEqualTo(type);
        assertThat(dto.getCoordinates()).isNotNull();
        assertThat(dto.getCoordinates().getLatitude()).isEqualTo("-23.55052");
        assertThat(dto.getCoordinates().getLongitude()).isEqualTo("-46.633308");
    }

    @DisplayName("Test new instance should have null type and coordinates")
    @Test
    public void testNewInstanceHasNullFields() {

        // Arrange
        BrasilApiCepClientLocationResponse dto = new BrasilApiCepClientLocationResponse();

        // Act & Assert
        assertThat(dto.getType()).isNull();
        assertThat(dto.getCoordinates()).isNull();
    }
}

