package com.project.bff.infrastructure.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.project.bff.application.dtos.responses.BrasilApiCepClientResponse;
import com.project.bff.application.dtos.responses.CepServiceResponse;
import com.project.bff.application.dtos.responses.ViaCepClientResponse;
import com.project.bff.application.exceptions.AppException;
import com.project.bff.application.interfaces.clients.IBrasilApiCepClient;
import com.project.bff.application.interfaces.clients.IViaCepClient;
import com.project.bff.application.mappings.BrasilApiCepClientResponseMapping;
import com.project.bff.application.mappings.ViaCepClientResponseMapping;
import java.util.concurrent.CompletableFuture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CepServiceTest {

    @DisplayName("Should return CepServiceResponse from ViaCep when available")
    @Test
    public void testViaCepSuccess() throws Exception {
        // Arrange
        IViaCepClient viaCepClient = mock(IViaCepClient.class);
        ViaCepClientResponseMapping viaMapping = mock(ViaCepClientResponseMapping.class);
        IBrasilApiCepClient brasilClient = mock(IBrasilApiCepClient.class);
        BrasilApiCepClientResponseMapping brasilMapping = mock(BrasilApiCepClientResponseMapping.class);

        String cep = "01001000";
        ViaCepClientResponse viaDto = new ViaCepClientResponse(
                "01001-000","Rua","","Bairro","Cidade","SP","123","","11","1"
        );

        CepServiceResponse expected = new CepServiceResponse(
                "01001-000","Rua","","Bairro","Cidade","SP","123","","11","1"
        );

        when(viaCepClient.getAddressAsync(cep)).thenReturn(CompletableFuture.completedFuture(viaDto));
        when(viaMapping.convertToCepServiceResponse(viaDto)).thenReturn(expected);

        CepService service = new CepService(viaCepClient, viaMapping, brasilClient, brasilMapping);

        // Act
        CepServiceResponse actual = service.getAddressAsync(cep).get();

        // Assert
        assertThat(actual).isEqualTo(expected);
        // brasil client should not be invoked
        verifyNoInteractions(brasilClient);
    }

    @DisplayName("Should fallback to BrasilApi when ViaCep mapping returns null")
    @Test
    public void testViaReturnsNull_thenBrasilSucceeds() throws Exception {
        // Arrange
        IViaCepClient viaCepClient = mock(IViaCepClient.class);
        ViaCepClientResponseMapping viaMapping = mock(ViaCepClientResponseMapping.class);
        IBrasilApiCepClient brasilClient = mock(IBrasilApiCepClient.class);
        BrasilApiCepClientResponseMapping brasilMapping = mock(BrasilApiCepClientResponseMapping.class);

        String cep = "01001000";
        ViaCepClientResponse viaDto = new ViaCepClientResponse(
                "01001-000","Rua","","Bairro","Cidade","SP","123","","11","1"
        );

        BrasilApiCepClientResponse brasilDto = new BrasilApiCepClientResponse();
        brasilDto.setCep("01001-000");
        brasilDto.setState("SP");

        CepServiceResponse expected = new CepServiceResponse(
                "01001-000","RuaBrasil","","Bairro","Cidade","SP","123","","11","1"
        );

        when(viaCepClient.getAddressAsync(cep)).thenReturn(CompletableFuture.completedFuture(viaDto));
        when(viaMapping.convertToCepServiceResponse(viaDto)).thenReturn(null);

        when(brasilClient.getAddressAsync(cep)).thenReturn(CompletableFuture.completedFuture(brasilDto));
        when(brasilMapping.convertToCepServiceResponse(brasilDto)).thenReturn(expected);

        CepService service = new CepService(viaCepClient, viaMapping, brasilClient, brasilMapping);

        // Act
        CepServiceResponse actual = service.getAddressAsync(cep).get();

        // Assert
        assertThat(actual).isEqualTo(expected);
        verify(brasilClient).getAddressAsync(cep);
    }

    @DisplayName("Should fallback to BrasilApi when ViaCep throws and Brasil succeeds")
    @Test
    public void testViaThrows_thenBrasilSucceeds() throws Exception {
        // Arrange
        IViaCepClient viaCepClient = mock(IViaCepClient.class);
        ViaCepClientResponseMapping viaMapping = mock(ViaCepClientResponseMapping.class);
        IBrasilApiCepClient brasilClient = mock(IBrasilApiCepClient.class);
        BrasilApiCepClientResponseMapping brasilMapping = mock(BrasilApiCepClientResponseMapping.class);

        String cep = "01001000";

        BrasilApiCepClientResponse brasilDto = new BrasilApiCepClientResponse();
        brasilDto.setCep("01001-000");
        brasilDto.setState("SP");

        CepServiceResponse expected = new CepServiceResponse(
                "01001-000","RuaBrasil","","Bairro","Cidade","SP","123","","11","1"
        );

        when(viaCepClient.getAddressAsync(cep)).thenReturn(CompletableFuture.failedFuture(new RuntimeException("Via down")));

        when(brasilClient.getAddressAsync(cep)).thenReturn(CompletableFuture.completedFuture(brasilDto));
        when(brasilMapping.convertToCepServiceResponse(brasilDto)).thenReturn(expected);

        CepService service = new CepService(viaCepClient, viaMapping, brasilClient, brasilMapping);

        // Act
        CepServiceResponse actual = service.getAddressAsync(cep).get();

        // Assert
        assertThat(actual).isEqualTo(expected);
        verify(brasilClient).getAddressAsync(cep);
    }

    @DisplayName("Should throw AppException when both services return null responses")
    @Test
    public void testBothReturnNull_throwAppException() {
        // Arrange
        IViaCepClient viaCepClient = mock(IViaCepClient.class);
        ViaCepClientResponseMapping viaMapping = mock(ViaCepClientResponseMapping.class);
        IBrasilApiCepClient brasilClient = mock(IBrasilApiCepClient.class);
        BrasilApiCepClientResponseMapping brasilMapping = mock(BrasilApiCepClientResponseMapping.class);

        String cep = "01001000";
        ViaCepClientResponse viaDto = new ViaCepClientResponse(
                "01001-000","Rua","","Bairro","Cidade","SP","123","","11","1"
        );

        BrasilApiCepClientResponse brasilDto = new BrasilApiCepClientResponse();
        brasilDto.setCep("01001-000");
        brasilDto.setState("SP");

        when(viaCepClient.getAddressAsync(cep)).thenReturn(CompletableFuture.completedFuture(viaDto));
        when(viaMapping.convertToCepServiceResponse(viaDto)).thenReturn(null);

        when(brasilClient.getAddressAsync(cep)).thenReturn(CompletableFuture.completedFuture(brasilDto));
        when(brasilMapping.convertToCepServiceResponse(brasilDto)).thenReturn(null);

        CepService service = new CepService(viaCepClient, viaMapping, brasilClient, brasilMapping);

        // Act & Assert
        assertThatThrownBy(() -> service.getAddressAsync(cep).join())
                .isInstanceOf(AppException.class)
                .satisfies(ex -> {
                    AppException appEx = (AppException) ex.getCause() != null && ex instanceof java.util.concurrent.CompletionException
                            ? (AppException) ex.getCause()
                            : (AppException) ex;
                    String expectedCode = com.project.bff.shared.utils.MsgUtil.SERVICE_FAILURE_X0("BrasilCep API")[0];
                    String expectedMessage = com.project.bff.shared.utils.MsgUtil.SERVICE_FAILURE_X0("BrasilCep API")[1];
                    assertThat(appEx.getCode()).isEqualTo(expectedCode);
                    assertThat(appEx.getMessage()).isEqualTo(expectedMessage);
                });
    }

    @DisplayName("Should throw AppException with BrasilCepss API message when brasil client throws")
    @Test
    public void testBrasilThrows_throwsAppExceptionWithTypoMessage() {
        // Arrange
        IViaCepClient viaCepClient = mock(IViaCepClient.class);
        ViaCepClientResponseMapping viaMapping = mock(ViaCepClientResponseMapping.class);
        IBrasilApiCepClient brasilClient = mock(IBrasilApiCepClient.class);
        BrasilApiCepClientResponseMapping brasilMapping = mock(BrasilApiCepClientResponseMapping.class);

        String cep = "01001000";
        ViaCepClientResponse viaDto = new ViaCepClientResponse(
                "01001-000","Rua","","Bairro","Cidade","SP","123","","11","1"
        );

        when(viaCepClient.getAddressAsync(cep)).thenReturn(CompletableFuture.completedFuture(viaDto));
        when(viaMapping.convertToCepServiceResponse(viaDto)).thenReturn(null);

        when(brasilClient.getAddressAsync(cep)).thenReturn(CompletableFuture.failedFuture(new RuntimeException("Brasil error")));

        CepService service = new CepService(viaCepClient, viaMapping, brasilClient, brasilMapping);

        // Act & Assert
        assertThatThrownBy(() -> service.getAddressAsync(cep))
                .isInstanceOf(AppException.class)
                .satisfies(ex -> {
                    AppException appEx = (AppException) ex;
                    String expectedCode = com.project.bff.shared.utils.MsgUtil.SERVICE_FAILURE_X0("BrasilCep API")[0];
                    String expectedMessage = com.project.bff.shared.utils.MsgUtil.SERVICE_FAILURE_X0("BrasilCep API")[1];

                    assertThat(appEx.getCode()).isEqualTo(expectedCode);
                    assertThat(appEx.getMessage()).isEqualTo(expectedMessage);
                    assertThat(appEx.getCause()).isNotNull();
                    assertThat(appEx.getCause()).hasMessageContaining("Brasil error");
                });
    }
}

