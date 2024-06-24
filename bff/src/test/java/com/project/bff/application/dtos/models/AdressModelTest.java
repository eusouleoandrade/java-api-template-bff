package com.project.bff.application.dtos.models;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AdressModelTest {

    @DisplayName("Test AddressModel Constructor and Getters")
    @ParameterizedTest
    @CsvSource({
            "12345-678, Rua Exemplo 1, Apto 101, Centro, Cidade Exemplo 1, EX, 1234567, 98765, 11, 1234",
            "56789-012, Rua Exemplo 2, Apto 102, Centro, Cidade Exemplo 2, AX, 5678901, 54321, 11, 5678"
    })
    public void testAddressModelConstructorAndGetters(String cep,
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
        AddressModel addressModel;

        // Act
        addressModel = new AddressModel(cep, logradouro, complemento, bairro, localidade, uf, ibge, gia, ddd, siafi);

        // Assert
        assertThat(addressModel).isNotNull();
        assertThat(addressModel.getCep()).isEqualTo(cep);
        assertThat(addressModel.getLogradouro()).isEqualTo(logradouro);
        assertThat(addressModel.getComplemento()).isEqualTo(complemento);
        assertThat(addressModel.getBairro()).isEqualTo(bairro);
        assertThat(addressModel.getLocalidade()).isEqualTo(localidade);
        assertThat(addressModel.getUf()).isEqualTo(uf);
        assertThat(addressModel.getIbge()).isEqualTo(ibge);
        assertThat(addressModel.getGia()).isEqualTo(gia);
        assertThat(addressModel.getDdd()).isEqualTo(ddd);
        assertThat(addressModel.getSiafi()).isEqualTo(siafi);
    }
}