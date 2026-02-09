package com.project.bff.infrastructure.clients;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.project.bff.application.dtos.responses.ViaCepClientResponse;
import com.project.bff.application.exceptions.AppException;
import com.project.bff.shared.utils.MsgUtil;
import java.util.concurrent.CompletableFuture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

public class ViaCepClientTest {

    @DisplayName("getAddressAsync should return completed future with response when RestTemplate succeeds")
    @Test
    public void testGetAddressAsyncSuccess() throws Exception {
        // Arrange
        Environment env = mock(Environment.class);
        RestTemplate restTemplate = mock(RestTemplate.class);

        String urlBase = "https://viacep.test";
        when(env.getProperty("viaCepClient.urlBase")).thenReturn(urlBase);

        String cep = "01001000";
        String fullUrl = String.format("%s/ws/%s/json", urlBase, cep);

        ViaCepClientResponse expectedResponse = new ViaCepClientResponse(
                "01001-000",
                "Praça da Sé",
                "",
                "Sé",
                "Sao Paulo",
                "SP",
                "3550308",
                "",
                "11",
                "7107"
        );

        when(restTemplate.getForObject(fullUrl, ViaCepClientResponse.class)).thenReturn(expectedResponse);

        ViaCepClient client = new ViaCepClient(env, restTemplate);

        // Act
        CompletableFuture<ViaCepClientResponse> future = client.getAddressAsync(cep);
        ViaCepClientResponse actual = future.get();

        // Assert
        assertThat(actual).isNotNull();
        assertThat(actual.getCep()).isEqualTo(expectedResponse.getCep());
        assertThat(actual.getUf()).isEqualTo(expectedResponse.getUf());

        verify(restTemplate).getForObject(fullUrl, ViaCepClientResponse.class);
    }

    @DisplayName("getAddressAsync should throw AppException when RestTemplate fails")
    @Test
    public void testGetAddressAsyncThrowsAppExceptionOnRestTemplateError() {
        // Arrange
        Environment env = mock(Environment.class);
        RestTemplate restTemplate = mock(RestTemplate.class);

        String urlBase = "https://viacep.test";
        when(env.getProperty("viaCepClient.urlBase")).thenReturn(urlBase);

        String cep = "01001000";
        String fullUrl = String.format("%s/ws/%s/json", urlBase, cep);

        when(restTemplate.getForObject(fullUrl, ViaCepClientResponse.class))
                .thenThrow(new RuntimeException("Network down"));

        ViaCepClient client = new ViaCepClient(env, restTemplate);

        // Act & Assert
        assertThatThrownBy(() -> client.getAddressAsync(cep))
                .isInstanceOf(AppException.class)
                .satisfies(ex -> {
                    AppException appEx = (AppException) ex;
                    String expectedCode = MsgUtil.FAILED_TO_INTEGRATE_WITH_X0("ViaCep API")[0];
                    String expectedMessage = MsgUtil.FAILED_TO_INTEGRATE_WITH_X0("ViaCep API")[1];
                    assertThat(appEx.getCode()).isEqualTo(expectedCode);
                    assertThat(appEx.getMessage()).isEqualTo(expectedMessage);
                });
    }
}
