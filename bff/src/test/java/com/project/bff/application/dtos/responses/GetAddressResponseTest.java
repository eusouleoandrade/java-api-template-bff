package com.project.bff.application.dtos.responses;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;

import com.project.bff.application.dtos.models.AddressModel;
import com.project.bff.shared.ultils.MsgUltil;

@SpringBootTest
public class GetAddressResponseTest {

    @DisplayName("Test GetAddressResponse Constructor and Getters")
    @ParameterizedTest
    @CsvSource({
            "12345-678, Rua Exemplo 1, Apto 101, Centro, Cidade Exemplo 1, EX, 1234567, 98765, 11, 1234",
            "56789-012, Rua Exemplo 2, Apto 102, Centro, Cidade Exemplo 2, AX, 5678901, 54321, 11, 5678"
    })
    public void testGetAddressResponseConstructorAndGetters(String cep,
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
        AddressModel addressModel = new AddressModel(cep, logradouro, complemento, bairro, localidade, uf, ibge,
                gia, ddd, siafi);

        // Act
        GetAddressResponse getAddressResponse = new GetAddressResponse(addressModel);

        // Assert
        assertThat(getAddressResponse).isNotNull();
        assertThat(getAddressResponse.isSucceeded()).isTrue();
        assertThat(getAddressResponse.getAddressModel()).isEqualTo(addressModel);
        assertThat(getAddressResponse.getAddressModel().getCep()).isEqualTo(cep);
        assertThat(getAddressResponse.getAddressModel().getLogradouro()).isEqualTo(logradouro);
        assertThat(getAddressResponse.getAddressModel().getComplemento()).isEqualTo(complemento);
        assertThat(getAddressResponse.getAddressModel().getBairro()).isEqualTo(bairro);
        assertThat(getAddressResponse.getAddressModel().getLocalidade()).isEqualTo(localidade);
        assertThat(getAddressResponse.getAddressModel().getUf()).isEqualTo(uf);
        assertThat(getAddressResponse.getAddressModel().getIbge()).isEqualTo(ibge);
        assertThat(getAddressResponse.getAddressModel().getGia()).isEqualTo(gia);
        assertThat(getAddressResponse.getAddressModel().getDdd()).isEqualTo(ddd);
        assertThat(getAddressResponse.getAddressModel().getSiafi()).isEqualTo(siafi);

        assertEquals(MsgUltil.RESPONSE_SUCCEEDED_MESSAGE()[1], getAddressResponse.getMessage());
    }
}