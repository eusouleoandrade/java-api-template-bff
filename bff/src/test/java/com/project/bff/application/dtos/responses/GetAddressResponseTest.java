package com.project.bff.application.dtos.responses;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;

import com.project.bff.application.dtos.models.AddressModel;
import com.project.bff.shared.ultils.MsgUltil;

@SpringBootTest
public class GetAddressResponseTest {

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
        var addressModel = new AddressModel(cep,
                logradouro,
                complemento,
                bairro,
                localidade,
                uf,
                ibge,
                gia,
                ddd,
                siafi);

        GetAddressResponse response;

        // Act
        response = new GetAddressResponse(addressModel);

        // Assert
        assertNotNull(response);

        assertThat(response.getAddressModel()).isEqualTo(addressModel);

        assertThat(response.isSucceeded()).isTrue();
        assertEquals(MsgUltil.RESPONSE_SUCCEEDED_MESSAGE()[1], response.getMessage());
    }
}