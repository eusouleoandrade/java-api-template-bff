package com.project.bff.application.dtos.responses;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CepServiceResponseTest {

    @DisplayName("Test CepServiceResponse Constructor and Getters")
    @ParameterizedTest
    @CsvSource({
            "12345-678, Rua Exemplo 1, Apto 101, Centro, Cidade Exemplo 1, EX, 1234567, 98765, 11, 1234",
            "56789-012, Rua Exemplo 2, Apto 102, Centro, Cidade Exemplo 2, AX, 5678901, 54321, 11, 5678"
    })
    public void testCepServiceResponseConstructorAndGetters(String cep,
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
        CepServiceResponse cepServiceResponse;

        // Act
        cepServiceResponse = new CepServiceResponse(cep, logradouro, complemento, bairro, localidade, uf, ibge, gia,
                ddd, siafi);

        // Assert
        assertThat(cepServiceResponse).isNotNull();
        assertThat(cepServiceResponse.getCep()).isEqualTo(cep);
        assertThat(cepServiceResponse.getLogradouro()).isEqualTo(logradouro);
        assertThat(cepServiceResponse.getComplemento()).isEqualTo(complemento);
        assertThat(cepServiceResponse.getBairro()).isEqualTo(bairro);
        assertThat(cepServiceResponse.getLocalidade()).isEqualTo(localidade);
        assertThat(cepServiceResponse.getUf()).isEqualTo(uf);
        assertThat(cepServiceResponse.getIbge()).isEqualTo(ibge);
        assertThat(cepServiceResponse.getGia()).isEqualTo(gia);
        assertThat(cepServiceResponse.getDdd()).isEqualTo(ddd);
        assertThat(cepServiceResponse.getSiafi()).isEqualTo(siafi);
    }
}