package com.project.bff.presentation.controllers.v1;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import com.project.bff.application.dtos.models.AddressModel;
import com.project.bff.application.dtos.responses.GetAddressResponse;
import com.project.bff.application.dtos.responses.GetAddressUseCaseResponse;
import com.project.bff.application.interfaces.useCases.IGetAddressUseCase;
import com.project.bff.application.mappings.GetAddressUseCaseResponseMapping;
import com.project.bff.shared.notifications.contexts.NotificationContext;

import nl.altindag.log.LogCaptor;

@SpringBootTest
public class AddressControllerTest {

    private final IGetAddressUseCase getAddressUseCase;

    private final GetAddressUseCaseResponseMapping mapping;

    private final NotificationContext notificationContext;

    private AddressController controller;

    private LogCaptor logCaptor;

    public AddressControllerTest() {

        this.getAddressUseCase = mock(IGetAddressUseCase.class);
        this.mapping = mock(GetAddressUseCaseResponseMapping.class);
        this.notificationContext = mock(NotificationContext.class);
    }

    @DisplayName("Test get success")
    @ParameterizedTest
    @CsvSource({
            "12345-678, 12345678, Rua Exemplo 1, Apto 101, Centro, Cidade Exemplo 1, EX, 1234567, 98765, 11, 1234",
            "11223-445, 11223445,  Rua Exemplo 2, Apto 102, Centro, Cidade Exemplo 2, EX, 1122334, 11223, 22, 1122"
    })
    public void testGetSuccess(String outputCep,
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

        // Arranje
        GetAddressUseCaseResponse usecCaseResponse = new GetAddressUseCaseResponse(outputCep, logradouro, complemento,
                bairro, localidade, uf, ibge, gia, ddd, siafi);

        AddressModel addressModel = new AddressModel(outputCep, logradouro, complemento, bairro, localidade, uf, ibge,
                gia, ddd, siafi);

        when(getAddressUseCase.runAsync(anyString())).thenReturn(CompletableFuture.completedFuture(usecCaseResponse));
        when(mapping.convertToAddressModel(usecCaseResponse)).thenReturn(addressModel);

        var getAddressExpectedResponse = new GetAddressResponse(addressModel);

        controller = new AddressController(getAddressUseCase, mapping, notificationContext);

        logCaptor = LogCaptor.forClass(AddressController.class);

        // Act
        var response = controller.get(inputCep);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getBody()).usingRecursiveComparison().isEqualTo(getAddressExpectedResponse);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        verify(getAddressUseCase, times(1)).runAsync(anyString());
        verify(mapping, times(1)).convertToAddressModel(any(GetAddressUseCaseResponse.class));

        assertThat(logCaptor.getInfoLogs())
                .containsExactly(
                        "Start controller AddressController > method get.",
                        "Finishes successfully controller AddressController > method get.");
    }

    @DisplayName("Test get failure with has error notification")
    @Test
    public void testGetFailureWithHasErrorNotification() {

        // Arranje
        String cep = "12345678";

        when(getAddressUseCase.runAsync(anyString())).thenReturn(CompletableFuture.completedFuture(null));
        when(getAddressUseCase.hasErrorNotification()).thenReturn(true);

        controller = new AddressController(getAddressUseCase, mapping, notificationContext);

        logCaptor = LogCaptor.forClass(AddressController.class);

        // Act
        var response = controller.get(cep);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        verify(getAddressUseCase, times(1)).runAsync(anyString());

        assertThat(logCaptor.getInfoLogs())
                .containsExactly("Start controller AddressController > method get.")
                .doesNotContain("Finishes successfully controller AddressController > method get.");
    }
}
