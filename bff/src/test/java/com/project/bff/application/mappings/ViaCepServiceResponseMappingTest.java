package com.project.bff.application.mappings;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.project.bff.application.dtos.responses.CepServiceResponse;
import com.project.bff.application.dtos.responses.ViaCepServiceResponse;

@SpringBootTest
public class ViaCepServiceResponseMappingTest {

    @DisplayName("Test convert ViaCepServiceResponse to CepServiceResponse")
    @Test
    public void testConvertToCepServiceResponse() {
        // Arrange
        ViaCepServiceResponse viaCepServiceResponse = new ViaCepServiceResponse();
        viaCepServiceResponse.setCep("12345-678");
        viaCepServiceResponse.setLogradouro("Rua Exemplo");
        viaCepServiceResponse.setComplemento("Apto 101");
        viaCepServiceResponse.setBairro("Centro");
        viaCepServiceResponse.setLocalidade("Cidade Exemplo");
        viaCepServiceResponse.setUf("EX");
        viaCepServiceResponse.setIbge("1234567");
        viaCepServiceResponse.setGia("98765");
        viaCepServiceResponse.setDdd("11");
        viaCepServiceResponse.setSiafi("1234");

        ViaCepServiceResponseMapping mapping = new ViaCepServiceResponseMapping();

        // Act
        CepServiceResponse cepServiceResponse = mapping.convertToCepServiceResponse(viaCepServiceResponse);

        // Assert
        assertThat(cepServiceResponse).isNotNull();
        assertThat(cepServiceResponse.getCep()).isEqualTo("12345-678");
        assertThat(cepServiceResponse.getLogradouro()).isEqualTo("Rua Exemplo");
        assertThat(cepServiceResponse.getComplemento()).isEqualTo("Apto 101");
        assertThat(cepServiceResponse.getBairro()).isEqualTo("Centro");
        assertThat(cepServiceResponse.getLocalidade()).isEqualTo("Cidade Exemplo");
        assertThat(cepServiceResponse.getUf()).isEqualTo("EX");
        assertThat(cepServiceResponse.getIbge()).isEqualTo("1234567");
        assertThat(cepServiceResponse.getGia()).isEqualTo("98765");
        assertThat(cepServiceResponse.getDdd()).isEqualTo("11");
        assertThat(cepServiceResponse.getSiafi()).isEqualTo("1234");
    }
}