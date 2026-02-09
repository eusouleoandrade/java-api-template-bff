package com.project.bff.application.dtos.responses;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BrasilApiCepClientCordinateResponseTest {

    @DisplayName("Test should store and retrieve latitude and longitude")
    @Test
    public void testSetAndGetLatitudeLongitude() {
        // Arrange
        BrasilApiCepClientCordinateResponse dto = new BrasilApiCepClientCordinateResponse();
        String lat = "-23.55052";
        String lon = "-46.633308";

        // Act
        dto.setLatitude(lat);
        dto.setLongitude(lon);

        // Assert
        assertThat(dto.getLatitude()).isEqualTo(lat);
        assertThat(dto.getLongitude()).isEqualTo(lon);
    }

    @DisplayName("Test new instance should have null latitude and longitude")
    @Test
    public void testNewInstanceHasNullFields() {
        // Arrange
        BrasilApiCepClientCordinateResponse dto = new BrasilApiCepClientCordinateResponse();

        // Act & Assert
        assertThat(dto.getLatitude()).isNull();
        assertThat(dto.getLongitude()).isNull();
    }
}

