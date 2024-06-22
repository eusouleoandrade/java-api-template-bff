package com.project.bff.application.mappings;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.project.bff.application.dtos.models.AddressModel;
import com.project.bff.application.dtos.responses.GetAddressUseCaseResponse;

@SpringBootTest
public class GetAddressUseCaseResponseMappingTest {

    @DisplayName("Test convert GetAddressUseCaseResponse to AddressModel")
    @Test
    public void testConvertToAddressModel() {

        // Arrange
        GetAddressUseCaseResponse getAddressUseCaseResponse = new GetAddressUseCaseResponse(
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

        GetAddressUseCaseResponseMapping mapping = new GetAddressUseCaseResponseMapping();

        // Act
        AddressModel addressModel = mapping.convertToAddressModel(getAddressUseCaseResponse);

        // Assert
        assertThat(addressModel).isNotNull();
        assertThat(addressModel.getCep()).isEqualTo("12345-678");
        assertThat(addressModel.getLogradouro()).isEqualTo("Rua Exemplo");
        assertThat(addressModel.getComplemento()).isEqualTo("Apto 101");
        assertThat(addressModel.getBairro()).isEqualTo("Centro");
        assertThat(addressModel.getLocalidade()).isEqualTo("Cidade Exemplo");
        assertThat(addressModel.getUf()).isEqualTo("EX");
        assertThat(addressModel.getIbge()).isEqualTo("1234567");
        assertThat(addressModel.getGia()).isEqualTo("98765");
        assertThat(addressModel.getDdd()).isEqualTo("11");
        assertThat(addressModel.getSiafi()).isEqualTo("1234");
    }
}