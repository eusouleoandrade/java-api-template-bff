package com.project.bff.application.dtos.responses;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CepServiceResponseTest {

    @DisplayName("Should execute successfully when to use the parameterized constructor")
    @ParameterizedTest
    @CsvSource({
            "01001-000, Praça da Sé, lado ímpar, Sé, São Paulo, SP, 3550308, 1004, 11, 7107",
            "02001-000, Parque Anhembi, , Santana, São Paulo, SP, 3550308, 1004, 11, 7107"
    })
    public void shouldExecuteSuccessfullyWhenToUseTheParameterizedCtor(String cep,
            String logradouro,
            String complemento,
            String bairro,
            String localidade,
            String uf,
            String ibge,
            String gia,
            String ddd,
            String siafi) {

        // Arranje
        CepServiceResponse response;

        // Act
        response = new CepServiceResponse(cep, logradouro, complemento, bairro, localidade, uf, ibge, gia, ddd, siafi);

        // Assert
        assertNotNull(response);

        assertEquals(cep, response.getCep());
        assertEquals(logradouro, response.getLogradouro());
        assertEquals(complemento, response.getComplemento());
        assertEquals(bairro, response.getBairro());
        assertEquals(localidade, response.getLocalidade());
        assertEquals(uf, response.getUf());
        assertEquals(ibge, response.getIbge());
        assertEquals(gia, response.getGia());
        assertEquals(ddd, response.getDdd());
        assertEquals(siafi, response.getSiafi());
    }
}