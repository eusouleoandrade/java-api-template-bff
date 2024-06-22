package com.project.bff.application.mappings;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.project.bff.application.dtos.responses.CepServiceResponse;
import com.project.bff.application.dtos.responses.GetAddressUseCaseResponse;

@SpringBootTest
public class CepServiceResponseMappingTest {

    @DisplayName("Test convert CepServiceResponse to GetAddressUseCaseResponse")
    @Test
    public void testConvertToGetAddressUseCaseResponse() {

        // Arrange
        CepServiceResponse cepServiceResponse = new CepServiceResponse(
                "12345-678",
                "Rua Exemplo",
                "Apto 101",
                "Centro",
                "Cidade Exemplo",
                "EX",
                "1234567",
                "98765",
                "11",
                "1234");

        CepServiceResponseMapping mapping = new CepServiceResponseMapping();

        // Act
        GetAddressUseCaseResponse response = mapping.convertToGetAddressUseCaseResponse(cepServiceResponse);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getCep()).isEqualTo("12345-678");
        assertThat(response.getLogradouro()).isEqualTo("Rua Exemplo");
        assertThat(response.getComplemento()).isEqualTo("Apto 101");
        assertThat(response.getBairro()).isEqualTo("Centro");
        assertThat(response.getLocalidade()).isEqualTo("Cidade Exemplo");
        assertThat(response.getUf()).isEqualTo("EX");
        assertThat(response.getIbge()).isEqualTo("1234567");
        assertThat(response.getGia()).isEqualTo("98765");
        assertThat(response.getDdd()).isEqualTo("11");
        assertThat(response.getSiafi()).isEqualTo("1234");
    }
}