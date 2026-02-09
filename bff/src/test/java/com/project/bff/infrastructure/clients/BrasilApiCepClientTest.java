package com.project.bff.infrastructure.clients;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.project.bff.application.dtos.responses.BrasilApiCepClientResponse;
import com.project.bff.application.exceptions.AppException;
import com.project.bff.shared.utils.MsgUtil;
import java.util.concurrent.CompletableFuture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

public class BrasilApiCepClientTest {

    @DisplayName("getAddressAsync should return completed future with response when RestTemplate succeeds")
    @Test
    public void testGetAddressAsyncSuccess() throws Exception {
        // Arrange
        Environment env = mock(Environment.class);
        RestTemplate restTemplate = mock(RestTemplate.class);

        String urlBase = "https://api.test";
        when(env.getProperty("brasilApiClient.urlBase")).thenReturn(urlBase);

        String cep = "01001000";
        String fullUrl = String.format("%s/api/cep/v2/%s", urlBase, cep);

        BrasilApiCepClientResponse expectedResponse = new BrasilApiCepClientResponse();
        expectedResponse.setCep("01001-000");
        expectedResponse.setState("SP");

        when(restTemplate.getForObject(fullUrl, BrasilApiCepClientResponse.class)).thenReturn(expectedResponse);

        BrasilApiCepClient client = new BrasilApiCepClient(env, restTemplate);

        // Act
        CompletableFuture<BrasilApiCepClientResponse> future = client.getAddressAsync(cep);
        BrasilApiCepClientResponse actual = future.get();

        // Assert
        assertThat(actual).isNotNull();
        assertThat(actual.getCep()).isEqualTo(expectedResponse.getCep());
        assertThat(actual.getState()).isEqualTo(expectedResponse.getState());

        verify(restTemplate).getForObject(fullUrl, BrasilApiCepClientResponse.class);
    }

    @DisplayName("getAddressAsync should throw AppException when RestTemplate fails")
    @Test
    public void testGetAddressAsyncThrowsAppExceptionOnRestTemplateError() {
        // Arrange
        Environment env = mock(Environment.class);
        RestTemplate restTemplate = mock(RestTemplate.class);

        String urlBase = "https://api.test";
        when(env.getProperty("brasilApiClient.urlBase")).thenReturn(urlBase);

        String cep = "01001000";
        String fullUrl = String.format("%s/api/cep/v2/%s", urlBase, cep);

        when(restTemplate.getForObject(fullUrl, BrasilApiCepClientResponse.class))
                .thenThrow(new RuntimeException("Network down"));

        BrasilApiCepClient client = new BrasilApiCepClient(env, restTemplate);

        // Act & Assert
        assertThatThrownBy(() -> client.getAddressAsync(cep))
                .isInstanceOf(AppException.class)
                .satisfies(ex -> {
                    AppException appEx = (AppException) ex;
                    String expectedCode = MsgUtil.FAILED_TO_INTEGRATE_WITH_X0("BrasilCep API")[0];
                    String expectedMessage = MsgUtil.FAILED_TO_INTEGRATE_WITH_X0("BrasilCep API")[1];
                    assertThat(appEx.getCode()).isEqualTo(expectedCode);
                    assertThat(appEx.getMessage()).isEqualTo(expectedMessage);
                });
    }
}

