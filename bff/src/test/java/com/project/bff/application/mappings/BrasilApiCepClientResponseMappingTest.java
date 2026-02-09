package com.project.bff.application.mappings;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.project.bff.application.dtos.responses.BrasilApiCepClientResponse;
import com.project.bff.application.dtos.responses.CepServiceResponse;

public class BrasilApiCepClientResponseMappingTest {

    @DisplayName("Test convertToCepServiceResponse maps fields")
    @Test
    public void testConvertToCepServiceResponseMapsFields() {
        // Arrange
        BrasilApiCepClientResponseMapping mapping = new BrasilApiCepClientResponseMapping();

        BrasilApiCepClientResponse source = new BrasilApiCepClientResponse();
        source.setCep("12345678");
        source.setStreet("Rua Exemplo");
        source.setNeighborhood("Centro");
        source.setCity("Cidade Exemplo");
        source.setState("SP");

        // Act
        CepServiceResponse result = mapping.convertToCepServiceResponse(source);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getCep()).isEqualTo("12345678");
        assertThat(result.getLogradouro()).isEqualTo("Rua Exemplo");
        assertThat(result.getComplemento()).isEqualTo(""); // mapping sets complemento to empty string
        assertThat(result.getBairro()).isEqualTo("Centro");
        assertThat(result.getLocalidade()).isEqualTo("Cidade Exemplo");
        assertThat(result.getUf()).isEqualTo("SP");
        assertThat(result.getIbge()).isEqualTo(""); // mapping sets ibge to empty string
        assertThat(result.getGia()).isEqualTo("");
        assertThat(result.getDdd()).isEqualTo("");
        assertThat(result.getSiafi()).isEqualTo("");
    }

    @DisplayName("Test convertToCepServiceResponse handles null fields")
    @Test
    public void testConvertToCepServiceResponseHandlesNulls() {
        // Arrange
        BrasilApiCepClientResponseMapping mapping = new BrasilApiCepClientResponseMapping();

        BrasilApiCepClientResponse source = new BrasilApiCepClientResponse();
        source.setCep(null);
        source.setStreet(null);
        source.setNeighborhood(null);
        source.setCity(null);
        source.setState(null);

        // Act
        CepServiceResponse result = mapping.convertToCepServiceResponse(source);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getCep()).isNull();
        assertThat(result.getLogradouro()).isNull();
        assertThat(result.getComplemento()).isEqualTo("");
        assertThat(result.getBairro()).isNull();
        assertThat(result.getLocalidade()).isNull();
        assertThat(result.getUf()).isNull();
        assertThat(result.getIbge()).isEqualTo("");
        assertThat(result.getGia()).isEqualTo("");
        assertThat(result.getDdd()).isEqualTo("");
        assertThat(result.getSiafi()).isEqualTo("");
    }
}
