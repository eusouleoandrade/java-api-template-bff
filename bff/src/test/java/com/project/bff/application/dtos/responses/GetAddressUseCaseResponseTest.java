package com.project.bff.application.dtos.responses;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GetAddressUseCaseResponseTest {

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
        GetAddressUseCaseResponse response;

        // Act
        response = new GetAddressUseCaseResponse(cep,
                logradouro,
                complemento,
                bairro,
                localidade,
                uf,
                ibge,
                gia,
                ddd,
                siafi);

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
