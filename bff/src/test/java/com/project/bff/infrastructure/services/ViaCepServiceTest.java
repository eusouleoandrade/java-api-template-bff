package com.project.bff.infrastructure.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

import com.project.bff.application.dtos.responses.CepServiceResponse;
import com.project.bff.application.dtos.responses.ViaCepServiceResponse;
import com.project.bff.application.exceptions.AppException;
import com.project.bff.application.mappings.ViaCepServiceResponseMapping;
import com.project.bff.shared.ultils.MsgUltil;

import nl.altindag.log.LogCaptor;

@SpringBootTest
public class ViaCepServiceTest {

    private final RestTemplate restTemplate;

    private final ViaCepServiceResponseMapping viaCepServiceResponseMapping;

    private final Environment env;

    private ViaCepService viaCepService;

    private final String urlBase = "http://viacep.com.br";

    private LogCaptor logCaptor;

    public ViaCepServiceTest() {

        restTemplate = mock(RestTemplate.class);
        viaCepServiceResponseMapping = mock(ViaCepServiceResponseMapping.class);
        env = mock(Environment.class);
    }

    @BeforeEach
    public void setup() {

        when(env.getProperty("viaCepService.urlBase")).thenReturn(urlBase);
    }

    @DisplayName("Test GetAddressAsync success")
    @ParameterizedTest
    @CsvSource({
            "12345-678, 12345678, Rua Exemplo 1, Apto 101, Centro, Cidade Exemplo 1, EX, 1234567, 98765, 11, 1234",
            "11223-445, 11223445,  Rua Exemplo 2, Apto 102, Centro, Cidade Exemplo 2, EX, 1122334, 11223, 22, 1122"
    })
    public void testGetAddressAsyncSuccess(String outputCep,
            String inputCep,
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
        String fullUrl = String.format("%s/ws/%s/json", urlBase, inputCep);

        ViaCepServiceResponse viaCepServiceResponse = new ViaCepServiceResponse();

        CepServiceResponse cepServiceExpectedResponse = new CepServiceResponse(outputCep, logradouro,
                complemento,
                bairro, localidade, uf, ibge, gia, ddd, siafi);

        when(restTemplate.getForObject(anyString(), eq(ViaCepServiceResponse.class)))
                .thenReturn(viaCepServiceResponse);

        when(viaCepServiceResponseMapping.convertToCepServiceResponse(viaCepServiceResponse))
                .thenReturn(cepServiceExpectedResponse);

        viaCepService = new ViaCepService(viaCepServiceResponseMapping, env, restTemplate);

        logCaptor = LogCaptor.forClass(ViaCepService.class);

        // Act
        var serviceResponse = viaCepService.getAddressAsync(inputCep).join();

        // Assert
        assertThat(viaCepService).isNotNull();

        assertThat(serviceResponse).isNotNull();
        assertThat(serviceResponse).isEqualTo(cepServiceExpectedResponse);

        verify(restTemplate, times(1)).getForObject(fullUrl, ViaCepServiceResponse.class);
        verify(viaCepServiceResponseMapping, times(1))
                .convertToCepServiceResponse(any(ViaCepServiceResponse.class));

        assertThat(logCaptor.getInfoLogs())
                .containsExactly(
                        "Start integration ViaCepService > method getAddressAsync.",
                        String.format("Send integration ViaCepService > %s.", fullUrl),
                        "Finishes integration ViaCepService > method getAddressAsync.");
    }

    @DisplayName("Test GetAddressAsync Failure with exception")
    @Test
    public void testGetAddressAsyncFailureWithException() {

        // Arrange
        String cep = "12345678";
        String fullUrl = String.format("%s/ws/%s/json", urlBase, cep);

        when(restTemplate.getForObject(anyString(), eq(ViaCepServiceResponse.class)))
                .thenThrow(new RuntimeException("Integration error"));

        viaCepService = new ViaCepService(viaCepServiceResponseMapping, env, restTemplate);

        logCaptor = LogCaptor.forClass(ViaCepService.class);

        // Act & Assert
        assertThrows(AppException.class, () -> viaCepService.getAddressAsync(cep).join());

        assertThat(viaCepService).isNotNull();

        verify(restTemplate, times(1)).getForObject(fullUrl, ViaCepServiceResponse.class);

        verify(viaCepServiceResponseMapping, never())
                .convertToCepServiceResponse(any(ViaCepServiceResponse.class));

        assertThat(logCaptor.getInfoLogs())
                .containsExactly(
                        "Start integration ViaCepService > method getAddressAsync.",
                        String.format("Send integration ViaCepService > %s.", fullUrl),
                        "Finishes integration ViaCepService > method getAddressAsync.");

        assertThat(logCaptor.getErrorLogs())
                .containsExactly(
                        MsgUltil.FAILED_TO_INTEGRATE_WITH_X0("ViaCep API")[1]
                                + " - Error: Integration error");
    }
}
