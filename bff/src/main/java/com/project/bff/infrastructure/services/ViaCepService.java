package com.project.bff.infrastructure.services;

import java.util.concurrent.CompletableFuture;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.project.bff.application.dtos.responses.CepServiceResponse;
import com.project.bff.application.dtos.responses.ViaCepServiceResponse;
import com.project.bff.application.interfaces.services.ICepService;
import com.project.bff.application.mappings.ViaCepServiceResponseMapping;

@Service
public class ViaCepService implements ICepService {

    private final RestTemplate restTemplate;

    private final String urlBase;

    private final ViaCepServiceResponseMapping viaCepServiceResponseMapping;

    public ViaCepService(ViaCepServiceResponseMapping viaCepServiceResponseMapping,
            Environment env,
            RestTemplate restTemplate) {

        this.viaCepServiceResponseMapping = viaCepServiceResponseMapping;
        this.restTemplate = restTemplate;

        urlBase = env.getProperty("viaCepService.urlBase");
    }

    @Override
    public CompletableFuture<CepServiceResponse> getAddressAsync(String cep) {

        // TODO: Inserir log e tratamento de erros.

        var fullUrl = String.format("%s/ws/%s/json", urlBase, cep);

        var viaCepServiceResponse = restTemplate.getForObject(fullUrl, ViaCepServiceResponse.class);

        var response = viaCepServiceResponseMapping.convertToCepServiceResponse(viaCepServiceResponse);

        return CompletableFuture.completedFuture(response);
    }
}