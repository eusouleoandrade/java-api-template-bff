package com.project.bff.application.useCases;

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
import org.springframework.core.env.Environment;

import com.project.bff.application.dtos.responses.CepServiceResponse;
import com.project.bff.application.dtos.responses.GetAddressUseCaseResponse;
import com.project.bff.application.interfaces.repositories.IAddressAuditRepositoryAsync;
import com.project.bff.application.interfaces.services.ICepService;
import com.project.bff.application.mappings.CepServiceResponseMapping;
import com.project.bff.domain.entities.AddressAudit;
import com.project.bff.shared.ultils.MsgUltil;

import nl.altindag.log.LogCaptor;

@SpringBootTest
public class GetAddressUseCaseTest {

    private final IAddressAuditRepositoryAsync addressAuditRepositoryAsync;

    private final ICepService cepService;

    private final CepServiceResponseMapping cepServiceResponseMapping;

    private final Environment env;

    private GetAddressUseCase useCase;

    private LogCaptor logCaptor;

    public GetAddressUseCaseTest() {

        addressAuditRepositoryAsync = mock(IAddressAuditRepositoryAsync.class);
        cepService = mock(ICepService.class);
        cepServiceResponseMapping = mock(CepServiceResponseMapping.class);
        env = mock(Environment.class);

        when(env.getProperty("cep.numberOfCharacters")).thenReturn("8");
    }

    @DisplayName("Test RunAsync Success")
    @ParameterizedTest
    @CsvSource({
            "12345-678, 12345678, Rua Exemplo 1, Apto 101, Centro, Cidade Exemplo 1, EX, 1234567, 98765, 11, 1234",
            "56789-012, 56789012, Rua Exemplo 2, Apto 102, Centro, Cidade Exemplo 2, AX, 5678901, 54321, 11, 5678"
    })
    public void testRunAsyncSuccess(String cep,
            String sanitizedCep,
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
        CepServiceResponse cepServiceResponse = new CepServiceResponse(sanitizedCep, logradouro, complemento,
                bairro,
                localidade, uf, ibge, gia, ddd, siafi);

        GetAddressUseCaseResponse useCaseExpectedResponse = new GetAddressUseCaseResponse(sanitizedCep,
                logradouro,
                complemento, bairro, localidade, uf, ibge, gia, ddd, siafi);

        when(cepService.getAddressAsync(anyString()))
                .thenReturn(CompletableFuture.completedFuture(cepServiceResponse));

        when(addressAuditRepositoryAsync.createAsync(any(AddressAudit.class)))
                .thenReturn(CompletableFuture.completedFuture(null));

        when(cepServiceResponseMapping.convertToGetAddressUseCaseResponse(cepServiceResponse))
                .thenReturn(useCaseExpectedResponse);

        useCase = new GetAddressUseCase(addressAuditRepositoryAsync, cepService, cepServiceResponseMapping, env);

        logCaptor = LogCaptor.forClass(GetAddressUseCase.class);

        // Act
        var useCaseResponse = useCase.runAsync(cep).join();

        // Assert
        assertThat(useCaseResponse).isNotNull();
        assertThat(useCaseResponse).isEqualTo(useCaseExpectedResponse);

        assertThat(useCase.hasErrorNotification()).isFalse();
        assertThat(useCase.getErrorNotifications()).isEmpty();
        assertThat(useCase.getErrorNotifications()).hasSize(0);

        verify(cepService, times(1)).getAddressAsync(anyString());
        verify(addressAuditRepositoryAsync, times(1)).createAsync(any(AddressAudit.class));
        verify(cepServiceResponseMapping, times(1))
                .convertToGetAddressUseCaseResponse(any(CepServiceResponse.class));

        assertThat(logCaptor.getInfoLogs())
                .containsExactly(
                        "Start useCase GetAddressUseCase > method runAsync.",
                        "Finishes successfully useCase  GetAddressUseCase > method runAsync.");
    }

    @DisplayName("Test RunAsync with invalid Cep")
    @ParameterizedTest
    @CsvSource({
            "12345",
            "12345-56"
    })
    public void testRunAsyncWithInvalidCep(String cep) {

        // Arranje
        useCase = new GetAddressUseCase(addressAuditRepositoryAsync, cepService, cepServiceResponseMapping, env);

        logCaptor = LogCaptor.forClass(GetAddressUseCase.class);

        // Act
        var useCaseResponse = useCase.runAsync(cep).join();

        // Assert
        assertThat(useCaseResponse).isNull();

        assertThat(useCase.hasErrorNotification()).isTrue();
        assertThat(useCase.getErrorNotifications()).isNotEmpty();
        assertThat(useCase.getErrorNotifications()).hasSize(1);

        assertThat(useCase.getErrorNotifications().get(0).getKey())
                .isEqualTo(MsgUltil.X0_MUST_CONTAIN_X1_CHARACTERS(null, null)[0]);
        assertThat(useCase.getErrorNotifications().get(0).getMessage())
                .isEqualTo(MsgUltil.X0_MUST_CONTAIN_X1_CHARACTERS("Cep", "8")[1]);

        assertThat(logCaptor.getInfoLogs())
                .contains("Start useCase GetAddressUseCase > method runAsync.");
        assertThat(logCaptor.getInfoLogs())
                .doesNotContain("Finishes successfully useCase  GetAddressUseCase > method runAsync.");
    }

    @DisplayName("Test RunAsync with empty Cep")
    @Test
    public void testRunAsyncWithEmptyCep() {

        // Arrange
        String[] ceps = { "", " " };

        for (String cep : ceps) {

            useCase = new GetAddressUseCase(addressAuditRepositoryAsync, cepService, cepServiceResponseMapping, env);

            logCaptor = LogCaptor.forClass(GetAddressUseCase.class);

            // Act
            var useCaseResponse = useCase.runAsync(cep).join();

            // Assert
            assertThat(useCaseResponse).isNull();

            assertThat(useCase.hasErrorNotification()).isTrue();
            assertThat(useCase.getErrorNotifications()).isNotEmpty();
            assertThat(useCase.getErrorNotifications()).hasSize(1);

            assertThat(useCase.getErrorNotifications().get(0).getKey())
                    .isEqualTo(MsgUltil.X0_IS_REQUIRED(null)[0]);
            assertThat(useCase.getErrorNotifications().get(0).getMessage())
                    .isEqualTo(MsgUltil.X0_IS_REQUIRED("Cep")[1]);

            assertThat(logCaptor.getInfoLogs())
                    .containsExactly("Start useCase GetAddressUseCase > method runAsync.");
            assertThat(logCaptor.getInfoLogs())
                    .doesNotContain("Finishes successfully useCase  GetAddressUseCase > method runAsync.");
        }
    }
}