package com.project.bff.application.dtos.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AdressModelTest {

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
        AddressModel model;

        // Act
        model = new AddressModel(cep, logradouro, complemento, bairro, localidade, uf, ibge, gia, ddd, siafi);

        // Assert
        assertNotNull(model);

        assertEquals(cep, model.getCep());
        assertEquals(logradouro, model.getLogradouro());
        assertEquals(complemento, model.getComplemento());
        assertEquals(bairro, model.getBairro());
        assertEquals(localidade, model.getLocalidade());
        assertEquals(uf, model.getUf());
        assertEquals(ibge, model.getIbge());
        assertEquals(gia, model.getGia());
        assertEquals(ddd, model.getDdd());
        assertEquals(siafi, model.getSiafi());
    }
}
