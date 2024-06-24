package com.project.bff.application.dtos.responses;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ViaCepServiceResponseTest {

    @DisplayName("Test ViaCepServiceResponse Getters and Setters")
    @ParameterizedTest
    @CsvSource({
            "12345-678, Rua Exemplo 1, Apto 101, Centro, Cidade Exemplo 1, EX, 1234567, 98765, 11, 1234",
            "56789-012, Rua Exemplo 2, Apto 102, Centro, Cidade Exemplo 2, AX, 5678901, 54321, 11, 5678"
    })
    public void testViaCepServiceResponseGettersAndSetters(String cep,
            String logradouro,
            String complemento,
            String bairro,
            String localidade,
            String uf,
            String ibge,
            String gia,
            String ddd,
            String siafi) {

        // Arrange
        ViaCepServiceResponse response = new ViaCepServiceResponse();

        // Act
        response.setCep(cep);
        response.setLogradouro(logradouro);
        response.setComplemento(complemento);
        response.setBairro(bairro);
        response.setLocalidade(localidade);
        response.setUf(uf);
        response.setIbge(ibge);
        response.setGia(gia);
        response.setDdd(ddd);
        response.setSiafi(siafi);

        // Assert
        assertThat(response).isNotNull();

        assertThat(response.getCep()).isEqualTo(cep);
        assertThat(response.getLogradouro()).isEqualTo(logradouro);
        assertThat(response.getComplemento()).isEqualTo(complemento);
        assertThat(response.getBairro()).isEqualTo(bairro);
        assertThat(response.getLocalidade()).isEqualTo(localidade);
        assertThat(response.getUf()).isEqualTo(uf);
        assertThat(response.getIbge()).isEqualTo(ibge);
        assertThat(response.getGia()).isEqualTo(gia);
        assertThat(response.getDdd()).isEqualTo(ddd);
        assertThat(response.getSiafi()).isEqualTo(siafi);
    }
}