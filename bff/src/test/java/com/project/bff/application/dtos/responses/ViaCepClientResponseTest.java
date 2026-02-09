package com.project.bff.application.dtos.responses;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ViaCepClientResponseTest {

    @DisplayName("Test constructor initializes all fields correctly")
    @Test
    public void testConstructorInitializesFields() {
        // Arrange
        String cep = "12345678";
        String logradouro = "Rua Exemplo";
        String complemento = "Apto 101";
        String bairro = "Centro";
        String localidade = "Cidade Exemplo";
        String uf = "SP";
        String ibge = "1234567";
        String gia = "98765";
        String ddd = "11";
        String siafi = "1234";

        // Act
        ViaCepClientResponse dto = new ViaCepClientResponse(cep, logradouro, complemento, bairro, localidade, uf,
                ibge, gia, ddd, siafi);

        // Assert
        assertThat(dto.getCep()).isEqualTo(cep);
        assertThat(dto.getLogradouro()).isEqualTo(logradouro);
        assertThat(dto.getComplemento()).isEqualTo(complemento);
        assertThat(dto.getBairro()).isEqualTo(bairro);
        assertThat(dto.getLocalidade()).isEqualTo(localidade);
        assertThat(dto.getUf()).isEqualTo(uf);
        assertThat(dto.getIbge()).isEqualTo(ibge);
        assertThat(dto.getGia()).isEqualTo(gia);
        assertThat(dto.getDdd()).isEqualTo(ddd);
        assertThat(dto.getSiafi()).isEqualTo(siafi);
    }

    @DisplayName("Test setters update fields correctly")
    @Test
    public void testSettersAndGetters() {
        // Arrange
        ViaCepClientResponse dto = new ViaCepClientResponse(null, null, null, null, null, null, null, null, null, null);

        // Act
        dto.setCep("87654321");
        dto.setLogradouro("Av Outro");
        dto.setComplemento("Casa");
        dto.setBairro("Bairro X");
        dto.setLocalidade("Outra Cidade");
        dto.setUf("RJ");
        dto.setIbge("7654321");
        dto.setGia("56789");
        dto.setDdd("21");
        dto.setSiafi("4321");

        // Assert
        assertThat(dto.getCep()).isEqualTo("87654321");
        assertThat(dto.getLogradouro()).isEqualTo("Av Outro");
        assertThat(dto.getComplemento()).isEqualTo("Casa");
        assertThat(dto.getBairro()).isEqualTo("Bairro X");
        assertThat(dto.getLocalidade()).isEqualTo("Outra Cidade");
        assertThat(dto.getUf()).isEqualTo("RJ");
        assertThat(dto.getIbge()).isEqualTo("7654321");
        assertThat(dto.getGia()).isEqualTo("56789");
        assertThat(dto.getDdd()).isEqualTo("21");
        assertThat(dto.getSiafi()).isEqualTo("4321");
    }
}

