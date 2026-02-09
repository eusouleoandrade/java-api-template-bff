package com.project.bff.application.mappings;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.project.bff.application.dtos.responses.CepServiceResponse;
import com.project.bff.application.dtos.responses.ViaCepClientResponse;

public class ViaCepClientResponseMappingTest {

    @DisplayName("Test convertToCepServiceResponse maps fields")
    @Test
    public void testConvertToCepServiceResponseMapsFields() {

        // Arrange
        ViaCepClientResponseMapping mapping = new ViaCepClientResponseMapping();

        ViaCepClientResponse source = new ViaCepClientResponse("12345678", "Rua Exemplo", "Apto 101",
                "Centro", "Cidade Exemplo", "SP", "1234567", "98765", "11", "1234");

        // Act
        CepServiceResponse result = mapping.convertToCepServiceResponse(source);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getCep()).isEqualTo("12345678");
        assertThat(result.getLogradouro()).isEqualTo("Rua Exemplo");
        assertThat(result.getComplemento()).isEqualTo("Apto 101");
        assertThat(result.getBairro()).isEqualTo("Centro");
        assertThat(result.getLocalidade()).isEqualTo("Cidade Exemplo");
        assertThat(result.getUf()).isEqualTo("SP");
        assertThat(result.getIbge()).isEqualTo("1234567");
        assertThat(result.getGia()).isEqualTo("98765");
        assertThat(result.getDdd()).isEqualTo("11");
        assertThat(result.getSiafi()).isEqualTo("1234");
    }

    @DisplayName("Test convertToCepServiceResponse handles null fields")
    @Test
    public void testConvertToCepServiceResponseHandlesNulls() {

        // Arrange
        ViaCepClientResponseMapping mapping = new ViaCepClientResponseMapping();

        ViaCepClientResponse source = new ViaCepClientResponse(null, null, null, null, null, null, null, null, null, null);

        // Act
        CepServiceResponse result = mapping.convertToCepServiceResponse(source);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getCep()).isNull();
        assertThat(result.getLogradouro()).isNull();
        assertThat(result.getComplemento()).isNull();
        assertThat(result.getBairro()).isNull();
        assertThat(result.getLocalidade()).isNull();
        assertThat(result.getUf()).isNull();
        assertThat(result.getIbge()).isNull();
        assertThat(result.getGia()).isNull();
        assertThat(result.getDdd()).isNull();
        assertThat(result.getSiafi()).isNull();
    }
}
