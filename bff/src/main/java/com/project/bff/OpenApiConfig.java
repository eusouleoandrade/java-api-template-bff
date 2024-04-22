package com.project.bff;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;

@Configuration
@OpenAPIDefinition(info = @io.swagger.v3.oas.annotations.info.Info(title = "BFF Api v1", version = "1.0", description = "Your BFF Api v1.", contact = @Contact(name = "Leandro Andrade", email = "eusouleoandrade@email.com")))
public class OpenApiConfig {

    @Bean
    GroupedOpenApi publicApi() {

        return GroupedOpenApi.builder()
                .group("BFF Api - V1")
                .packagesToScan("com.project.bff")
                .build();
    }
}