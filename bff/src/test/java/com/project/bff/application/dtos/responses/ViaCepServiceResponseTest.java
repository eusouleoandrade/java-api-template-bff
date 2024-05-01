package com.project.bff.application.dtos.responses;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ViaCepServiceResponseTest {

    @DisplayName("Should execute successfully when to use the getters and setters")
    @ParameterizedTest
    @CsvSource({
            "01001-000, Praça da Sé, lado ímpar, Sé, São Paulo, SP, 3550308, 1004, 11, 7107",
            "02001-000, Parque Anhembi, , Santana, São Paulo, SP, 3550308, 1004, 11, 7107"
    })
    public void shouldExecuteSuccessfullyWhenToUseTheGettersAndSetters() {

        // Arranje

        // Act

        // Assert
    }
}